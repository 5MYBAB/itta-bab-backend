package com.fivemybab.ittabab.user.query.controller;

import com.fivemybab.ittabab.security.util.CustomUserDetails;
import com.fivemybab.ittabab.user.command.application.dto.UserDto;
import com.fivemybab.ittabab.user.query.dto.MyPageResponse;
import com.fivemybab.ittabab.user.query.service.UserQueryService;
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

@Tag(name = "User", description = "회원 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserQueryController {

    private final UserQueryService userQueryService;

    @Operation(summary = "마이페이지 조회")
    @GetMapping("/mypage")
    public ResponseEntity<MyPageResponse> getMyPageInfo(@AuthenticationPrincipal CustomUserDetails loginUser) {
        Long userId = loginUser.getUserId();
        MyPageResponse myPageResponse = userQueryService.findMyPageInfoById(userId);
        return new ResponseEntity<>(myPageResponse, HttpStatus.OK);
    }

    /* 특정 회원 조회 */
    @Operation(summary = "특정 회원 조회")
    @GetMapping("/admin/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long id) {
        UserDto userDto = userQueryService.findById(id);
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }

    /* 회원 전체 조회 */
    @Operation(summary = "회원 전체 조회")
    @GetMapping("/admin/all")
    public ResponseEntity<List<UserDto>> getAllUsers() {
        List<UserDto> users = userQueryService.findAll();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }
}
