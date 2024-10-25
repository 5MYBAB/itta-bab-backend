package com.fivemybab.ittabab.board.query.service;

import com.fivemybab.ittabab.board.query.dto.MyCommentResponse;
import com.fivemybab.ittabab.board.query.dto.PostCommentQueryDto;
import com.fivemybab.ittabab.board.query.mapper.PostCommentQueryMapper;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.javassist.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostCommentQueryService {

    private final PostCommentQueryMapper postCommentQueryMapper;

    /* 댓글 많은 순으로 게시글 목록 조회 */
    @Transactional(readOnly = true)
    public List<PostCommentQueryDto> findPostsByCommentCount() throws NotFoundException {
        List<PostCommentQueryDto> posts = postCommentQueryMapper.selectPostsByCommentCount();

        if (posts.isEmpty()) {
            throw new NotFoundException("게시물이 없습니다.");
        }

        return posts;
    }

    public List<MyCommentResponse> findMyCommentList(Long userId) throws NotFoundException {

        List<MyCommentResponse> comments = postCommentQueryMapper.findMyCommentList(userId);

        if (comments.isEmpty()) {
            throw new NotFoundException("게시물이 없습니다.");
        }

        return comments;
    }
}
