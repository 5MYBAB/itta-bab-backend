package com.fivemybab.ittabab.user.command.application.controller;

import com.fivemybab.ittabab.security.util.CustomUserDetails;
import com.fivemybab.ittabab.user.command.application.dto.FriendRequestDTO;
import com.fivemybab.ittabab.user.command.application.dto.UpdateFriendRequest;
import com.fivemybab.ittabab.user.command.application.service.FriendCommandService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Tag(name = "Friend", description = "친구 관련 API")
@RestController
@RequestMapping("/friend")
@RequiredArgsConstructor
public class FriendCommandController {

    private final FriendCommandService friendCommandService;

    /* 친구 신청 기능 */
    @Operation(summary = "친구 신청")
    @PostMapping("/request")
    public ResponseEntity<String> sendFriendRequest(@RequestBody FriendRequestDTO friendRequest,
                                                    @AuthenticationPrincipal CustomUserDetails loginUser) {

        Long userId = loginUser.getUserId();

        friendCommandService.sendFriendRequest(userId, friendRequest);
        return ResponseEntity.ok("친구 신청을 보냈습니다.");
    }

    /* 친구 수락 기능 */
    @Operation(summary = "친구 수락")
    @PutMapping("/accept")
    public ResponseEntity<String> acceptFriendRequest(@RequestBody UpdateFriendRequest updateFriendRequest,
                                                      @AuthenticationPrincipal CustomUserDetails loginUser) {

        Long userId = loginUser.getUserId();

        friendCommandService.acceptFriendRequest(userId, updateFriendRequest);
        return ResponseEntity.ok("친구 요청을 수락하였습니다.");
    }

    /* 친구 거절 기능 */
    @Operation(summary = "친구 거절")
    @PutMapping("/reject")
    public ResponseEntity<String> rejectFriendRequest(@RequestBody UpdateFriendRequest updateFriendRequest,
                                                      @AuthenticationPrincipal CustomUserDetails loginUser) {

        Long userId = loginUser.getUserId();

        friendCommandService.rejectFriendRequest(userId, updateFriendRequest);
        return ResponseEntity.ok("친구 요청을 거절하였습니다.");
    }

    /* 친구 삭제 기능 */
    @Operation(summary = "친구 삭제")
    @DeleteMapping
    public ResponseEntity<String> deleteFriend(@RequestBody Map<String, Long> requestBody,
                                               @AuthenticationPrincipal CustomUserDetails loginUser) {
        Long userId = loginUser.getUserId();
        Long friendUserId = requestBody.get("friendUserId");

        friendCommandService.deleteFriend(userId, friendUserId);
        return ResponseEntity.ok("친구를 삭제했습니다.");
    }
}
