package com.fivemybab.ittabab.store.command.domain.repository;

import com.fivemybab.ittabab.store.command.domain.aggregate.Store;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreRepository extends JpaRepository<Store, Long> {
    // 가게 ID로 가게를 찾는 메소드
    Store findByStoreId(Long storeId);

    // 해당 가게가 존재하는지 확인
    boolean existsByStoreId(Long storeId);


}
