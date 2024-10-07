package com.fivemybab.ittabab.store.command.application.service;

import com.fivemybab.ittabab.exception.NotFoundException;
import com.fivemybab.ittabab.store.command.application.dto.CreateStoreFavoriteDto;
import com.fivemybab.ittabab.store.command.domain.repository.StoreFavoriteRepository;
import com.fivemybab.ittabab.store.command.domain.aggregate.StoreFavorite;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StoreFavoriteService {

    private final StoreFavoriteRepository repository;
    private final ModelMapper modelMapper;

    /* 가게 즐겨찾기 추가 */
    @Transactional
    public void createStoreFavorite(CreateStoreFavoriteDto newStoreFavorite, Long userId) {

        StoreFavorite storeFavorite = modelMapper.map(newStoreFavorite, StoreFavorite.class);

        // 이전에 즐겨찾기 여부 확인
        if (repository.existsByUserIdAndStoreId(userId, newStoreFavorite.getStoreId())) {
            throw new IllegalArgumentException("이미 즐겨찾기한 가게입니다.");
        }

        storeFavorite.setUserId(userId);
        repository.save(storeFavorite);
    }

    /* 가게 즐겨찾기 삭제 */
    @Transactional
    public void deleteStoreFavorite(Long storeId, Long userId) {

        // 즐겨찾기와 가게 번호가 일치하는지 확인
        if (!repository.existsByStoreId(storeId)) {
            throw new NotFoundException("해당 가게의 즐겨찾기가 존재하지 않습니다.");
        }


        // 본인의 가게 즐겨찾기가 아닐 경우 예외 발생
        StoreFavorite storeFavorite = repository.findByStoreIdAndUserId(storeId, userId)
                .orElseThrow(() -> new IllegalArgumentException("해당 가게의 즐겨찾기가 존재하지 않습니다."));

        // 즐겨찾기 삭제
        repository.delete(storeFavorite);
    }

}
