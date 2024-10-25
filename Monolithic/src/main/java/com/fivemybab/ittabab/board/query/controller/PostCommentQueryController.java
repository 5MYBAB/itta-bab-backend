package com.fivemybab.ittabab.board.query.controller;

import com.fivemybab.ittabab.board.query.dto.MyCommentResponse;
import com.fivemybab.ittabab.security.util.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import com.fivemybab.ittabab.board.query.dto.PostCommentQueryDto;
import com.fivemybab.ittabab.board.query.service.PostCommentQueryService;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.javassist.NotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/postComment")
@RequiredArgsConstructor
@Tag(name = "PostComment", description = "댓글 관련 API")
public class PostCommentQueryController {

    private final PostCommentQueryService postCommentQueryService;

    /* 댓글 많은 순으로 게시글 목록 조회 */
    @Operation(summary = "댓글 많은 순 게시글 목록", description = "댓글이 많은 순서로 조회합니다.")
    @GetMapping
    public ResponseEntity<List<PostCommentQueryDto>> getPostsByCommentCount() throws NotFoundException {
        List<PostCommentQueryDto> posts = postCommentQueryService.findPostsByCommentCount();
        return ResponseEntity.ok(posts);
    }

    @Operation(summary = "특정 유저가 쓴 댓글 목록", description = "특정 유저가 쓴 댓글을 조회합니다.")
    @GetMapping("/user")
    public ResponseEntity<List<MyCommentResponse>> getMyCommentList(@AuthenticationPrincipal CustomUserDetails loginUser) throws NotFoundException {

        Long userId = loginUser.getUserId();

        List<MyCommentResponse> posts = postCommentQueryService.findMyCommentList(userId);
        return ResponseEntity.ok(posts);
    }
}
