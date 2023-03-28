package com.wsf.huanbaobao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wsf.huanbaobao.entity.ProductCategory;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface ProductCategoryMapper extends BaseMapper<ProductCategory> {
    /**
     * 查询所有分类
     * @return 分类列表
     */
    List<ProductCategory> findAll();

    /**
     * 分页查询分类
     * @param pageNum 偏移量
     * @param pageSize  分页大小
     * @return 分类列表
     */
    List<ProductCategory> findByPage(@Param("pageNum") int pageNum, @Param("pageSize") int pageSize);

    /**
     * 查询分类数量
     * @return 分类数量
     */
    @Select("select count(*) from t_product_category ")
    int count();

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
     * 删除分类
     * @param id 分类ID
     * @return 删除成功数量
     */
    int delete(int id);

    /**
     * 根据父id查询分类
     * @param parentId 父分类id
     * @return 分类列表
     */
    List<ProductCategory> listByParentId(int parentId);

    /**
     * 根据分类等级查询分类
     * @param level 分类等级
     * @return 分类列表
     */
    @Select("select * from t_product_category where level=#{level}")
    List<ProductCategory> listByLevel(int level);

    /**
     * 查询一级分类
     * @return 一级分类列表
     */
    @Select("select * from t_product_category where level =#{level}")
    List<ProductCategory> selectLevel();

    /**
     * 根据分类等级和父分类ID查询相应的子分类
     * @param level 分类等级
     * @param parentId 父id
     * @return 分类列表
     */
    List<ProductCategory> selectByLevelAndParentId(@Param("level") Integer level, @Param("parentId") Integer parentId);
}
