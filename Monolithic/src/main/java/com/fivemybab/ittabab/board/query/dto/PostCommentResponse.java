package com.fivemybab.ittabab.board.query.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class PostCommentResponse {
    private Long postCommentId;
    private Long postId;
    private Long parentCommentId;
    private Long userId;
    private String commentContent;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;
    private boolean isBlinded;
}
