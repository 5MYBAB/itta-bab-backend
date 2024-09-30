package com.fivemybab.ittabab.board.command.application.controller;

import com.fivemybab.ittabab.board.command.application.dto.CreatePostDto;
import com.fivemybab.ittabab.board.command.application.dto.UpdatedPostDto;
import com.fivemybab.ittabab.board.command.application.service.PostCommandService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
@RequestMapping("/post")
@Tag(name = "Post", description = "게시판 관련 API")
public class PostCommandController {

    private final PostCommandService postCommandService;


    //게시판 작성(create)
    @PostMapping
    @Operation(summary = "게시판 글 작성")
    public ResponseEntity<CreatePostDto> createPost(@RequestBody CreatePostDto createPostDto, @RequestParam Long userId) {
        createPostDto.setCreateDate(LocalDateTime.now());
        CreatePostDto createdPost = postCommandService.createPost(createPostDto, userId);
        return new ResponseEntity<>(createdPost, HttpStatus.CREATED);
    }

    //게시판 삭제(delete)
    @DeleteMapping("/{postId}")
    @Operation(summary = "게시판 글 삭제")
    public ResponseEntity<Void> deletePost(@PathVariable final Long postId){
        postCommandService.deletePost(postId);
        return ResponseEntity.noContent().build();
    }

    //게시판 수정 (update)
    @PutMapping("/{postId}")
    @Operation(summary = "게시판 글 수정")
    public ResponseEntity<Void> updatePost(
            @PathVariable Long postId,
            @RequestBody @Valid UpdatedPostDto updatedPostDto) {

        postCommandService.updatePost(postId, updatedPostDto);
        return ResponseEntity.ok().build();
    }
}