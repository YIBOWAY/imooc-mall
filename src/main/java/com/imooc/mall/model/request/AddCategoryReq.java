package com.imooc.mall.model.request;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * 描述:  添加目录请求类，为了避免传入参数过多而造成的工程量过大
 */
public class AddCategoryReq {//注解的使用，也突出了新建一个类的必要性，如果直接运用Category实体类，在复杂业务中可能拥有不同的逻辑；
    @Size(min = 2,max = 5,message = "name长度必须在2-5字符串之间")
    @NotNull(message = "name不能为空")//message为返回的信息
    private String name;

    @NotNull(message = "type不能为空")
    @Max(value = 3,message = "type必须小于等于3")
    private Integer type;

    @NotNull(message = "parentId不能为空")
    private Integer parentId;

    @NotNull(message = "orderNum不能为空")
    private Integer orderNum;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public Integer getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
    }

}
