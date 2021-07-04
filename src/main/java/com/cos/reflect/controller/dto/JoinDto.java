package com.cos.reflect.controller.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter
@ToString
public class JoinDto {

    private String username;
    private String password;
    private String email;
}
