package com.fivemybab.ittabab.board.query.controller;

import com.fivemybab.ittabab.board.query.dto.MyPostResponse;
import com.fivemybab.ittabab.board.query.dto.PostQueryDto;
import com.fivemybab.ittabab.board.query.service.PostQueryService;
import com.fivemybab.ittabab.security.util.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.javassist.NotFoundException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/post")
@Tag(name = "Post", description = "게시판 관련 API")
public class PostQueryController {

    private final PostQueryService postQueryService;

    // 게시물 목록 조회 (최신순)
    @Operation(summary = "게시글 목록 조회(최신순)", description = "게시판의 글을 최신순으로 조회합니다.")
    @GetMapping("/recent")
    public List<PostQueryDto> findPostsByTime(@AuthenticationPrincipal CustomUserDetails userId) throws NotFoundException {
        return postQueryService.findPostsByTime(userId);
    }

    // 게시물 목록 조회 (좋아요 내림차순)
    @Operation(summary = "게시글 목록 조회(좋아요 내림차순)", description = "게시판의 글을 좋아요 내림차순으로 조회합니다.")
    @GetMapping("/likes/desc")
    public List<PostQueryDto> findPostsByLikesDesc(@AuthenticationPrincipal CustomUserDetails loginUser) throws NotFoundException {
        return postQueryService.findPostsByLikesDesc(loginUser);
    }

    // 게시물 목록 조회 (좋아요 오름차순)
    @Operation(summary = "게시글 목록 조회(좋아요 오름차순)", description = "게시판의 글을 좋아요 오름차순으로 조회합니다.")
    @GetMapping("/likes/asc")
    public List<PostQueryDto> findPostsByLikesAsc(@AuthenticationPrincipal CustomUserDetails loginUser) throws NotFoundException {
        return postQueryService.findPostsByLikesAsc(loginUser);
    }

    @Operation(summary = "특정 유저가 쓴 게시글 목록 조회", description = "특정 유저가 쓴 게시글을 조회합니다.")
    @GetMapping("/list")
    public List<MyPostResponse> findMyPostList(@AuthenticationPrincipal CustomUserDetails loginUser) throws NotFoundException {

        Long userId = loginUser.getUserId();
        return postQueryService.findMyPostList(userId);
    }


}
