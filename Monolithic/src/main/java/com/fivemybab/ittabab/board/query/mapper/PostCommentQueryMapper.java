package com.fivemybab.ittabab.board.query.mapper;

import com.fivemybab.ittabab.board.query.dto.MyCommentResponse;
import com.fivemybab.ittabab.board.query.dto.PostCommentQueryDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PostCommentQueryMapper {
    // 댓글 많은 순으로 게시글 목록 조회
    List<PostCommentQueryDto> selectPostsByCommentCount();

    List<MyCommentResponse> findMyCommentList(Long userId);
}
