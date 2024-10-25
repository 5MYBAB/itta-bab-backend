package com.fivemybab.ittabab.user.query.controller;

import com.fivemybab.ittabab.security.util.CustomUserDetails;
import com.fivemybab.ittabab.user.query.dto.FriendResponse;
import com.fivemybab.ittabab.user.query.service.FriendQueryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "Friend", description = "친구 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/friend")
public class FriendQueryController {

    private final FriendQueryService friendQueryService;

    /* 친구 요청 조회 */
    @Operation(summary = "친구 요청 조회")
    @GetMapping("/request")
    public ResponseEntity<List<FriendResponse>> getFriendRequests(@AuthenticationPrincipal CustomUserDetails loginUser) {

        Long userId = loginUser.getUserId();   // 현재 로그인된 계정의 userId

        List<FriendResponse> friendRequests = friendQueryService.findFriendRequests(userId);

        return new ResponseEntity<>(friendRequests, HttpStatus.OK);
    }

    /* 친구 목록 조회 */
    @Operation(summary = "친구 목록 조회")
    @GetMapping("/list")
    public ResponseEntity<List<FriendResponse>> getFriendList(@AuthenticationPrincipal CustomUserDetails loginUser) {

        Long userId = loginUser.getUserId();   // 현재 로그인된 계정의 userId

        List<FriendResponse> friendRequests = friendQueryService.findFriendList(userId);

        return new ResponseEntity<>(friendRequests, HttpStatus.OK);
    }
}
