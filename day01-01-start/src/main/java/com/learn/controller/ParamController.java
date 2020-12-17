package com.learn.controller;

import com.learn.pojo.AccoountMap;
import com.learn.pojo.Account;
import com.learn.pojo.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

//请求参数的绑定

@Controller
@RequestMapping("/param")
public class ParamController {

    /**
     * 方法中添加参数可以获取到url传回来的参数
     * 封装简单参数
     *
     * @param username 获取url携带的参数
     * @return 返回成功页面
     */
    @RequestMapping("/testParam")
    public String testParam(String username, String password) {
        System.out.println("执行了testParam...");
        System.out.println(username);
        System.out.println(password);
        return "success";
    }

    /**
     * 将数据封装到JavaBean的类当中
     * @param account 在表单中输入对应的属性，就会传值到该对象中
     * @return 返回请求成功页面
     */
    @RequestMapping("/saveAccount")
    public String saveAccount(Account account) {
        System.out.println("执行了保存用户...");
        System.out.println(account);
        return "success";
    }

    /**
     * 将数据封装到JavaBean的类当中
     * 对象属性中包含对象
     *
     * @param account 在表单中输入对应的属性，就会传值到该对象中
     * @return 返回请求成功页面
     */
    @RequestMapping("/saveAccountAndUser")
    public String saveAccountAndUser(Account account) {
        System.out.println("执行了保存用户...");
        System.out.println(account);
        return "success";
    }

    /**
     * 将数据封装到JavaBean的类当中
     * 对象属性中包含集合
     *
     * @param accoountMap 在表单中输入对应的属性，就会传值到该对象中
     * @return 返回请求成功页面
     */
    @RequestMapping("/saveAccountAndMap")
    public String saveAccountAndMap(AccoountMap accoountMap) {
        System.out.println("执行了保存用户...");
        System.out.println(accoountMap);
        return "success";
    }

    /**
     * 传入自定义类型的值，做一个自定义类型转换器
     * @param user 传入参数
     * @return 返回成功页面
     */
    @RequestMapping("/saveUser")
    public String saveUser(User user) {
        System.out.println(user);
        return "success";
    }

}
