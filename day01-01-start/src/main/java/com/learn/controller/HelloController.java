package com.learn.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

//控制器
@Controller
@RequestMapping("/user")
public class HelloController {

    @RequestMapping(path = "/hello")  //请求映射
    public String sayHello() {
        System.out.println("Hello SpringMVC");
        return "success";
    }

    @RequestMapping(value = "/testRequestMapping", params = {"username=username"}, headers = {"Accept"})
    public String testRequestMapping() {
        System.out.println("测试RequestMapping注解...");
        return "success";
    }

    @RequestMapping(path = "/testMethod", method = {RequestMethod.POST})
    public String testMethod() {
        System.out.println("testMethod...");
        return "success";
    }

    @RequestMapping("testServletAPI")
    public String testServletAPI(HttpServletRequest request) {
        System.out.println("执行了testServletAPI...");
        System.out.println(request);
        HttpSession session = request.getSession();
        System.out.println(session);
        ServletContext context = session.getServletContext();
        System.out.println(context);
        return "success";
    }
}
