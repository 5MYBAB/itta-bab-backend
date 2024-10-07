package com.fivemybab.ittabab.store.command.domain.repository;

import com.fivemybab.ittabab.store.command.domain.aggregate.StoreOrderMenu;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreOrderMenuRepository extends JpaRepository<StoreOrderMenu, Long> {

    void deleteByMenuId(Long menuId);
}
