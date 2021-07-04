package com.cos.reflect.controller.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter
@ToString
public class LoginDto {
    private String username;
    private String password;
}
