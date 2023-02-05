package com.wsf.huanbaobao.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wsf.huanbaobao.entity.Product;
import com.wsf.huanbaobao.entity.ProductCategory;
import com.wsf.huanbaobao.mapper.ProductCategoryMapper;
import com.wsf.huanbaobao.mapper.ProductMapper;
import com.wsf.huanbaobao.service.ProductService;
import com.wsf.huanbaobao.service.ex.ProductBadStatusException;
import com.wsf.huanbaobao.service.ex.ProductNotFoundException;
import com.wsf.huanbaobao.service.ex.UpdateException;
import com.wsf.huanbaobao.vo.ProductVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 商品业务层接口的实现类
 */
@Service
public class ProductServiceImpl extends ServiceImpl<ProductMapper,Product> implements ProductService {
    @Autowired(required = false)
    private ProductMapper productMapper;

    @Autowired
    private ProductCategoryMapper categoryMapper;

    /**
     * 查询热销商品
     * @return
     **/
    @Override
    public List<Product> queryPriorityProduct() {
        return productMapper.queryPriorityProduct();
    }

    /**
     * 查询最新商品
     * @return
     **/
    @Override
    public List<Product> queryTheNewProduct() {
        return productMapper.queryTheNewProduct();
    }

    /**
     * 根据商品id查询商品
     * @param id 商品id
     * @return
     **/
    @Override
    public Product queryProductById(Integer id) {
        Product product = productMapper.queryProductById(id);

        if (product == null){
            throw new ProductNotFoundException("无此商品信息，查询失败");
        }

        if (product.getStatus() == 2){
            throw new ProductBadStatusException("商品已下架");
        }

        if (product.getStatus() == 3){
            throw new ProductBadStatusException("商品已删除");
        }
        //无任何异常则返回数据
        return product;
    }

    /**
     * 根据名称进行模糊查询
     * @param title 查询的关键字
     * @return
     **/
    @Override
    public PageInfo<Product> queryProductByTitle(Integer pageNum, Integer pageSize, String title) {
        //开启分页功能
        PageHelper.startPage(pageNum,pageSize);
        //调用持久层方法进行查询
        List<Product> products = productMapper.queryProductByTitle(title);
        //返回分页数据
        PageInfo<Product> pageInfo = new PageInfo<>(products);
        return pageInfo;
    }

    /**
     * 多表联查查询所有
     * @return
     */
    @Override
    public List<ProductVo> findAll() {
        List<Product> products = productMapper.selectList(null);
        ProductVo productVo = null;
        List<ProductVo> result = new ArrayList<>();
        for (Product product: products) {
            productVo = new ProductVo();
            BeanUtil.copyProperties(product,productVo);
            QueryWrapper<ProductCategory> qw = new QueryWrapper<>();
            qw.eq("id",product.getCategoryleveloneId());
            ProductCategory category = categoryMapper.selectOne(qw);
            productVo.setCategoryOneName(category.getName());
            qw = new QueryWrapper<>();
            qw.eq("id",product.getCategoryleveltwoId());
            category = categoryMapper.selectOne(qw);
            productVo.setCategoryTwoName(category.getName());
            qw = new QueryWrapper<>();
            qw.eq("id",product.getCategorylevelthreeId());
            category = categoryMapper.selectOne(qw);
            productVo.setCategoryThreeName(category.getName());
            result.add(productVo);
        }
        return result;
    }

    /**
     * 分页查询
     * @param pageNum
     * @param pageSize
     * @param title
     * @return
     */
    @Override
    public List<ProductVo> queryPage(Integer pageNum, Integer pageSize, String title) {
        pageNum = (pageNum - 1) * pageSize;
        return productMapper.page(pageNum,pageSize,title);
    }

    /**
     * 编辑商品
     * @param product
     */
    @Override
    public void editPro(Product product) {
        int updateById = productMapper.updateById(product);
        if (updateById != 1){
            throw new UpdateException("更新商品信息时发生了未知异常");
        }
    }


}
