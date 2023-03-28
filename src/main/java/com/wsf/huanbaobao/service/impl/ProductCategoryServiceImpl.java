package com.wsf.huanbaobao.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wsf.huanbaobao.entity.ProductCategory;
import com.wsf.huanbaobao.mapper.ProductCategoryMapper;
import com.wsf.huanbaobao.service.ProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ProductCategoryServiceImpl extends ServiceImpl<ProductCategoryMapper, ProductCategory> implements ProductCategoryService {
    @Autowired
    private ProductCategoryMapper categoryMapper;

    /**
     * 查询所有分类
     * @return 分类列表
     */
    @Override
    public List<ProductCategory> findAll() {
        List<ProductCategory> categories = categoryMapper.listByLevel(1);
        for (ProductCategory category : categories) {
            List<ProductCategory> list = categoryMapper.listByParentId(category.getId());  // 获取当前一级分类的所有子分类
            if (list != null && !list.isEmpty()) {
                category.setChildren(list); // 将子分类放入当前一级分类的children中
                for (ProductCategory childCategory : list) {
                    List<ProductCategory> grandChildCategoryList = categoryMapper.listByParentId(childCategory.getId()); // 获取当前二级分类的所有子分类
                    if (grandChildCategoryList != null && !grandChildCategoryList.isEmpty()) {
                        childCategory.setChildren(grandChildCategoryList); // 将子分类放入当前二级分类的children中
                    }
                }
            }
        }
        return categories;
    }

    /**
     * 根据ID查询分类
     * @param id 分类ID
     * @return 分类实体
     */
    @Override
    public ProductCategory findById(int id) {
        return categoryMapper.findById(id);
    }

    /**
     * 添加分类
     * @param category 分类实体
     * @return 添加成功数量
     */
    @Override
    public int add(ProductCategory category) {
        return categoryMapper.add(category);
    }

    /**
     * 更新分类
     * @param category 分类实体
     * @return 更新成功数量
     */
    @Override
    public int update(ProductCategory category) {
        category.setModifiedTime(new Date());
        return categoryMapper.updateById(category);
    }

    /**
     * 根据父id查询分类
     * @param parentId 父分类id
     * @return 分类列表
     */
    @Override
    public List<ProductCategory> listByParentId(Integer parentId) {
        return categoryMapper.listByParentId(parentId);
    }

    /**
     * 查询分类等级
     * @return  分类等级列表
     */
    @Override
    public List<ProductCategory> getLevelCategories(Integer level) {
        return categoryMapper.listByLevel(level);
    }

    /**
     * 根据分类等级和父分类ID查询相应的子分类
     * @param level 分类等级
     * @param parentId 父id
     * @return 分类列表
     */
    @Override
    public List<ProductCategory> getChildCategories(Integer level, Integer parentId) {
        return categoryMapper.selectByLevelAndParentId(level, parentId);
    }


}
