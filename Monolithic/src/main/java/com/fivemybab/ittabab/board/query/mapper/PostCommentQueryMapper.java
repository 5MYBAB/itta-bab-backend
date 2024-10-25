package com.fivemybab.ittabab.board.query.mapper;

import com.fivemybab.ittabab.board.query.dto.MyCommentResponse;
import com.fivemybab.ittabab.board.query.dto.PostCommentQueryDto;
import com.fivemybab.ittabab.board.query.dto.PostCommentResponse;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PostCommentQueryMapper {
    // 댓글 많은 순으로 게시글 목록 조회
    List<PostCommentQueryDto> selectPostsByCommentCount();

    List<MyCommentResponse> findMyCommentList(Long userId);

    // 해당 게시물의 댓글 리스트
    List<PostCommentResponse> selectCommentByPostId(Long postId);
}
