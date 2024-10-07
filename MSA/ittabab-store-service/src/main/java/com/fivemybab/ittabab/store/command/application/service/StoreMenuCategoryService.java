package com.fivemybab.ittabab.store.command.application.service;

import com.fivemybab.ittabab.exception.NotFoundException;
import com.fivemybab.ittabab.store.command.application.dto.CreateStoreMenuCategoryDto;
import com.fivemybab.ittabab.store.command.application.dto.UpdateStoreMenuCategoryDto;
import com.fivemybab.ittabab.store.command.domain.repository.StoreMenuCategoryRepository;
import com.fivemybab.ittabab.store.command.domain.repository.StoreMenuRepository;
import com.fivemybab.ittabab.store.command.domain.aggregate.StoreMenuCategory;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StoreMenuCategoryService {

    private final StoreMenuCategoryRepository storeMenuCategoryRepository;
    private final StoreMenuRepository storeMenuRepository;
    private final ModelMapper modelMapper;

    /* 메뉴 카테고리 추가 */
    @Transactional
    public void createStoreMenuCategory(CreateStoreMenuCategoryDto newMenuCategory) {

        String menuCategoryName = newMenuCategory.getMenuCategoryName();

        // 중복된 메뉴 카테고리 이름이 존재하는지 확인
        if (storeMenuCategoryRepository.existsByMenuCategoryName(menuCategoryName)) {
            throw new IllegalArgumentException("이미 존재하는 메뉴 카테고리 이름입니다.");
        }

        StoreMenuCategory storeMenuCategory = modelMapper.map(newMenuCategory, StoreMenuCategory.class);
        storeMenuCategoryRepository.save(storeMenuCategory);
    }

    /* 메뉴 카테고리 수정 */
    @Transactional
    public void updateStoreMenuCategory(Long categoryId , UpdateStoreMenuCategoryDto updateMenuCategory) {
        StoreMenuCategory menuCategory = storeMenuCategoryRepository.findById(categoryId)
                .orElseThrow(()-> new IllegalArgumentException("메뉴를 찾지 못했습니다."));

        if (updateMenuCategory.getMenuCategoryName() != null) {
            menuCategory.modifyMenuCategoryName(updateMenuCategory.getMenuCategoryName());
        }

    }


    /* 가게 카테고리 삭제 */
    @Transactional
    public void deleteStoreMenuCategory(Long categoryId) {

        // 메뉴 카테고리가 존재하지 않으면 예외 발생
        StoreMenuCategory menuCategory = storeMenuCategoryRepository.findById(categoryId)
                .orElseThrow(() -> new NotFoundException("존재하지 않는 카테고리입니다."));

        storeMenuRepository.deleteMenuByMenuCategoryId(categoryId);


        storeMenuCategoryRepository.deleteById(categoryId);
    }



}
