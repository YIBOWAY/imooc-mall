package com.imooc.mall.common;

//通用返回对象

import com.imooc.mall.exception.ImoocMallExceptionEnum;

public class ApiRestResponse<T> {
    private Integer status;
    private String msg;
    private T data;

    private static final int OK_CODE = 10000;

    private static final String OK_MSG = "success";

    public ApiRestResponse(Integer status, String msg, T data) {
        this.status = status;
        this.msg = msg;
        this.data = data;
    }

    public ApiRestResponse(Integer status, String msg) {
        this.status = status;
        this.msg = msg;
    }

    public ApiRestResponse() {
        this(OK_CODE,OK_MSG);//直接调用两参数的构造方法
    }

    public static <T> ApiRestResponse<T> success(){//在静态方法前声明泛型
        return new ApiRestResponse<>();//调用无参构造方法，进而生成带有成功码和信息的返回值
    }

    public static <T> ApiRestResponse<T> success(T result){
        ApiRestResponse<T> response = new ApiRestResponse<>();
        response.setData(result);
        return response;
    }

    public static <T> ApiRestResponse<T> error(Integer code,String msg){//通过设定异常类，来统一返回报错信息
        return new ApiRestResponse<>(code,msg);
    }
    public static <T> ApiRestResponse<T> error(ImoocMallExceptionEnum ex){//通过设定异常类，来统一返回报错信息
        return new ApiRestResponse<>(ex.getCode(),ex.getMsg());
    }

    @Override
    public String toString() {
        return "ApiRestResponse{" +
                "status=" + status +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public static int getOkCode() {
        return OK_CODE;
    }

    public static String getOkMsg() {
        return OK_MSG;
    }
}
