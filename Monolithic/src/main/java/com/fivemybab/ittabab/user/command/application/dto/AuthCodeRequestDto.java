package com.fivemybab.ittabab.user.command.application.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AuthCodeRequestDto {

    private String email;
    private String authCode;
}
