package com.wsf.huanbaobao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wsf.huanbaobao.entity.Cart;
import com.wsf.huanbaobao.vo.CartVo;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 *  购物车mapper接口
 */
public interface CartMapper extends BaseMapper<Cart> {
    /**
     * 根据uid和pid查询某条购物车数据
     * @param uid   用户id
     * @param pid   商品id
     */
    Cart findCartByUidAndPid(Integer uid, @Param("pid") Integer pid);

    /**
     * 更新购物车数据
     * @param cart
     * @return
     */
    Integer updateCartInfo(Cart cart);

    /**
     * 添加购物车
     * @param cart  购物车详情
     * @return  受影响行数
     */
    Integer addCart(Cart cart);

    /**
     * 更新购物车数据
     * @param cid   购物车数据id
     * @param num   更新的数量
     * @param modifiedUser  执行人
     * @param modifiedTime  执行时间
     * @return  受影响行数
     */
    Integer updateNumByCid(Integer cid, Integer num, String modifiedUser, Date modifiedTime);

    /**
     * 根据uid查询用户购物车信息
     * @param uid   用户id
     * @return
     */
    List<CartVo> selectAllCartsByUid(Integer uid);

    /**
     * 根据cid查询用户cart信息
     * @param cid   购物车id
     * @return
     */
    Cart findCartByCid(Integer cid);

    /**
     * 根据cid查询返回CartVo对象
     * @param cid
     * @return
     */
    CartVo selectCartVoByCid(Integer cid);

    /**
     * 根据cid删除指定商品
     * @param cid
     * @return
     */
    Integer deleteCartByCid(Integer cid);

    /**
     * 根据uid和pid删除对应的t_cart表中的数据
     * @param uid
     * @param pid
     * @return
     */
    Integer deleteCartByUidAndPid(Integer uid,Integer pid);
}
