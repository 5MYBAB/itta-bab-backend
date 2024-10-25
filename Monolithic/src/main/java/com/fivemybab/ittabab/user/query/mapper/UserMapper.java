package com.fivemybab.ittabab.user.query.mapper;

import com.fivemybab.ittabab.user.command.application.dto.UserDto;
import com.fivemybab.ittabab.user.query.dto.MyPageResponse;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Optional;

@Mapper
public interface UserMapper {

    UserDto findById(Long id);

    List<UserDto> findAll();

    Optional<String> findByLoginId(String loginId);

    MyPageResponse findMyPageInfoById(Long id);

    Optional<String> findIdByNameAndPhone(String username, String phone);

    Optional<Long> findPwd(String username, String loginId, String email);
}
