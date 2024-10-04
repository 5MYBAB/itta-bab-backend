package com.fivemybab.ittabab.user.command.application.controller;

import com.fivemybab.ittabab.user.command.application.dto.CreateUserRequest;
import com.fivemybab.ittabab.user.command.application.dto.MailRequestDto;
import com.fivemybab.ittabab.user.command.application.dto.UpdateUserRequest;
import com.fivemybab.ittabab.user.command.application.service.UserCommandService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "User", description = "회원 관련 API")
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@Slf4j
public class UserCommandController {

    private final Environment env;
    private final UserCommandService userCommandService;

    @GetMapping("/health")
    public String healthCheck() {

        return "I'm OK. Port = " + env.getProperty("local.server.port");
    }

    /* 이메일 인증 */
    @Operation(summary = "이메일 인증")
    @PostMapping("/signup-request")
    public ResponseEntity<String> signupRequest(@RequestBody MailRequestDto mailRequestDto) {

        userCommandService.sendCodeToEmail(mailRequestDto);
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
    @PutMapping("/mypage/{userNo}")
    public ResponseEntity<String> modifyUser(@PathVariable Long userNo, @RequestBody UpdateUserRequest updateUserRequest) {

        userCommandService.modifyUser(userNo, updateUserRequest);

        return ResponseEntity.ok().body("회원 정보 수정 완료");
    }

    /* 회원 정보 삭제 */
    @Operation(summary = "회원 정보 삭제")
    @DeleteMapping("/mypage/{userNo}")
    public ResponseEntity<String> deleteUser(@PathVariable Long userNo) {

        userCommandService.deleteUser(userNo);

        return ResponseEntity.ok().body("회원 삭제 완료");
    }


}
