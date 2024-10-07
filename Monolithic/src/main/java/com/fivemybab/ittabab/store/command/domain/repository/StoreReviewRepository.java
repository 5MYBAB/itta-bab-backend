package com.fivemybab.ittabab.store.command.domain.repository;

import com.fivemybab.ittabab.store.command.domain.aggregate.StoreReview;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StoreReviewRepository extends JpaRepository<StoreReview, Long> {

    // reviewId와 userId를 통해 해당 유저의 리뷰인지 확인
    Optional<StoreReview> findByReviewIdAndUserId(Long reviewId, Long userId);

    // reviewId와 userId를 통해 본인이 작성한 리뷰를 삭제
    Optional<StoreReview> deleteByReviewIdAndUserId(Long reviewId, Long userId);

    // 해당 가게 리뷰 삭제
    void deleteByStoreId(Long storeId);
}
