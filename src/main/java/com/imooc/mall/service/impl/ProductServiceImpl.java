package com.imooc.mall.service.impl;

import com.imooc.mall.common.Constant;
import com.imooc.mall.exception.ImoocMallException;
import com.imooc.mall.exception.ImoocMallExceptionEnum;
import com.imooc.mall.model.dao.ProductMapper;
import com.imooc.mall.model.pojo.Product;
import com.imooc.mall.model.request.AddProductReq;
import com.imooc.mall.service.ProductService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.UUID;

/**
 * 描述:  商品服务实现类
 */
@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductMapper productMapper;

    @Override
    public void add(AddProductReq addProductReq){
        Product product = new Product();
        BeanUtils.copyProperties(addProductReq,product);
        Product productOld = productMapper.selectByName(addProductReq.getName());
        if (productOld != null){
            throw new ImoocMallException(ImoocMallExceptionEnum.NAME_EXISTED);
        }
        int count = productMapper.insertSelective(product);
        if (count == 0){
            throw new ImoocMallException(ImoocMallExceptionEnum.CREATE_FAILED);
        }
    }

    @Override
    public String upload(HttpServletRequest httpServletRequest, MultipartFile file){
        String fileName = file.getOriginalFilename();//获取文件名称
        String suffixName = fileName.substring(fileName.lastIndexOf("."));//从"."处截取，获取文件后缀名称
        //生成文件名称的UUID
        UUID uuid = UUID.randomUUID();//java提供的工具类
        String newFileName = uuid.toString()+suffixName;//拼接成新的名字
        //创建文件
        File fileDirectory = new File(Constant.FILE_UPLOAD_DIR);
        File destFile = new File(Constant.FILE_UPLOAD_DIR + newFileName);
        if (!fileDirectory.exists()){//如果文件夹不存在
            if (!fileDirectory.mkdir()){//创建文件夹
                throw new ImoocMallException(ImoocMallExceptionEnum.MKDIR_FAILED);
            }
        }
        try {
            file.transferTo(destFile);//将传入的文件内容添加到新建的空文件中
        } catch (IOException e) {
            e.printStackTrace();
        }
        return newFileName;
    }
    @Override
    public URI getHost(URI uri){
        URI effectiveURI;
        try {
            /*
            scheme是协议，例如http，或者https协议。
            userInfo是指用户信息，host是服务器地址，port是端口号，path是路径，query是指查询参数，fragment是指引用的文档。
             */
            effectiveURI = new URI(uri.getScheme(),uri.getUserInfo(),uri.getHost(),uri.getPort(),null,null,null);
        } catch (URISyntaxException e) {
            e.printStackTrace();
            effectiveURI = null;
        }
        return effectiveURI;
    }

    @Override
    public void update(Product updateProduct){//不需要额外进行空校验，是否为null将在同名且不同id时判断，如果为null，将继续运行；
        Product productOld = productMapper.selectByName(updateProduct.getName());
        //同名且不同id，不能继续修改
        if (productOld != null && productOld.getId().equals(updateProduct.getId())){
            throw new ImoocMallException(ImoocMallExceptionEnum.NAME_EXISTED);
        }
        int count = productMapper.updateByPrimaryKeySelective(updateProduct);
        if (count == 0){
            throw new ImoocMallException(ImoocMallExceptionEnum.UPDATE_FAILED);
        }
    }

    @Override
    public void delete(Integer id){
        Product productOld = productMapper.selectByPrimaryKey(id);
        //查找不到，无法删除
        if (productOld == null){
            throw new ImoocMallException(ImoocMallExceptionEnum.DELETE_FAILED);
        }
        int count = productMapper.deleteByPrimaryKey(id);
        if (count == 0){
            throw new ImoocMallException(ImoocMallExceptionEnum.DELETE_FAILED);
        }
    }

    @Override
    public void batchUpdateSellStatus(Integer[] ids,Integer sellStatus){
        productMapper.batchUpdateSellStatus(ids,sellStatus);
    }
}
