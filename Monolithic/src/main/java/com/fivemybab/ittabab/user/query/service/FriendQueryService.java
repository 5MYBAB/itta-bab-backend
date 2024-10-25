package com.fivemybab.ittabab.user.query.service;

import com.fivemybab.ittabab.user.command.domain.aggregate.FriendStatus;
import com.fivemybab.ittabab.user.command.domain.aggregate.UserInfo;
import com.fivemybab.ittabab.user.query.dto.FriendResponse;
import com.fivemybab.ittabab.user.query.mapper.FriendMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FriendQueryService {

    private final FriendMapper friendMapper;

    public List<FriendResponse> findFriendRequests(Long id) {

        return friendMapper.findFriendList(id, FriendStatus.PENDING);
    }

    public List<FriendResponse> findFriendList(Long id) {

        List<FriendResponse> friendList1 = friendMapper.findFriendList(id, FriendStatus.ACCEPTED);
        List<FriendResponse> friendList2 = friendMapper.findFriendListReverse(id, FriendStatus.ACCEPTED);

        /* 두 리스트가 모두 비어 있으면 예외 발생   */
        if ((friendList1 == null || friendList1.isEmpty()) &&
                (friendList2 == null || friendList2.isEmpty())) {
            throw new IllegalArgumentException("해당 친구 관계를 찾을 수 없습니다.");
        }

        /* 두 리스트를 합침 */
        List<FriendResponse> friendList = new ArrayList<>();
        if (friendList1 != null) {
            friendList.addAll(friendList1);
        }
        if (friendList2 != null) {
            friendList.addAll(friendList2);
        }
        return friendList;
    }
}
