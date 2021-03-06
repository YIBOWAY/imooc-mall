package com.imooc.mall.exception;

//异常枚举

public enum  ImoocMallExceptionEnum {
    NEED_USER_NAME(10001,"用户名不能为空"),
    NEED_PASSWORD(10002,"密码不能为空"),
    PASSWORD_TOO_SHORT(10003,"密码长度不能少于8位"),
    NAME_EXISTED(10004,"不允许重名"),
    INSERT_FAILED(10005,"插入失败，请重试"),
    WRONG_PASSWORD(10006,"用户名或密码错误"),
    NEED_LOGIN(10007,"未登录，需要登录！"),
    UPDATE_FAILED(10008,"更新失败"),
    NEED_ADMIN(10009,"无管理员权限"),
    PARA_NOT_NULL(10010,"参数不能为空"),
    CREATE_FAILED(10011,"新增失败"),
    REQUEST_PARAM_ERROR(10012,"参数错误"),
    DELETE_FAILED(10013,"删除失败"),
    MKDIR_FAILED(10014,"文件夹创建失败"),
    UPLOAD_FAILED(10015,"图片上传失败"),
    NOT_SALE(10016,"商品不可售"),
    NOT_ENOUGH(10017,"商品库存不足"),
    NOT_IN_CART(10018,"商品不在购物车中"),
    CART_EMPTY(10019,"购物车为空"),
    NO_ENUM(10020,"未找到对应枚举"),
    NO_ORDER(10021,"订单为空"),
    NOT_YOUR_ORDER(10022,"订单不属于你"),
    WRONG_ORDER_STATUS(10023,"订单状态错误"),
    //区分业务异常及系统异常，通过异常代码；
    SYSTEM_ERROR(20000,"系统异常");
    /**
     * 异常码
     */
    Integer code;
    /**
     * 异常信息
     */
    String msg;

    ImoocMallExceptionEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
