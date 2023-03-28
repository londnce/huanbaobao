package com.wsf.huanbaobao.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.wsf.huanbaobao.entity.ProductCategory;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProductCategoryService extends IService<ProductCategory> {
    /**
     * 查询所有分类
     * @return 分类列表
     */
    List<ProductCategory> findAll();

    /**
     * 根据ID查询分类
     * @param id 分类ID
     * @return 分类实体
     */
    ProductCategory findById(int id);

    /**
     * 添加分类
     * @param category 分类实体
     * @return 添加成功数量
     */
    int add(ProductCategory category);

    /**
     * 更新分类
     * @param category 分类实体
     * @return 更新成功数量
     */
    int update(ProductCategory category);

    /**
     * 根据父id查询分类
     * @param parentId 父分类id
     * @return 分类列表
     */
    List<ProductCategory> listByParentId(Integer parentId);

    /**
     * 查询分类等级
     * @return 分类列表
     */
    List<ProductCategory> getLevelCategories(Integer level);

    /**
     * 根据分类等级和父分类ID查询相应的子分类
     * @param level 分类等级
     * @param parentId 父id
     * @return 分类列表
     */
    List<ProductCategory> getChildCategories(Integer level, Integer parentId);
}
