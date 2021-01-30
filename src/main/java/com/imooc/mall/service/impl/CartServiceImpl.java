package com.imooc.mall.service.impl;

import com.imooc.mall.common.Constant;
import com.imooc.mall.exception.ImoocMallException;
import com.imooc.mall.exception.ImoocMallExceptionEnum;
import com.imooc.mall.model.dao.ProductMapper;
import com.imooc.mall.model.pojo.Product;
import com.imooc.mall.model.vo.CartVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 描述:  购物车service实现类
 */
@Service
public class CartServiceImpl {
    @Autowired
    ProductMapper productMapper;

    public List<CartVO> add(Integer userId, Integer productId, Integer count){
        validProduct(productId,count);

    }

    private void validProduct(Integer productId,Integer count){
        Product product = productMapper.selectByPrimaryKey(productId);
        //判断商品是否存在，是否上架
        if (product == null || product.getStatus().equals(Constant.SaleStatus.NOT_SALE)) {//这里创建一个常量来保存商品状态代码，这样方便后续扩充；
            throw new ImoocMallException(ImoocMallExceptionEnum.NOT_SALE);
        }
        //判断商品库存
        if (count > product.getStock()) {
            throw new ImoocMallException(ImoocMallExceptionEnum.NOT_ENOUGH);
        }
    }
}
