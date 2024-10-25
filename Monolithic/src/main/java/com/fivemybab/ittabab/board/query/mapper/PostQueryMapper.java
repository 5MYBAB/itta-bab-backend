package com.fivemybab.ittabab.board.query.mapper;

import com.fivemybab.ittabab.board.query.dto.MyPostResponse;
import com.fivemybab.ittabab.board.query.dto.PostQueryDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PostQueryMapper {
  
    // 최신순으로 게시물 목록 조회
    List<PostQueryDto> selectPostsByTime(Long courseId);

    // 좋아요가 가장 많은 게시물 목록 조회
    List<PostQueryDto> selectPostsByLikesDesc(Long courseId);

    // 좋아요가 가장 적은 게시물 목록 조회
    List<PostQueryDto> selectPostsByLikesAsc(Long courseId);

    List<MyPostResponse> findMyPostList(Long userId);
}
