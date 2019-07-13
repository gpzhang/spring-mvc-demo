package com.demo.spring.exception;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;

/**
 * @author haishen
 * @date 2019/07/12
 * <p>
 * 异常统一处理的实现原理
 */
@ControllerAdvice(annotations = {Controller.class})
public class MvcExceptionHandler {

    @ExceptionHandler({Throwable.class})
    @ResponseBody
    public String handleException(Throwable t, WebRequest request) {
        System.out.println("web层异常统一处理");
        System.out.println("异常信息:" + t.getMessage());
        if (t instanceof RuntimeException) {
            return "exception already handle";
        } else {
            return "service error,please waiting!";
        }
    }
}
