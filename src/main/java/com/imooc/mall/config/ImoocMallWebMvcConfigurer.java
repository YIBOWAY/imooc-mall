package com.imooc.mall.config;

import com.imooc.mall.common.Constant;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 *   配置地址映射
 */
@Configuration
public class ImoocMallWebMvcConfigurer implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        registry.addResourceHandler("/admin/**").addResourceLocations("classpath:/static/admin/");
        registry.addResourceHandler("/images/**").addResourceLocations("file:" + Constant.FILE_UPLOAD_DIR);
        registry.addResourceHandler("swagger-ui.html").addResourceLocations(
                "classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**").addResourceLocations(
                "classpath:/META-INF/resources/webjars/");
    }
}
/**
 * ImoocMallWebMvcConfig类是配置静态资源的映射路径。以registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");为例，
 * 当请求swagger-ui.html时，会去/META-INF/resources/目录下找。
 * META-INF目录指的是springfox-swagger-ui.jar中的。
 */
