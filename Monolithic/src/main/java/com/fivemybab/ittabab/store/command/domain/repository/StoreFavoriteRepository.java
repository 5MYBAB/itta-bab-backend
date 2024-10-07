package com.fivemybab.ittabab.store.command.domain.repository;

import com.fivemybab.ittabab.store.command.domain.aggregate.StoreFavorite;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StoreFavoriteRepository extends JpaRepository<StoreFavorite, Long> {

    boolean existsByUserIdAndStoreId(Long userId, Long storeId);

    Optional<StoreFavorite> findByStoreIdAndUserId(Long storeId, Long userId);

    boolean existsByStoreId(Long storeId);

    void deleteByStoreId(Long storeId);
}
