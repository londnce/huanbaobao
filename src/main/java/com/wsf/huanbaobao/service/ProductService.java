package com.wsf.huanbaobao.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.wsf.huanbaobao.entity.Product;
import com.wsf.huanbaobao.vo.ProductVo;

import java.util.List;

/**
 * 商品业务层接口
 */
public interface ProductService extends IService<Product> {

    /**
     * 查询热销商品的前五项
     * @return
     */
    List<Product> queryPriorityProduct();

    /**
     * 查询最新商品的前五项
     * @return
     */
    List<Product> queryTheNewProduct();

    /**
     * 查询指定id商品
     * @param id
     * @return
     */
    Product queryProductById(Integer id);

    /**
     * 根据名称进行模糊查询
     * @param pageNum
     * @param pageSize
     * @param title
     * @return
     */
    PageInfo<Product> queryProductByTitle(Integer pageNum, Integer pageSize, String title);

    /**
     * 查询所有商品
     * @return
     */
    List<ProductVo> findAll();

    /**
     * 分页查询
     * @param pageNum
     * @param pageSize
     * @param title
     * @return
     */
    List<ProductVo> queryPage(Integer pageNum, Integer pageSize, String title);

    /**
     * 编辑商品
     * @param product
     */
    void editPro(Product product);
}
