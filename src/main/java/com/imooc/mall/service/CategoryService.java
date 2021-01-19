package com.imooc.mall.service;

import com.imooc.mall.model.pojo.Category;
import com.imooc.mall.model.request.AddCategoryReq;

/**
 * 描述:  目录分类service接口
 */
public interface CategoryService {
    void add(AddCategoryReq addCategoryReq);

    void update(Category updateCategory);

    void delete(Integer id);
}
