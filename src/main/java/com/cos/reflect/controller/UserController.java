package com.cos.reflect.controller;

import com.cos.reflect.annotation.RequestMapping;

public class UserController {

    @RequestMapping("/join")
    public void join(){
        System.out.println("join call!");
    }

    @RequestMapping("/login")
    public void login(){
        System.out.println("login call!");
    }

    @RequestMapping("/user")
    public void user(){
        System.out.println("user call!");
    }
}
