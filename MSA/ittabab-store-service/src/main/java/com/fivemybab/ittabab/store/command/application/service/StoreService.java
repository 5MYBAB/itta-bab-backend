package com.fivemybab.ittabab.store.command.application.service;

import com.fivemybab.ittabab.exception.NotFoundException;
import com.fivemybab.ittabab.store.command.application.dto.CreateStoreDto;
import com.fivemybab.ittabab.store.command.application.dto.UpdateStoreDto;
import com.fivemybab.ittabab.store.command.domain.repository.StoreFavoriteRepository;
import com.fivemybab.ittabab.store.command.domain.repository.StoreMenuRepository;
import com.fivemybab.ittabab.store.command.domain.repository.StoreRepository;
import com.fivemybab.ittabab.store.command.domain.repository.StoreReviewRepository;
import com.fivemybab.ittabab.store.command.domain.aggregate.Store;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StoreService {


    private final StoreRepository storeRepository;
    private final StoreReviewRepository storeReviewRepository;
    private final StoreFavoriteRepository storeFavoriteRepository;
    private final StoreMenuRepository storeMenuRepository;
    private final ModelMapper modelMapper;


    /* 가게 추가 */
    @Transactional
    public void createStore(CreateStoreDto newStore, Long userId) {

        Store store = modelMapper.map(newStore, Store.class);
        store.setUserId(userId);
        storeRepository.save(store);

    }

    /* 가게 수정 */
    @Transactional
    public void updateStore(Long storeId, Long userId ,UpdateStoreDto updateStoreDto) {

        if (!storeRepository.existsByStoreId(storeId)) {
            throw new NotFoundException("해당 가게가 존재하지 않습니다.");
        }

        Store store =
                storeRepository.findByStoreId(storeId);



        if (updateStoreDto.getStoreName() != null) {
            store.modifyStoreName(updateStoreDto.getStoreName());
        }

        if (updateStoreDto.getStoreLocation() != null) {
            store.modifyStoreLocation(updateStoreDto.getStoreLocation());
        }

        if (updateStoreDto.getStoreOpenTime() != null) {
            store.modifyStoreOpenTime(updateStoreDto.getStoreOpenTime());
        }

        if (updateStoreDto.getStoreEndTime() != null) {
            store.modifyStoreEndTime(updateStoreDto.getStoreEndTime());
        }

        if (updateStoreDto.getStoreWeek() != null) {
            store.modifyStoreWeek(updateStoreDto.getStoreWeek());
        }

        if (updateStoreDto.getStoreInfo() != null) {
            store.modifyStoreInfo(updateStoreDto.getStoreInfo());
        }

        if (updateStoreDto.getStoreStatus() != null) {

            String status = updateStoreDto.getStoreStatus().toString();
            store.modifyStoreStatus(status);
        }

    }



    /* 가게 삭제 */
    @Transactional
    public void deleteStore(Long storeId) {

        if (!storeRepository.existsByStoreId(storeId)) {
            throw new NotFoundException("해당 가게가 존재하지 않습니다.");
        }

        /* 가게 리뷰 삭제 */
        storeReviewRepository.deleteByStoreId(storeId);

        /* 즐겨찾기 삭제 */
        storeFavoriteRepository.deleteByStoreId(storeId);

        /* 가게 메뉴 삭제 */
        storeMenuRepository.deleteByStoreId(storeId);

        // 가게 삭제
        storeRepository.deleteById(storeId);
    }


}
