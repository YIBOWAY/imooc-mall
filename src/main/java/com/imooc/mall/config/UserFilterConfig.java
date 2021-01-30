package com.imooc.mall.config;

import com.imooc.mall.filter.UserFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 描述:  用户过滤器
 */
@Configuration
public class UserFilterConfig {
    @Bean
    public UserFilter userFilter(){
        return new UserFilter();
    }

    @Bean(name = "userFilterConf")
    public FilterRegistrationBean userFilterConf(){
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        filterRegistrationBean.setFilter(userFilter());//将adminFilter放入整个的链路中去
        filterRegistrationBean.addUrlPatterns("/cart/*");
        filterRegistrationBean.addUrlPatterns("/order/*");
        filterRegistrationBean.setName("userFilterConf");
        return filterRegistrationBean;
    }
}
