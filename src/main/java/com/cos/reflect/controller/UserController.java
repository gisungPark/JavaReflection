package com.cos.reflect.controller;

import com.cos.reflect.annotation.RequestMapping;
import com.cos.reflect.controller.dto.JoinDto;
import com.cos.reflect.controller.dto.LoginDto;

public class UserController {

    @RequestMapping("/join")
    public String join(JoinDto dto){
        System.out.println("join call!");
        return "/";
    }

    @RequestMapping("/login")
    public String login(LoginDto dto){
        System.out.println("login call!");
        return "/";
    }

    @RequestMapping("/user")
    public String user(){
        System.out.println("user call!");
        return "/";
    }
}
