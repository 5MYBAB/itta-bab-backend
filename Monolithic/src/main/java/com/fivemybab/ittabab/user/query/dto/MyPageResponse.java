package com.fivemybab.ittabab.user.query.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class MyPageResponse {
    private String username;
    private String phone;
    private String className;
    private int seasonNum;
}
