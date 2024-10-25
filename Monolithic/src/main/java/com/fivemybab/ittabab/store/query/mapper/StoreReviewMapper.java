package com.fivemybab.ittabab.store.query.mapper;

import com.fivemybab.ittabab.store.command.application.dto.StoreReviewInfoDto;
import com.fivemybab.ittabab.store.query.dto.MyReviewResponse;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface StoreReviewMapper {

    /* 전체 리뷰 조회 */
    List<StoreReviewInfoDto> findStoreReviewList();

    /* 특정 가게 전체 리뷰 조회 */
    List<StoreReviewInfoDto> findStoreReviewByStoreIdList(Long id);

    /* 특정 가게 리뷰 조회 */
    StoreReviewInfoDto findStoreReviewById(Long id);

    List<MyReviewResponse> findMyReviewList(Long userId);
}
