package com.dyq.demo.config;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice//在异常类上添加ControllerAdvice注解
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    private static final String ERROR = "error";

    /**
     * 不管是访问返回视图接口，还是返回json串接口，只要抛出的excetion异常全部由这个方法拦截，并统一返回json串
     * 统一异常拦截 rest接口
     *
     * @param request
     * @param ex
     * @return
     */
    @ExceptionHandler(Exception.class)//Exception 这个是所有异常的父类
    //定义异常统一返回json，即使是返回视图的接口
    @ResponseBody
    Map<String, String> handleControllerException(HttpServletRequest request, Throwable ex) {
        HttpStatus status = getStatus(request);
        Map<String, String> map = new HashMap();
        map.put("code", String.valueOf(status.value()));
        map.put("msg", ex.getMessage());
        //这时会返回我们统一的异常json
        return map;
    }

    private HttpStatus getStatus(HttpServletRequest request) {
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        if (statusCode == null) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return HttpStatus.valueOf(statusCode);
    }

    /**
     * 拦截自定义异常 Exception rest接口
     *
     * @param request
     * @param ex
     * @return
     */
    @ExceptionHandler(RuntimeException.class)
    @ResponseBody
    Map<String, String> handleControllerCustomException(HttpServletRequest request, Exception ex) {
        HttpStatus status = getStatus(request);
        Map<String, String> map = new HashMap();
        map.put("code", String.valueOf(status.value()));
        map.put("msg", "customer error msg");
        return map;
    }
}