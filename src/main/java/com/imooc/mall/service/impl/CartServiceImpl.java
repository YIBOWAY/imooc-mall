package com.imooc.mall.service.impl;

import com.imooc.mall.common.Constant;
import com.imooc.mall.exception.ImoocMallException;
import com.imooc.mall.exception.ImoocMallExceptionEnum;
import com.imooc.mall.model.dao.CartMapper;
import com.imooc.mall.model.dao.ProductMapper;
import com.imooc.mall.model.pojo.Cart;
import com.imooc.mall.model.pojo.Product;
import com.imooc.mall.model.vo.CartVO;
import com.imooc.mall.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 描述:  购物车service实现类
 */
@Service
public class CartServiceImpl implements CartService {
    @Autowired
    ProductMapper productMapper;

    @Autowired
    CartMapper cartMapper;

    @Override
    public List<CartVO> list(Integer userId){
        List<CartVO> cartVOS = cartMapper.selectList(userId);
        for (int i = 0; i < cartVOS.size(); i++) {//为每一个购物车对象添加所有价格属性
            CartVO cartVO = cartVOS.get(i);
            cartVO.setTotalPrice(cartVO.getPrice() * cartVO.getQuantity());
        }
        return cartVOS;
    }

    @Override
    public List<CartVO> add(Integer userId, Integer productId, Integer count){//直接返回购物车列表，避免多次调用，提高性能
        validProduct(productId,count);

        Cart cart = cartMapper.selectCartByUserIdAndProductId(userId, productId);
        if (cart == null){
            //这个商品之前不在购物车里，需要新增记录
            cart = new Cart();
            cart.setProductId(productId);
            cart.setUserId(userId);
            cart.setQuantity(count);
            cart.setSelected(Constant.Cart.CHECKED);
            cartMapper.insertSelective(cart);
        }else {
            //这个商品已经在购物车里，则数量相加
            count = cart.getQuantity() + count;
            Cart cartNew = new Cart();
            cartNew.setQuantity(count);
            cartNew.setId(cart.getId());
            cartNew.setProductId(cart.getProductId());
            cartNew.setUserId(cart.getUserId());
            cartNew.setSelected(Constant.Cart.CHECKED);//这里是否选中状态，要根据具体的业务逻辑进行分析；如有的厂商认为增加了数量证明用户想购买，自动选中；有的则不是；
            cartMapper.updateByPrimaryKeySelective(cartNew);
        }
        return this.list(userId);

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


    @Override
    public List<CartVO> update(Integer userId, Integer productId, Integer count){//直接返回购物车列表，避免多次调用，提高性能
        validProduct(productId,count);

        Cart cart = cartMapper.selectCartByUserIdAndProductId(userId, productId);
        if (cart == null){
            //这个商品之前不在购物车里，无法更新
           throw new ImoocMallException(ImoocMallExceptionEnum.UPDATE_FAILED);
        }else {
            //这个商品已经在购物车里，则更新数量
            Cart cartNew = new Cart();
            cartNew.setQuantity(count);
            cartNew.setId(cart.getId());
            cartNew.setProductId(cart.getProductId());
            cartNew.setUserId(cart.getUserId());
            cartNew.setSelected(Constant.Cart.CHECKED);//这里是否选中状态，要根据具体的业务逻辑进行分析；如有的厂商认为增加了数量证明用户想购买，自动选中；有的则不是；
            cartMapper.updateByPrimaryKeySelective(cartNew);
        }
        return this.list(userId);

    }

    @Override
    public List<CartVO> delete(Integer userId, Integer productId){//直接返回购物车列表，避免多次调用，提高性能
        Cart cart = cartMapper.selectCartByUserIdAndProductId(userId, productId);
        if (cart == null){
            //这个商品之前不在购物车里，无法删除
            throw new ImoocMallException(ImoocMallExceptionEnum.DELETE_FAILED);
        }else {
            //这个商品已经在购物车里，则可以删除
           cartMapper.deleteByPrimaryKey(cart.getId());
        }
        return this.list(userId);

    }


}
