package com.fivemybab.ittabab.user.query.mapper;

import com.fivemybab.ittabab.user.command.domain.aggregate.FriendStatus;
import com.fivemybab.ittabab.user.query.dto.FriendResponse;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface FriendMapper {

    List<FriendResponse> findFriendList(@Param("id") Long id, @Param("friendStatus") FriendStatus friendStatus);

    List<FriendResponse> findFriendListReverse(@Param("id") Long id, @Param("friendStatus") FriendStatus friendStatus);

}
