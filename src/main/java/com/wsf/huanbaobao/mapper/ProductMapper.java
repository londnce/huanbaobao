package com.wsf.huanbaobao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wsf.huanbaobao.entity.Product;
import com.wsf.huanbaobao.entity.User;
import com.wsf.huanbaobao.vo.ProductVo;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 商品mapper接口
 */
public interface ProductMapper extends BaseMapper<Product> {

    /**
     * 查询优先权前四的商品进行展示
     * @return
     **/
    List<Product> queryPriorityProduct();

    /**
     * 查询最新上架的商品进行展示
     * @return
     **/
    List<Product> queryTheNewProduct();

    /**
     * 根据指定商品id进行商品查询
     * @param id 商品id
     * @return
     **/
    Product queryProductById(Integer id);

    /**
     * 根据指定的名称关键字进行模糊查询
     * @param title 要查询的商品名称关键字
     * @return
     **/
    List<Product> queryProductByTitle(String title);

    /**
     * 多表查询
     * @return
     */
    List<Product> findByTitle(String title);

    /**
     * 分页查询
     * @param pageNum
     * @param pageSize
     * @param title
     * @return
     */
    List<ProductVo> page(Integer pageNum, Integer pageSize, String title);

    /**
     * 统计查询
     * @param title
     * @return
     */
    @Select("select count(*) from t_product where title like concat('%',#{title},'%')")
    Integer count(String title);

    /**
     * 根据id编辑商品
     * @param id
     * @return
     */
    //Product editById(Integer id);
}
