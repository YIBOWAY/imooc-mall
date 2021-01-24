package com.imooc.mall.service;

import com.github.pagehelper.PageInfo;
import com.imooc.mall.model.pojo.Product;
import com.imooc.mall.model.request.AddProductReq;
import com.imooc.mall.model.request.ProductListReq;
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

    PageInfo listForAdmin(Integer pageNum, Integer pageSize);

    Product details(Integer id);

    PageInfo list(ProductListReq productListReq);
}
