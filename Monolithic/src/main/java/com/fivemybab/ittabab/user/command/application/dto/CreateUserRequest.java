package com.fivemybab.ittabab.user.command.application.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class CreateUserRequest {

    private String username;
    private String loginId;
    private String pwd;
    private String email;
    private String phone;
    private LocalDate birth;
    private Long courseId;

}
