package com.fivemybab.ittabab.good.command.application.controller;

import com.fivemybab.ittabab.good.command.application.dto.GoodDto;
import com.fivemybab.ittabab.good.command.application.service.GoodCommandService;
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
public class GoodCommandController {

    private final GoodCommandService goodCommandService;

    /* 좋아요 등록 및 취소 */
    @Operation(summary = "좋아요 등록 및 취소")
    @PostMapping
    public ResponseEntity<Void> toggleLike(@RequestBody GoodDto goodDTO, @AuthenticationPrincipal CustomUserDetails loginUser) {
        goodDTO.setUserId(loginUser.getUserId()); // 로그인 사용자 ID 설정
        goodCommandService.toggleLike(goodDTO);
        return ResponseEntity.ok().build();
    }
}
