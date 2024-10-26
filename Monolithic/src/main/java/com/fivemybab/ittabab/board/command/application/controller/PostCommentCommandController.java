package com.fivemybab.ittabab.board.command.application.controller;


import com.fivemybab.ittabab.board.command.application.dto.CreatePostCommentDto;
import com.fivemybab.ittabab.board.command.application.dto.PostCommentResponseDto;
import com.fivemybab.ittabab.board.command.application.dto.UpdatePostCommentDto;
import com.fivemybab.ittabab.board.command.application.service.PostCommentCommandService;
import com.fivemybab.ittabab.board.command.domain.aggregate.PostComment;

import com.fivemybab.ittabab.security.util.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/postComment")
@RequiredArgsConstructor
@Tag(name = "PostComment", description = "댓글 관련 API")
public class PostCommentCommandController {

    private final PostCommentCommandService postCommentCommandService;

    // 댓글 생성
    @Operation(summary = "댓글 등록")
    @PostMapping
    public ResponseEntity<PostCommentResponseDto> createPostComment(@RequestBody CreatePostCommentDto createPostCommentDto,
                                                                    @AuthenticationPrincipal CustomUserDetails loginUser) {
        PostCommentResponseDto responseDto = postCommentCommandService.createPostComment(createPostCommentDto, loginUser.getUserId());
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    // 대댓글 등록
    @Operation(summary = "대댓글 등록")
    @PostMapping("/replies") // 대댓글 등록을 위한 새로운 엔드포인트
    public ResponseEntity<PostCommentResponseDto> createReply(@RequestBody CreatePostCommentDto createPostCommentDto,
                                                              @AuthenticationPrincipal CustomUserDetails loginUser) {
        PostCommentResponseDto responseDto = postCommentCommandService.createPostComment(createPostCommentDto, loginUser.getUserId());
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    // 댓글 수정
    @PutMapping("/{commentId}")
    @Operation(summary = "댓글 수정", description = "댓글을 수정합니다.")
    public ResponseEntity<PostComment> updateComment(@PathVariable Long commentId, @RequestBody UpdatePostCommentDto updatePostCommentDto) {
        updatePostCommentDto.setCommentId(commentId);
        PostComment updatedComment = postCommentCommandService.updateComment(updatePostCommentDto);
        return ResponseEntity.ok(updatedComment);
    }

    // 댓글 삭제 (댓글 ID 기준)
    @DeleteMapping("/{commentId}")
    @Operation(summary = "댓글 삭제", description = "댓글을 삭제합니다.")
    public ResponseEntity<Void> deleteComment(@PathVariable Long commentId) {
        postCommentCommandService.deleteComment(commentId);
        return ResponseEntity.noContent().build();
    }


}