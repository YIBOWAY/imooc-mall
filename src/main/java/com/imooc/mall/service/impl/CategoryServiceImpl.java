package com.imooc.mall.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.imooc.mall.exception.ImoocMallException;
import com.imooc.mall.exception.ImoocMallExceptionEnum;
import com.imooc.mall.model.dao.CategoryMapper;
import com.imooc.mall.model.pojo.Category;
import com.imooc.mall.model.request.AddCategoryReq;
import com.imooc.mall.model.vo.CategoryVO;
import com.imooc.mall.service.CategoryService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 描述:  目录分类service接口实现类
 */

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    CategoryMapper categoryMapper;
    @Override
    public void add(AddCategoryReq addCategoryReq){
        Category category = new Category();
        BeanUtils.copyProperties(addCategoryReq,category);
        Category categoryOld = categoryMapper.selectByName(addCategoryReq.getName());
        if (categoryOld != null){
            throw new ImoocMallException(ImoocMallExceptionEnum.NAME_EXISTED);
        }
        int count = categoryMapper.insertSelective(category);
        if (count == 0){
            throw new ImoocMallException(ImoocMallExceptionEnum.CREATE_FAILED);
        }
    }


    @Override
    public void update(Category updateCategory){
        if (updateCategory.getName() != null){//如果传递来的参数包含name，需要进行校验
            Category categoryOld = categoryMapper.selectByName(updateCategory.getName());
            if (categoryOld != null && !categoryOld.getId().equals(updateCategory.getId())){//不仅需要校验是否查询到，还需要校验查询到的数据库中对象ID跟传入的要更新的ID是否一样；
                //因为有可能是更新目录所属层数
                throw new ImoocMallException(ImoocMallExceptionEnum.NAME_EXISTED);
            }
        }
        int count = categoryMapper.updateByPrimaryKeySelective(updateCategory);
        if (count == 0){
            throw new ImoocMallException(ImoocMallExceptionEnum.UPDATE_FAILED);
        }
    }

    @Override
    public void delete(Integer id){
        Category categoryOld = categoryMapper.selectByPrimaryKey(id);
        //查不到记录，无法删除，删除失败
        if (categoryOld == null){
            throw new ImoocMallException(ImoocMallExceptionEnum.DELETE_FAILED);
        }
        int count = categoryMapper.deleteByPrimaryKey(id);
        if (count == 0){
            throw new ImoocMallException(ImoocMallExceptionEnum.DELETE_FAILED);
        }
    }

    @Override
    public PageInfo listForAdmin(Integer pageNum, Integer pageSize){
        PageHelper.startPage(pageNum,pageSize,"type,order_num");//第一优先级按照type排序，第二优先级按照order_num
        List<Category> categoryList = categoryMapper.selectList();
        PageInfo pageInfo = new PageInfo(categoryList);//list包含在pageinfo当中
        return pageInfo;//不可以直接返回list。listForAdmin()方法中分页查询了数据。list中没有当前页，每页中多少条数据，总页数等信息，所以需要返回pageinfo。
        //pageInfo中包含了总共有多少条数据，当前是否是最后一页等多条在与前端交互时十分有用的数据

    }

    @Override
    @Cacheable(value = "listCategoryForCustomer")//注意该注解是springframework提供的注解
    public List<CategoryVO> listCategoryForCustomer(Integer parentId){
        ArrayList<CategoryVO> categoryVOList = new ArrayList<>();
        recursiveFindCategories(categoryVOList,parentId);//从0——一级目录开始检索,递归得到所有目录分级,添加一个参数，提高灵活性
        return categoryVOList;
    }

    private void recursiveFindCategories(List<CategoryVO> categoryVOList,Integer parentId){//单独新增一个方法，实现职责的分离
        //parentId意义：获取以……为父目录的目录
        //递归获取所有子类别，并组合成一个目录树
        List<Category> categoryList = categoryMapper.selectCategoriesByParentId(parentId);
        if (!CollectionUtils.isEmpty(categoryList)){
            for (int i = 0;i < categoryList.size();i++){
                Category category = categoryList.get(i);
                CategoryVO categoryVO = new CategoryVO();
                BeanUtils.copyProperties(category,categoryVO);//将获取到的类放入新的类当中
                categoryVOList.add(categoryVO);//此时还有一个字段未填充
                recursiveFindCategories(categoryVO.getChildCategory(),categoryVO.getId());//递归调用，获取子类别

            }
        }

    }


}
