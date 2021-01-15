package com.imooc.mall.exception;

import com.imooc.mall.common.ApiRestResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

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
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public ApiRestResponse handleMethodArgumentNotValidException(MethodArgumentNotValidException e){
        log.error("MethodArgumentNotValidException: ",e);
        return handleBindingResult(e.getBindingResult());
    }

    private ApiRestResponse handleBindingResult(BindingResult result){
        //把异常处理为对外暴露的提示
        List<String> list = new ArrayList<>();
        if (result.hasErrors()) {//里面是否包含错误
            List<ObjectError> allErrors = result.getAllErrors();
            for (ObjectError objectError:allErrors){
                String message = objectError.getDefaultMessage();//获取错误信息
                list.add(message);
            }
        }
        if (list.size() == 0){
            return ApiRestResponse.error(ImoocMallExceptionEnum.REQUEST_PARAM_ERROR);
        }
        return ApiRestResponse.error(ImoocMallExceptionEnum.REQUEST_PARAM_ERROR.getCode(),list.toString());
    }



}
