package com.fivemybab.ittabab.good.query.controller;

import com.fivemybab.ittabab.good.command.domain.aggregate.Target;
import com.fivemybab.ittabab.good.query.dto.IsLikedResponse;
import com.fivemybab.ittabab.good.query.service.GoodQueryService;
import com.fivemybab.ittabab.security.util.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Good", description = "좋아요 관련 API")
@RestController
@RequestMapping("/good")
@RequiredArgsConstructor
public class GoodQueryController {

    private final GoodQueryService goodQueryService;

    /* 특정 대상의 좋아요 개수 조회 */
    @Operation(summary = "좋아요 개수 조회")
    @GetMapping("/{target}/{targetId}")
    public ResponseEntity<Integer> countGoods(@PathVariable("target") Target target, @PathVariable("targetId") Long targetId) {

        int count = goodQueryService.countGoods(target, targetId);
        return ResponseEntity.ok(count);
    }

    @Operation(summary = "좋아요 상태 조회")
    @GetMapping
    public ResponseEntity<Boolean> checkLikeStatus(@RequestParam Target target, @RequestParam Long targetId, @AuthenticationPrincipal CustomUserDetails loginUser) {
        IsLikedResponse isLikedResponse = new IsLikedResponse();
        isLikedResponse.setUserId(loginUser.getUserId());
        isLikedResponse.setTarget(target);
        isLikedResponse.setTargetId(targetId);

        boolean isLiked = goodQueryService.isLiked(isLikedResponse);
        return ResponseEntity.ok(isLiked);
    }
}
