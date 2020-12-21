package com.learn.controller;

import com.learn.exception.SysException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

//异常处理的web
@Controller
@RequestMapping("test")
public class ExceptionController {

    @RequestMapping("testException")
    public String testException() throws SysException {
        System.out.println("testException...");

        try {
            //模拟异常
            int num = 1 / 0;
        } catch (Exception e) {
            e.printStackTrace();
            //抛出自定义异常信息
            throw new SysException("查询用户出现错误");
        }

        return "success";
    }
}
