package com.cos.reflect.controller;

import com.cos.reflect.annotation.RequestMapping;
import com.cos.reflect.controller.dto.JoinDto;
import com.cos.reflect.controller.dto.LoginDto;
import com.cos.reflect.model.User;

public class UserController {

    @RequestMapping("/join") // username, password, email
    public String join(JoinDto dto){
        System.out.println("join 함수 호출됨!");
        System.out.println(dto.toString());
        return "/";
    }

    @RequestMapping("/login") // username, password
    public String login(LoginDto dto){
        System.out.println("login 함수 호출됨!");
        System.out.println(dto.toString());
        return "/";
    }

    @RequestMapping("/list")
    public String list(User user){
        System.out.println("list 함수 호출됨!");
        System.out.println(user.toString());
        return "/";
    }

    @RequestMapping("/user")
    public String user(){
        System.out.println("user call!");
        return "/";
    }
}
