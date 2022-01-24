package com.doodl6.flink.web.advice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 统一处理异常
 */
@Slf4j
@ControllerAdvice
public class ExceptionAdvice {

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public String handleException(Exception e) {
        if (e instanceof IllegalArgumentException) {
            return e.getMessage();
        } else if (e instanceof HttpRequestMethodNotSupportedException) {
            return "不支持的请求方式";
        } else {
            log.error("请求出现异常", e);
            return "服务器未知异常";
        }
    }

}
