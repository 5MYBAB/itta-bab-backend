package com.fivemybab.ittabab.board.query.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class MyCommentResponse {

    private Long postCommentId;
    private Long postId;
    private Long userId;
    private String commentContent;
    private LocalDateTime createDate;
}
