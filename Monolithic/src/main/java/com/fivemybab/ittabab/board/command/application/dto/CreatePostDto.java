package com.fivemybab.ittabab.board.command.application.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class CreatePostDto {
    //게시판 글 테이블에 있는 게시글 제목, 내용, 블라인드 여부
    private String postTitle;
    private String postContent;
    private LocalDateTime createDate;
    private Long UserId;
}