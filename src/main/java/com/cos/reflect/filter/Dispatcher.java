package com.cos.reflect.filter;

import com.cos.reflect.controller.UserController;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.text.html.HTMLDocument;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Dispatcher implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res,
                                FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;

//        System.out.println(request.getContextPath());
//        System.out.println(request.getRequestURI());
//        System.out.println(request.getRequestURL());

        String endPoint = request.getRequestURI().replaceAll(request.getContextPath(), "");
        System.out.println("endPoint: " + endPoint);
        
//        if(endPoint.equals("/login")) System.out.println("login 호출됨");
//        if(endPoint.equals("/join")) System.out.println("join 호출됨");

        UserController userController = new UserController();
        Method[] methods = userController.getClass().getDeclaredMethods();

        // 리플렉션 -> 메서드를 런타임 시점에서 찾아내서 실행
        for (Method method : methods) {
            if(endPoint.equals(method.getName())) {
                try {
                    method.invoke(userController);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void destroy() {

    }
}
