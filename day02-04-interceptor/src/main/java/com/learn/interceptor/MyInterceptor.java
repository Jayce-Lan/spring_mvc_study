package com.learn.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//自定义拦截器
public class MyInterceptor implements HandlerInterceptor {

    /**
     * 重写预处理方法，controller 方法执行前
     * @param request
     * @param response
     * @param handler
     * @return 返回true表示放行，false表示不放行，即为controller中被拦截的方法不会执行
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("拦截器预处理执行了...");

        //该方法中也可以进行请求转发
//        request.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(request, response);

        return true;
    }

    /**
     * 后处理方法 controller 执行后， success 执行前
     * @param request
     * @param response
     * @param handler
     * @param modelAndView
     * @throws Exception
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        System.out.println("拦截器后处理方法执行了...");

        //该方法内部也可以进行请求转发
//        request.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(request, response);
    }

    /**
     * 该方法在controller方法完成并跳转后执行（即为success之后执行）
     * @param request
     * @param response
     * @param handler
     * @param ex
     * @throws Exception
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        System.out.println("拦截器最后方法执行了...");
    }
}
