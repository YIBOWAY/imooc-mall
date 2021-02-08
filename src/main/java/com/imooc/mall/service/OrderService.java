package com.imooc.mall.service;

import com.github.pagehelper.PageInfo;
import com.imooc.mall.model.request.CreateOrderReq;
import com.imooc.mall.model.vo.CartVO;
import com.imooc.mall.model.vo.OrderVO;
import org.springframework.stereotype.Service;

import java.util.List;

public interface OrderService {

    String create(CreateOrderReq createOrderReq);

    OrderVO detail(String orderCode);

    PageInfo listForCustomer(Integer pageNum, Integer pageSize);

    void cancel(String orderCode);
}
