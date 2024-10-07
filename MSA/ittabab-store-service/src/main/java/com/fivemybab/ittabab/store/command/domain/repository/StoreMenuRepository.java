package com.fivemybab.ittabab.store.command.domain.repository;

import com.fivemybab.ittabab.store.command.domain.aggregate.StoreMenu;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreMenuRepository extends JpaRepository<StoreMenu, Long> {
    void deleteByStoreId(Long storeId);

    void deleteMenuByMenuCategoryId(Long categoryId);
}
