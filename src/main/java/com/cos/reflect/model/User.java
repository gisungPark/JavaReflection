package com.cos.reflect.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter
@ToString
public class User {
    private Integer id;
    private String username;
    private String  password;
    private String email;

}
