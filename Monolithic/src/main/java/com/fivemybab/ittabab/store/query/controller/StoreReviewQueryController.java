package com.fivemybab.ittabab.store.query.controller;

import com.fivemybab.ittabab.security.util.CustomUserDetails;
import com.fivemybab.ittabab.store.command.application.dto.StoreReviewInfoDto;
import com.fivemybab.ittabab.store.query.dto.MyReviewResponse;
import com.fivemybab.ittabab.store.query.service.StoreReviewQueryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "Review", description = "가게 리뷰 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/store")
public class StoreReviewQueryController {

    private final StoreReviewQueryService storeReviewQueryService;


    /* 가게 리뷰 전체 조회 */
    @Operation(summary = "리뷰 전체 조회")
    @GetMapping("/review/list")
    public ResponseEntity<List<StoreReviewInfoDto>> storeReviewList() {

        List<StoreReviewInfoDto> storeReviewList = storeReviewQueryService.findStoreReviewList();

        return new ResponseEntity<>(storeReviewList, HttpStatus.OK);
    }

    /* 특정 가게 리뷰 전체 조회 */
    @Operation(summary = "특정 가게 리뷰 전체 조회")
    @GetMapping("/review/{storeId}")
    public ResponseEntity<List<StoreReviewInfoDto>> storeReviewByStoreIdList(Long storeId) {

        List<StoreReviewInfoDto> storeReviewList = storeReviewQueryService.findStoreReviewByStoreIdList(storeId);

        return new ResponseEntity<>(storeReviewList, HttpStatus.OK);
    }


    /* 가게 리뷰 상세 조회 */
    @Operation(summary = "리뷰 상세 조회")
    @GetMapping("/review/detail/{reviewId}")
    public ResponseEntity<StoreReviewInfoDto> storeReviewDetail(@PathVariable Long reviewId) {
        StoreReviewInfoDto storeReview = storeReviewQueryService.findStoreReviewById(reviewId);

        return new ResponseEntity<>(storeReview, HttpStatus.OK);
    }

    @Operation(summary = "특정 유저가 쓴 리뷰 전체 조회")
    @GetMapping("/review/user")
    public ResponseEntity<List<MyReviewResponse>> getMyReviewList(@AuthenticationPrincipal CustomUserDetails loginUser) {

        Long userId = loginUser.getUserId();
        List<MyReviewResponse> storeReviewList = storeReviewQueryService.findMyReviewList(userId);

        return new ResponseEntity<>(storeReviewList, HttpStatus.OK);
    }

}
