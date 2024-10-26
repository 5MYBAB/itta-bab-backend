package com.fivemybab.ittabab.user.command.application.controller;

import com.fivemybab.ittabab.security.util.CustomUserDetails;
import com.fivemybab.ittabab.user.command.application.dto.AuthCodeRequestDto;
import com.fivemybab.ittabab.user.command.application.dto.CreateUserRequest;
import com.fivemybab.ittabab.user.command.application.dto.MailRequestDto;
import com.fivemybab.ittabab.user.command.application.dto.UpdateUserRequest;
import com.fivemybab.ittabab.user.command.application.service.UserCommandService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Tag(name = "User", description = "회원 관련 API")
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@Slf4j
public class UserCommandController {

    private final UserCommandService userCommandService;

    /* 이메일 인증 */
    @Operation(summary = "이메일 인증")
    @PostMapping("/email-request")
    public ResponseEntity<String> emailRequest(@RequestBody MailRequestDto mailRequestDto) {

        userCommandService.sendCodeToEmail(mailRequestDto);
        return ResponseEntity.ok().body("이메일 인증코드를 전송하였습니다. 메일을 확인해주세요.");
    }

    @Operation(summary = "이메일 인증 확인")
    @PostMapping("/email-check")
    public ResponseEntity<String> emailCheck(@RequestBody AuthCodeRequestDto authCodeRequestDto) {
        if (userCommandService.checkAuthCode(authCodeRequestDto)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    @Operation(summary = "비밀번호 찾기")
    @GetMapping("/find-pwd")
    public ResponseEntity<String> findPwdAndRandomPwd(@RequestParam String username, @RequestParam String loginId, @RequestParam String email) {

        userCommandService.findPwdAndRandomPwd(username, loginId, email);

        return ResponseEntity.ok().body("이메일 인증코드를 전송하였습니다. 메일을 확인해주세요.");
    }

    /* 회원 가입 기능 */
    @Operation(summary = "회원 가입")
    @PostMapping("/signup")
    public ResponseEntity<Void> createUser(@RequestBody CreateUserRequest newUser) {

        userCommandService.createUser(newUser);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /* 회원 정보 수정 기능*/
    @Operation(summary = "회원 정보 수정")
    @PutMapping("/mypage")
    public ResponseEntity<String> modifyUser(@AuthenticationPrincipal CustomUserDetails loginUser, @RequestBody UpdateUserRequest updateUserRequest) {

        Long userId = loginUser.getUserId();
        userCommandService.modifyUser(userId, updateUserRequest);

        return ResponseEntity.ok().body("회원 정보 수정 완료");
    }

    /* 회원 정보 삭제 */
    @Operation(summary = "회원 정보 삭제")
    @DeleteMapping("/mypage")
    public ResponseEntity<String> deleteUser(@AuthenticationPrincipal CustomUserDetails loginUser) {

        Long userId = loginUser.getUserId();
        userCommandService.deleteUser(userId);

        return ResponseEntity.ok().body("회원 삭제 완료");
    }


}
