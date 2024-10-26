package com.fivemybab.ittabab.board.command.application.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreatePostCommentDto {
    private Long postId;          // 댓글이 달릴 게시글 ID
    private String commentContent; // 댓글 내용
    private Long parentCommentId;
    public String getContent() {
        return commentContent;
    }
}
