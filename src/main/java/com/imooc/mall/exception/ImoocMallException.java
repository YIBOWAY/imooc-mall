package com.imooc.mall.exception;

/**
 * 统一异常
 */
public class ImoocMallException extends RuntimeException{//RuntimeException可以不对异常做抛出处理
    private final Integer code;
    private final String message;

    public ImoocMallException(Integer code,String message){
        this.code = code;
        this.message = message;
    }

    public ImoocMallException(ImoocMallExceptionEnum exceptionEnum){
        this(exceptionEnum.getCode(),exceptionEnum.getMsg());
    }

    public Integer getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
