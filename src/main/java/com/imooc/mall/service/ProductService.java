package com.imooc.mall.service;

import com.imooc.mall.model.pojo.Product;
import com.imooc.mall.model.request.AddProductReq;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;

/**
 * 描述:  商品添加service接口
 */
public interface ProductService {

    void add(AddProductReq addProductReq);

    String upload(HttpServletRequest httpServletRequest, MultipartFile file);

    URI getHost(URI uri);

    void update(Product updateProduct);

    void delete(Integer id);

    void batchUpdateSellStatus(Integer[] ids, Integer sellStatus);
}
