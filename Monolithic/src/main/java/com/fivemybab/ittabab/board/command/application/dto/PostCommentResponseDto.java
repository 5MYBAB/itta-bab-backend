package com.fivemybab.ittabab.board.command.application.dto;

import com.fivemybab.ittabab.board.command.domain.aggregate.PostComment;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class PostCommentResponseDto {
    private Long postCommentId; // 댓글 ID
    private Long postId; // 게시글 ID
    private Long parentCommentId; // 부모 댓글 ID
    private Long userId; // 작성자 ID
    private String commentContent; // 댓글 내용
    private LocalDateTime createDate; // 생성일

    public PostCommentResponseDto(PostComment postComment) {
        this.postCommentId = postComment.getPostCommentId();
        this.postId = postComment.getPostId();
        this.parentCommentId = postComment.getParentCommentId();
        this.userId = postComment.getUserId();
        this.commentContent = postComment.getCommentContent();
        this.createDate = postComment.getCreateDate();
    }
}