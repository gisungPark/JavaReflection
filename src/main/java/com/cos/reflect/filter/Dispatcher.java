package com.cos.reflect.filter;

import com.cos.reflect.annotation.RequestMapping;
import com.cos.reflect.controller.UserController;
import com.sun.org.apache.bcel.internal.generic.INSTANCEOF;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Enumeration;

public class Dispatcher implements Filter {

    private boolean isMatching = false;

    @Override
    public void doFilter(ServletRequest req,
                         ServletResponse res,
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
                isMatching = true;
                try {
                    Parameter[] params = method.getParameters();
                    String path = null;
                    if (params.length != 0) {

                        // 해당 Object를 리플렉션해서 set 함수 호출(사용자로부터 받은 파라미터 근거하에)
                        Object dtoInstance = params[0].getType().newInstance();
                        
//                        String username = request.getParameter("username");
//                        String password = request.getParameter("password");
//                        System.out.println(username + " " + password);

                        setData(dtoInstance, request);
                        path = (String) method.invoke(userController, dtoInstance);

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
        if(!isMatching){
            response.setContentType("text/html; charset=utf-8");
            PrintWriter out = response.getWriter();
            out.println("잘못된 주소 요청입니다. 404 에러");
            out.flush();
        }
    }

    private <T> void setData(T instance, HttpServletRequest request) {
        Enumeration<String> keys = request.getParameterNames(); //username, password
        //keys 값을 변형 username => setUsername;
        //keys 값을 변형 password => setPassword;

        while(keys.hasMoreElements()){ // 열거형 타입!!
            String key = (String) keys.nextElement();
            String methodKey = keyToMethodKey(key);

            Method[] methods = instance.getClass().getDeclaredMethods();
            System.out.println("instance.getClass() : " + instance.getClass());
            for (Method method : methods) {
                if(method.getName().equals(methodKey)){
                    try {
                        Parameter[] params = method.getParameters();
                        if(params[0].getType() == Integer.class){
                            method.invoke(instance, Integer.parseInt(request.getParameter(key)));
                        }else{
                            method.invoke(instance, request.getParameter(key));
                        }


                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    private String keyToMethodKey(String key){
        String firstKey = "set";
        String upperKey = key.substring(0, 1).toUpperCase();
        String remainKey = key.substring(1);
        return firstKey + upperKey + remainKey;
    }



    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void destroy() {

    }
}
