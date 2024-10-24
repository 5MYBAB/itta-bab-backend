package com.fivemybab.ittabab.user.query.service;

import com.fivemybab.ittabab.user.command.application.dto.UserDto;
import com.fivemybab.ittabab.user.command.domain.aggregate.UserInfo;
import com.fivemybab.ittabab.user.query.dto.MyPageResponse;
import com.fivemybab.ittabab.user.query.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserQueryService {

    private final UserMapper userMapper;

    public UserDto findById(Long id) {
        return userMapper.findById(id);
    }

    public List<UserDto> findAll() {
        return userMapper.findAll();
    }

    public Optional<UserInfo> findUserIdByLoginId(Authentication loginUserLoginId) {
        return userMapper.findByLoginId(loginUserLoginId.getName());
    }

    public MyPageResponse findMyPageInfoById(Long id) {
        return userMapper.findMyPageInfoById(id);
    }
}
