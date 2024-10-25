package com.fivemybab.ittabab.user.query.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class FriendResponse {

    private Long userId;
    private String username;
}
