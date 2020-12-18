package com.learn.controller;

import com.learn.pojo.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("user")
public class UserController {

    /**
     * 返回值类型为String
     * @param model 存储键值对的参数
     * @return 返回String类型的字符串用于匹配物理路径下的文件
     */
    @RequestMapping("testString")
    public String testString(Model model) {
        System.out.println("testString执行了...");

        //模拟从数据库获取对象
        User user = new User();
        user.setUsername("李华");
        user.setPassword("1234");
        user.setAge(20);
        model.addAttribute("user", user);

        return "success";
    }

    /**
     * 返回一个ModelAndView对象，该对象是SpringMVC提供的对象
     * @return
     */
    @RequestMapping("testModelAndView")
    public ModelAndView testModelAndView() {
        ModelAndView mv = new ModelAndView();

        //模拟从数据库获取对象
        User user = new User();
        user.setUsername("李华");
        user.setPassword("1234");
        user.setAge(20);

        //存储对象
        mv.addObject("user", user);
        //跳转页面，由视图解析器进行解析
        mv.setViewName("success");

        return mv;
    }

    /**
     * 执行之后：文.件[/WEB-INF/pages/user/testVoidDefault.jsp] 未找到
     * 在默认情况下，该方法在默认情况下会返回@RequestMapping注解路径下的jsp文件
     */
    @RequestMapping("testVoidDefault")
    public void testVoidDefault() {
        System.out.println("testVoidDefault执行了...");
    }

    /**
     * 使用重定向或者请求转发达到跳转目的
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping("testVoidRequest")
    public void testVoidRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
        System.out.println("testVoidRequest执行了...");

        //编写请求转发的程序
            //一次请求，不会修改请求路径，需要写上转发的路径，这里不会使用字符解析器
    //            request.getRequestDispatcher("/WEB-INF/pages/success.jsp").forward(request, response);

            //重定向，两次请求，最后会跳转到index.jsp当中
    //            response.sendRedirect(request.getContextPath() + "/index.jsp");

        //设置中文乱码
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

        //直接写入浏览器，向浏览器进行响应
        response.getWriter().print("成功");

        return; //不会再跳转到下面的方法
    }

    /**
     * 关键字的方式完成请求
     * @return 由于不再使用视图解析器，因此此处必须写 forward: + 完整路径
     */
    @RequestMapping("testForward")
    public String testForward() {
        System.out.println("testForward执行了...");
        //使用转发的固定格式：forward:+路径，此处不再由视图解析器进行解析
        return "forward:/WEB-INF/pages/success.jsp";
    }

    /**
     * 由于是重定向，因此必须写根目录的文件
     * @return 标准格式：redirect: + 根目录文件名
     */
    @RequestMapping("testRedirect")
    public String testRedirect() {
        System.out.println("testRedirect执行了...");
        return "redirect:/index.jsp";
    }
}
