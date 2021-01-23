package com.imooc.mall.controller;

import com.imooc.mall.common.ApiRestResponse;
import com.imooc.mall.common.Constant;
import com.imooc.mall.exception.ImoocMallException;
import com.imooc.mall.exception.ImoocMallExceptionEnum;
import com.imooc.mall.model.pojo.Product;
import com.imooc.mall.model.request.AddProductReq;
import com.imooc.mall.model.request.UpdateProductReq;
import com.imooc.mall.service.ProductService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.UUID;

/**
 * 描述:  后台商品管理controller
 */
@RestController
public class ProductAdminController {
    @Autowired
    ProductService productService;

    @PostMapping("/admin/product/add")
    @ResponseBody
    public ApiRestResponse addProduct(@Valid @RequestBody AddProductReq addProductReq){
        productService.add(addProductReq);
        return ApiRestResponse.success();
    }

    @PostMapping("/admin/upload/file")
    public ApiRestResponse upload(HttpServletRequest httpServletRequest, @RequestParam("file")MultipartFile file){//通过HTTP请求获取相关参数，如URL等；
        String newFileName = productService.upload(httpServletRequest, file);
        try {
            return ApiRestResponse.success(productService.getHost(new URI(httpServletRequest.getRequestURI() + ""))+"/images/" + newFileName);
        } catch (URISyntaxException e) {
            e.printStackTrace();
            return ApiRestResponse.error(ImoocMallExceptionEnum.UPLOAD_FAILED);
        }
    }

    @ApiOperation("后台更新商品")
    @PostMapping("/admin/product/update")
    public ApiRestResponse updateProduct(@Valid @RequestBody UpdateProductReq updateProductReq){
        Product product = new Product();
        BeanUtils.copyProperties(updateProductReq,product);
        productService.update(product);
        return ApiRestResponse.success();
    }

    @ApiOperation("后台删除商品")
    @PostMapping("/admin/product/delete")
    public ApiRestResponse deleteProduct(Integer id){//并不是很推荐，一般用上架下架来改变
       productService.delete(id);
       return ApiRestResponse.success();
    }

    @ApiOperation("后台批量上下架商品接口")
    @PostMapping("/admin/product/batchUpdateSellStatus")
    public ApiRestResponse batchUpdateSellStatus(@RequestParam Integer[] ids,@RequestParam Integer sellStatus){
        if (ids == null || ids.length == 0 || sellStatus == null){
            throw new ImoocMallException(ImoocMallExceptionEnum.PARA_NOT_NULL);
        }
        productService.batchUpdateSellStatus(ids,sellStatus);
        return ApiRestResponse.success();
    }

}
