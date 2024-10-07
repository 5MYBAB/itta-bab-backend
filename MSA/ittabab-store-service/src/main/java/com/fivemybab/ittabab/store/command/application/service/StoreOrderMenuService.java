package com.fivemybab.ittabab.store.command.application.service;

import com.fivemybab.ittabab.exception.NotFoundException;
import com.fivemybab.ittabab.store.command.application.dto.CreateStoreOrderMenuDto;
import com.fivemybab.ittabab.store.command.domain.repository.StoreMenuRepository;
import com.fivemybab.ittabab.store.command.domain.repository.StoreOrderMenuRepository;
import com.fivemybab.ittabab.store.command.domain.repository.StoreReviewRepository;
import com.fivemybab.ittabab.store.command.domain.aggregate.StoreOrderMenu;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StoreOrderMenuService {

    private final StoreOrderMenuRepository repository;
    private final StoreReviewRepository storeReviewRepository;
    private final StoreMenuRepository storeMenuRepository;
    private final ModelMapper modelMapper;

    /* 가게 주문메뉴 추가 */
    @Transactional
    public void createStoreOrderMenu(CreateStoreOrderMenuDto newStoreOrderMenu, Long userId) {

        // 존재하지 않는 리뷰 입력시
        if(!storeReviewRepository.existsById(newStoreOrderMenu.getReviewId())) {
            throw new NotFoundException("해당 리뷰가 존재하지 않습니다.");
        }

        // 존재하지 않는 메뉴 입력시
        if(!storeMenuRepository.existsById(newStoreOrderMenu.getMenuId())) {
            throw new NotFoundException("해당 메뉴가 존재하지 않습니다.");
        }

        StoreOrderMenu storeOrderMenu = modelMapper.map(newStoreOrderMenu, StoreOrderMenu.class);
        storeOrderMenu.setUserId(userId);
        repository.save(storeOrderMenu);
    }

    /* 가게 주문메뉴 삭제 */
    @Transactional
    public void deleteStoreOrderMenu(Long orderId) {
        repository.deleteById(orderId);
    }


}
