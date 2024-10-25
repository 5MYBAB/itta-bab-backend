package com.fivemybab.ittabab.board.query.service;

import com.fivemybab.ittabab.board.query.dto.PostCommentResponse;
import com.fivemybab.ittabab.board.query.dto.PostQueryDto;
import com.fivemybab.ittabab.board.query.mapper.PostQueryMapper;
import com.fivemybab.ittabab.security.util.CustomUserDetails;
import com.fivemybab.ittabab.user.query.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.javassist.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class PostQueryService {

    private final PostQueryMapper postQueryMapper;

    /* 게시물 목록 조회 (최신순) */
    @Transactional(readOnly = true)
    public List<PostQueryDto> findPostsByTime(CustomUserDetails loginUser) throws NotFoundException {
        Long courseId = loginUser.getCourseId(); // CustomUserDetails에서 courseId 가져오기

        List<PostQueryDto> posts = postQueryMapper.selectPostsByTime(courseId);

        if (posts.isEmpty()) {
            throw new NotFoundException("게시물이 없습니다.");
        }

        return posts;
    }

    /* 게시물 목록 조회 (좋아요 내림차순) */
    @Transactional(readOnly = true)
    public List<PostQueryDto> findPostsByLikesDesc(CustomUserDetails loginUser) throws NotFoundException {
        Long courseId = loginUser.getCourseId(); // CustomUserDetails에서 courseId 가져오기

        List<PostQueryDto> posts = postQueryMapper.selectPostsByLikesDesc(courseId);

        if (posts.isEmpty()) {
            throw new NotFoundException("게시물이 없습니다.");
        }

        return posts;
    }

    /* 게시물 목록 조회 (좋아요 오름차순) */
    @Transactional(readOnly = true)
    public List<PostQueryDto> findPostsByLikesAsc(CustomUserDetails loginUser) throws NotFoundException {
        Long courseId = loginUser.getCourseId(); // CustomUserDetails에서 courseId 가져오기

        List<PostQueryDto> posts = postQueryMapper.selectPostsByLikesAsc(courseId);

        if (posts.isEmpty()) {
            throw new NotFoundException("게시물이 없습니다.");
        }

        return posts;
    }

    @Transactional(readOnly = true)
    public PostQueryDto findPostsByPostId(Long postId) throws NotFoundException {
        PostQueryDto postDetail = postQueryMapper.selectPostByPostId(postId);

        if (postDetail==null) {
            throw new NotFoundException("해당 글이 없습니다.");
        }
        return postDetail;
    }
}
