package com.cos.reflect.filter;

import com.cos.reflect.annotation.RequestMapping;
import com.cos.reflect.controller.UserController;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

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
//        for (Method method : methods) {
//            if(endPoint.equals(method.getName())) {
//                try {
//                    method.invoke(userController);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        }

        for (Method method : methods) {
            Annotation annotation = method.getDeclaredAnnotation(RequestMapping.class);
            RequestMapping requestMapping = (RequestMapping) annotation; // Down Casting

            if (requestMapping.value().equals(endPoint)) {
                try {
                    Parameter[] params = method.getParameters();
                    String path = "/";
                    if (params.length != 0) {
//                        System.out.println("params[0].getType(): "+params[0].getType()); // class를 알면 객체를 생성할 수 있다.
                        Object dtoInstance = params[0].getType().newInstance();
                    }else{
                        path = (String)method.invoke(userController);
                    }


                    // 내부에서 실행하기 때문에 필터를 안탄다!!
                    RequestDispatcher dispatcher = request.getRequestDispatcher(path);
                    dispatcher.forward(request,response);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            }
        }
    }

    @Override
    public void destroy() {

    }
}
