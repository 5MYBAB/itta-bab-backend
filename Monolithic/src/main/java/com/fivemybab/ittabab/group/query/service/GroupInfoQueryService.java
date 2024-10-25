package com.fivemybab.ittabab.group.query.service;

import com.fivemybab.ittabab.exception.NotFoundException;
import com.fivemybab.ittabab.group.query.dto.GroupInfoDto;
import com.fivemybab.ittabab.group.query.mapper.GroupInfoMapper;
import com.fivemybab.ittabab.group.query.mapper.GroupUserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GroupInfoQueryService {

    private final GroupInfoMapper groupInfoMapper;
    private final GroupUserMapper groupUserMapper;

    public List<GroupInfoDto> findGroupByGroupStatus(Long loginUserId) {
        Long courseId = findCourseIdByLoginId(loginUserId);
        System.out.println("courseId = " + courseId);
        return groupInfoMapper.findGroupByGroupStatus(courseId);
    }

    public GroupInfoDto findGroupByGroupId(Long groupId) {
        GroupInfoDto foundGroupInfo = groupInfoMapper.findGroupByGroupId(groupId);

        if (foundGroupInfo == null) {
            throw new NotFoundException(groupId + "에 해당하는 모임이 없습니다.");
        }

        return foundGroupInfo;
    }

    /* 모임에 가입된 사용자 아이디 가져오는 메소드 */
    public List<Long> findGroupUserByGroupId(Long groupId) {
        return groupUserMapper.findUserByGroupId(groupId);
    }

    /* 로그인 Id("test01")를 사용하여 유저 courseId 알아오는 메소드 */
    public Long findCourseIdByLoginId(Long userId) {
        return groupInfoMapper.findCourseIdByLoginId(userId);
    }

    public GroupInfoDto findCurrentGroup() {
        return groupInfoMapper.findCurrentGroup();
    }

    public List<GroupInfoDto> findAllGroup() {
        return groupInfoMapper.findAllGroup();
    }

    /* 로그인 Id로 사용자가 참여중인 모임 반환 메소드 */
    public List<GroupInfoDto> findGroupByUserId(Long userId, Long courseId) {
        return groupInfoMapper.findGroupByUserId(userId, courseId);
    }
}
