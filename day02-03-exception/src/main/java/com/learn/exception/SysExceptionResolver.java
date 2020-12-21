package com.learn.exception;

import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//异常处理器
public class SysExceptionResolver implements HandlerExceptionResolver {

    /**
     * 实现HandlerExceptionResolver接口的异常处理方法
     * 处理异常业务逻辑
     * @param httpServletRequest
     * @param httpServletResponse
     * @param o
     * @param e 抛出的异常对象
     * @return
     */
    @Override
    public ModelAndView resolveException(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) {
        //获取异常对象
        SysException sysException = null;

        if (e instanceof SysException){
            sysException = (SysException)e;   //假如异常为我们自定义类型，则抛出异常
        } else {
            sysException = new SysException("系统正在维护...");     //假如异常不是定义好的类型，那么新建异常
        }

        //创建ModelAndView对象
        ModelAndView mv = new ModelAndView();
        mv.addObject("errorMsg", sysException.getMessage());
        mv.setViewName("error");

        return mv;
    }

}
