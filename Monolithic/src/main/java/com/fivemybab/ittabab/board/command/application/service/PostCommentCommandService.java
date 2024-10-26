package com.fivemybab.ittabab.board.command.application.service;

import com.fivemybab.ittabab.board.command.application.dto.CreatePostCommentDto;
import com.fivemybab.ittabab.board.command.application.dto.PostCommentResponseDto;
import com.fivemybab.ittabab.board.command.application.dto.UpdatePostCommentDto;
import com.fivemybab.ittabab.board.command.domain.aggregate.PostComment;
import com.fivemybab.ittabab.board.command.domain.repository.PostCommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class PostCommentCommandService {

    private final PostCommentRepository postCommentRepository;

    // 댓글 생성 (C)
    @Transactional
    public PostCommentResponseDto createPostComment(CreatePostCommentDto createPostCommentDto, Long userId) {

        PostComment postComment = PostComment.builder()
                .postId(createPostCommentDto.getPostId())
                .userId(userId)
                .commentContent(createPostCommentDto.getContent())
                .parentCommentId(createPostCommentDto.getParentCommentId())
                .createDate(LocalDateTime.now())
                .isBlinded(false)
                .build();


        PostComment savedComment = postCommentRepository.save(postComment);


        return new PostCommentResponseDto(savedComment);
    }

    // 댓글 수정 (U)
    @Transactional
    public PostComment updateComment(UpdatePostCommentDto updatePostCommentDto) {
        // 필수 필드 검증
        if (updatePostCommentDto.getCommentId() == null) {
            throw new IllegalArgumentException("댓글 ID는 필수입니다.");
        }
        PostComment postComment = postCommentRepository.findById(updatePostCommentDto.getCommentId())
                .orElseThrow(() -> new IllegalArgumentException("댓글을 찾을 수 없습니다."));

        // 수정할 내용이 있는지 검증
        if (updatePostCommentDto.getCommentContent() == null || updatePostCommentDto.getCommentContent().isEmpty()) {
            throw new IllegalArgumentException("수정할 댓글 내용은 필수입니다.");
        }

        postComment.updateCommentContent(updatePostCommentDto.getCommentContent());
        return postCommentRepository.save(postComment);
    }

    // 댓글 삭제 (D)
    @Transactional
    public void deleteComment(Long commentId) {
        if (commentId == null) {
            throw new IllegalArgumentException("댓글 ID는 필수입니다.");
        }
        if (!postCommentRepository.existsById(commentId)) {
            throw new IllegalArgumentException("댓글을 찾을 수 없습니다.");
        }
        postCommentRepository.deleteById(commentId);
    }

    // 게시글 ID를 기준으로 모든 댓글 삭제
    @Transactional
    public void deleteCommentsByPostId(Long postId) {
        if (postId == null) {
            throw new IllegalArgumentException("게시글 ID는 필수입니다.");
        }
        postCommentRepository.deleteCommentByPostId(postId);
    }
}
