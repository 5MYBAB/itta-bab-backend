package com.fivemybab.ittabab.group.query.mapper;


import com.fivemybab.ittabab.group.query.dto.GroupInfoDto;
import com.fivemybab.ittabab.user.command.application.dto.UserDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.security.core.Authentication;

import java.util.List;

@Mapper
public interface GroupInfoMapper {

    /* 전체 모임 조회*/
    List<GroupInfoDto> findGroupByGroupStatus(Long courseId);

    /* 특정 모임 조회 */
    GroupInfoDto findGroupByGroupId(Long groupId);

    /* 로그인 아이디를 통한 회원 아이디 조회 */
    Long findUserIdByLoginId(String loginUserLoginId);

    /* 로그인 아이디를 통한 교육과정 Id */
    Long findCourseIdByLoginId(@Param("userId") Long userId);

    GroupInfoDto findCurrentGroup();

    List<GroupInfoDto> findAllGroup();

    List<GroupInfoDto> findGroupByUserId(Long userId, Long courseId);
}
