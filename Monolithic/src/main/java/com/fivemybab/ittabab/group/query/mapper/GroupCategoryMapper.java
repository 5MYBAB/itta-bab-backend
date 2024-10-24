package com.fivemybab.ittabab.group.query.mapper;

import com.fivemybab.ittabab.group.query.dto.GroupCategoryDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface GroupCategoryMapper {

    // 전체 카테고리 조회
    List<GroupCategoryDto> findGroupCategory();
}
