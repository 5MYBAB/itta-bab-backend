package com.fivemybab.ittabab.store.command.domain.repository;

import com.fivemybab.ittabab.store.command.domain.aggregate.StoreMenuCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreMenuCategoryRepository extends JpaRepository<StoreMenuCategory, Long> {

    boolean existsByMenuCategoryName(String menuCategoryName);

}
