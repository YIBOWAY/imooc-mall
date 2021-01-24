package com.imooc.mall.common;

import com.google.common.collect.Sets;
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
}
