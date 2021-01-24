package com.imooc.mall.model.query;

import java.util.List;

/**
 * 描述:  查询商品列表的query
 */
public class ProductListQuery {

    private String keyword;

    private List<Integer> categoryIds;//要查询一个目录下所有子目录的id

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public List<Integer> getCategoryIds() {
        return categoryIds;
    }

    public void setCategoryIds(List<Integer> categoryIds) {
        this.categoryIds = categoryIds;
    }
}
