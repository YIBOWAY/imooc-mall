package com.imooc.mall.common;

import com.google.common.collect.Sets;
import com.imooc.mall.exception.ImoocMallException;
import com.imooc.mall.exception.ImoocMallExceptionEnum;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Set;

/**
 *  常量信息
 */

@Component
//需要添加component注解使value能够注入
public class Constant {
    public static final String SALT = "8DFAF-Asdfsfe,,.]-";
    public static final String IMOOC_MALL_USER = "imooc_mall_user";


    public static String FILE_UPLOAD_DIR;//通过Value注解直接从配置文件中获取，由于是静态变量资源，无法通过Value注解获取，需要通过set方法

    @Value("${file.upload.dir}")
    public void setFileUploadDir(String fileUploadDir) {
        FILE_UPLOAD_DIR = fileUploadDir;
    }

    public interface ProductListOrderBy{
        Set<String> PRICE_ASC_DESC = Sets.newHashSet("price desc","price acs");
    }

    public interface SaleStatus{
        int NOT_SALE = 0;//商品下架状态
        int SALE = 1;//商品上架状态
    }

    public interface Cart{
        int NOT_CHECKED = 0;//购物车未被选中状态
        int CHECKED = 1;//购物车选中状态
    }

    public enum OrderStatusEnum{
        CANCELED(0,"用户已取消"),
        NOT_PAID(10,"未付款"),
        PAID(20,"已付款"),
        DELIVERED(30,"已发货"),
        FINISHED(40,"交易完成");
        private String value;
        private int code;

        OrderStatusEnum(int code,String value) {
            this.value = value;
            this.code = code;
        }

        public static OrderStatusEnum codeOf(int code){
            for (OrderStatusEnum orderStatusEnum :
                    values()) {
                if (orderStatusEnum.getCode() == code) {
                    return orderStatusEnum;
                }
            }
            throw new ImoocMallException(ImoocMallExceptionEnum.NO_ENUM);
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }
    }
}
/*
为什么要用接口来保存，为什么不用 类 来保存  （在类里面定义类，这个类是内部类，必须创建实例才能调用 如  new Constant().new SaleStatus().SALE ）
 */
