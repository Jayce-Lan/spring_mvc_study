package com.learn.controller;

import com.learn.pojo.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

import java.util.Date;
import java.util.Map;

//常用注解

@Controller
@RequestMapping("/anno")
@SessionAttributes(value = {"msg"})     //将msg="李华存入session域中"
public class AnnoController {
    /**
     * 传入参数并返回
     * @param username @RequestParam(name = "name", required = false)中
     *                 name/value 属性与传入参数的属性像对应，
     *                 即使与我们的变量名username不对应，因为加了注解，也可以成功传入
     *                 required 属性决定了是否需要传入参数，默认为true
     * @return 返回成功页面
     */
    @RequestMapping("/testRequestParam")
    public String testRequestParam(@RequestParam(name = "name", required = false) String username) {
        System.out.println(username);
        return "success";
    }

    /**
     * 获取请求体
     * @param body 由于有@RequestBody注解，因此获取到的不会是body这个属性，而是完整的请求体
     * @return 返回成功页面
     */
    @RequestMapping("/RequestBody")
    public String testRequestBody(@RequestBody String body) {
        System.out.println("RequestBody...");
        System.out.println(body);
        return "success";
    }

    /**
     * 占位符
     * @param id 由于存入了@PathVariable，前端页面只需要存入testPathVariable/10（id值）即可
     *              实例：<a href="anno/testPathVariable/10">测试PathVariable</a><br/>
     *           url中的占位符必须与注解中的name相对应
     * @return
     */
    @RequestMapping("/testPathVariable/{uid}")
    public String testPathVariable(@PathVariable(name = "uid") String id) {
        System.out.println("testPathVariable...");
        System.out.println(id);
        return "success";
    }

    /**
     * 获取请求头的值
     * @param header @RequestHeader("Accept")注解声明后获取请求头的值
     * @return 返回成功页面
     */
    @RequestMapping("testRequestHeader")
    public String testRequestHeader(@RequestHeader("Accept") String header) {
        System.out.println("testRequestHeader...");
        System.out.println(header);
        return "success";
    }

    /**
     * 获取Cookie的值
     * @param cookie 返回cookie的值
     * @return 返回成功页面
     */
    @RequestMapping("testCookieValue")
    public String testCookieValue(@CookieValue(value = "JSESSIONID") String cookie) {
        System.out.println("testCookieValue...");
        System.out.println(cookie);
        return "success";
    }

    /**
     * 该方法会拿到showModelAttribute方法返回的user
     * @param user @ModelAttribute(value = "user1")加入注解后，会在showModelAttributeNoReturn方法中取得userMap集合的值
     * @return
     */
    @RequestMapping("testModelAttribute")
    public String testModelAttribute(@ModelAttribute(value = "user1") User user) {
        System.out.println("testModelAttribute...");
        System.out.println(user);
        return "success";
    }

    /**
     * 无返回值方法使用该注解时，需要声明一个map集合存放获取到的参数
     * @param username 从前端获取的参数
     * @param age      从前端获取的参数
     * @param userMap  将参数封装成一个map集合传给有需要调用的方法
     */
    @ModelAttribute
    public void showModelAttributeNoReturn(String username, Integer age, Map<String, User> userMap) {
        User user = new User();
        user.setUsername(username);
        user.setAge(age);
        user.setDate(new Date());
        userMap.put("user1", user);
    }

    /**
     * 由于有了@ModelAttribute注解，该方法会优先执行，
     *
     * 有返回值的执行方法：
     * 由于优先执行的缘故，可以获取到前端传来的对象以及属性
     * 因此可以通过它获取到数据库的对象
     *
     * @param username 传入值后我们就可以获取到数据库对象
     * @param age 传入年龄
     * @return 返回查询出来的结果
     */
    //    @ModelAttribute
    public User showModelAttribute(String username, Integer age) {
        System.out.println("ModelAttribute执行了...");
        //模拟获取数据库对象
        User user = new User();
        user.setUsername(username);
        user.setAge(age);
        user.setDate(new Date());
        return user;
    }

    /**
     * 向session中，以键值对形式存入值
     * jsp：<a href="anno/testSessionAttributes">测试SessionAttributes</a><br/>
     * @param model 存入的值
     * @return 返回成功页面
     */
    @RequestMapping("testSessionAttributes")
    public String testSessionAttributes(Model model) {
        model.addAttribute("msg", "李华");
        return "success";
    }

    /**
     * 获取session中的值（由于是键值对形式，因此get方法获取值）
     * jsp：<a href="anno/getSessionAttributes">getSessionAttributes</a><br/>
     * @param modelMap 获取到的值
     * @return 返回成功页面
     */
    @RequestMapping("getSessionAttributes")
    public String getSessionAttributes(ModelMap modelMap) {
        modelMap.get("msg");
        System.out.println(modelMap);
        return "success";
    }

    /**
     * 清除应用中session的值
     * jsp：<a href="anno/deleteSessionAttributes">删除SessionAttributes</a><br/>
     * @param status 获取到session
     * @return 返回成功页面
     */
    @RequestMapping("deleteSessionAttributes")
    public String deleteSessionAttributes(SessionStatus status) {
        status.setComplete();
        return "success";
    }
}
