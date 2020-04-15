package com.almond.blog.commons.exception.handle;

import com.almond.blog.commons.exception.exceptions.NoDataException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@ControllerAdvice
public class MyExceptionHandle {
    @ExceptionHandler(RuntimeException.class)
    public String runtionExceptionHandle(RuntimeException e) {
        log.error("请求发生异常:{}", e.getMessage(), e);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("message", e.getMessage());
        return "error/500";
    }
    /**
     * 对自定义异常的统一处理
     *
     * @param e
     * @return
     * @annotation ExceptionHandler 参数异常类
     */
    @ExceptionHandler(NoDataException.class)
    public String noLonginExceptionHandle(NoDataException e) {
        log.error(e.getMessage());
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("message", e.getMessage());
        return "error/404";
    }
}
