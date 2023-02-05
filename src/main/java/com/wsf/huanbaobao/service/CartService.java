package com.wsf.huanbaobao.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wsf.huanbaobao.entity.Cart;
import com.wsf.huanbaobao.vo.CartVo;

import java.util.Date;
import java.util.List;

/**
 * 购物车的业务层接口
 */
public interface CartService extends IService<Cart> {
    /**
     * 根据uid和pid查询某条购物车数据
     * @param uid   用户id
     * @param pid   商品id
     * @return
     */
    Cart findCartByUidAndPid(Integer uid,Integer pid);

    /**
     * 将商品添加到购物车中
     * @param cart  购物车数据
     * @param createdUser   创建人
     * @param createdTime   创建时间
     * @param modifiedUser  修改人
     * @param modifiedTime  修改时间
     * @return
     */
    Integer addCart(Cart cart,String createdUser,Date createdTime,String modifiedUser, Date modifiedTime);

    /**
     * 根据uid查询用户CartVo信息
     * @param uid
     * @return
     */
    List<CartVo> findCartByUid(Integer uid);

    /**
     * 根据cid查询用户cart信息
     * @param cid
     * @return
     */
    Cart selectCartByCid(Integer cid);

    /**
     * 根据cid返回cartVo对象
     * @param cid
     * @return
     */
    CartVo findCartVoByCid(Integer cid);

    /**
     * 根据cids数组批量查询对应订单信息
     * @param cids
     * @return
     */
    List<CartVo> findCartByCids(Integer[] cids);

    /**
     * 根据cid删除指定的购物车商品
     * @param cid
     */
    void deleteCartByCid(Integer cid);

    /**
     * c
     * @param uid
     * @param pid
     * @return
     */
    Integer deleteCartByUidAndPid(Integer uid,Integer pid);

    /**
     * 根据cid更新用户cart数量
     * @param num
     * @param modifiedUser
     * @param modifiedTime
     * @param cid
     */
    void updateCartNumByCid(Integer num, String modifiedUser, Date modifiedTime, Integer cid);
}
