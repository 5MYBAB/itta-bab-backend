package com.fivemybab.ittabab.board.query.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class MyPostResponse {

    private Long postId;
    private Long userId;
    private String postTitle;
    private String postContent;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;
    private boolean isBlinded;
}
