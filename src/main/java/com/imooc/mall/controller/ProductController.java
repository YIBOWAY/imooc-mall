package com.imooc.mall.controller;

import com.imooc.mall.common.ApiRestResponse;
import com.imooc.mall.model.pojo.Product;
import com.imooc.mall.service.ProductService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 描述:  前台商品controller
 */
@RestController
public class ProductController {
    @Autowired
    ProductService productService;

    @ApiOperation("前台商品详情")
    @PostMapping("/product/detail")
    public ApiRestResponse detail(@RequestParam Integer id){
        Product product = productService.details(id);
        return ApiRestResponse.success(product);
    }
}
