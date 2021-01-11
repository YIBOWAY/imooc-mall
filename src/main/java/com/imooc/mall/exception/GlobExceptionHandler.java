package com.imooc.mall.exception;

import com.imooc.mall.common.ApiRestResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 处理统一异常的handler
 */

@ControllerAdvice//拦截异常
public class GlobExceptionHandler {
    //异常信息需要添加到日志中
    private final Logger log = LoggerFactory.getLogger(GlobExceptionHandler.class);

    @ExceptionHandler(Exception.class)//拦截的异常类型
    @ResponseBody
    public Object handleException(Exception e){//系统异常
        log.error("Default Exception: ",e);
        return ApiRestResponse.error(ImoocMallExceptionEnum.SYSTEM_ERROR);
    }

    @ExceptionHandler(ImoocMallException.class)
    @ResponseBody
    public Object handleImoocMallException(ImoocMallException e){//业务异常
        log.error("ImoocMallException: ",e);
        return ApiRestResponse.error(e.getCode(),e.getMessage());
    }
}
