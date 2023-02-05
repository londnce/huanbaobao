package com.wsf.huanbaobao.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wsf.huanbaobao.entity.Cart;
import com.wsf.huanbaobao.mapper.CartMapper;
import com.wsf.huanbaobao.service.CartService;
import com.wsf.huanbaobao.service.ex.CartNotFoundException;
import com.wsf.huanbaobao.service.ex.DeleteException;
import com.wsf.huanbaobao.vo.CartVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 购物车模块业务层接口实现类
 */
@Service
public class CartServiceImpl extends ServiceImpl<CartMapper, Cart> implements CartService {
    @Autowired
    private CartMapper cartMapper;

    /**
     * 根据uid和pid查询某条购物车数据
     * @param uid   用户id
     * @param pid   商品id
     * @return
     */
    @Override
    public Cart findCartByUidAndPid(Integer uid, Integer pid) {
        return cartMapper.findCartByUidAndPid(uid,pid);
    }

    /**
     * 将商品添加到购物车中
     * @param cart  购物车数据
     * @param createdUser   创建人
     * @param createdTime   创建时间
     * @param modifiedUser  修改人
     * @param modifiedTime  修改时间
     * @return
     */
    @Override
    public Integer addCart(Cart cart,String createdUser,Date createdTime,String modifiedUser, Date modifiedTime) {
        //取得当前商品的pid和用户uid
        Integer pid = cart.getPid();
        Integer uid = cart.getUid();
        //查询当前pid在当前用户的购物车中是否存在
        Cart destCart = cartMapper.findCartByUidAndPid(uid, pid);

        //根据查询的结果做出不同动作
        if (destCart == null){ //表示不存在，则直接添加
            //补全其他四个字段
            cart.setCreatedUser(createdUser);
            cart.setCreatedTime(createdTime);
            cart.setModifiedUser(modifiedUser);
            cart.setModifiedTime(modifiedTime);

            //执行插入操作
            return cartMapper.addCart(cart);
        }else { //表示该用户已存在该产品的数据
            //取出查询的数据数量
            Integer destNum = destCart.getNum();
            //取出新添加产品的数量
            Integer cartNum = cart.getNum();

            //计算最终的数量
            Integer endNum = destNum + cartNum;
            //并更新需要更新的字段
            destCart.setNum(endNum);
            destCart.setModifiedUser(modifiedUser);
            destCart.setModifiedTime(modifiedTime);

            //执行更新操作
            return cartMapper.updateCartInfo(destCart);
        }
    }

    /**
     * 根据uid查询用户CartVo信息
     * @param uid
     * @return
     */
    @Override
    public List<CartVo> findCartByUid(Integer uid) {
        return cartMapper.selectAllCartsByUid(uid);
    }

    /**
     * 根据cid查询用户cart信息
     * @param cid
     * @return
     */
    @Override
    public Cart selectCartByCid(Integer cid) {
        return cartMapper.findCartByCid(cid);
    }

    /**
     * 根据cid返回cartVo对象
     * @param cid
     * @return
     */
    @Override
    public CartVo findCartVoByCid(Integer cid) {
        return cartMapper.selectCartVoByCid(cid);
    }

    /**
     * 根据cids数组批量查询对应订单信息
     * @param cids
     * @return
     */
    @Override
    public List<CartVo> findCartByCids(Integer[] cids) {
        List<CartVo> list = new ArrayList<>();
        for (Integer cid : cids) {
            CartVo destCartVo = cartMapper.selectCartVoByCid(cid);
            if (destCartVo != null){
                list.add(destCartVo);
            }
        }
        return list;
    }

    /**
     * 根据cid删除指定的购物车商品
     * @param cid
     */
    @Override
    public void deleteCartByCid(Integer cid) {
        Integer result = cartMapper.deleteCartByCid(cid);

        if (result == 0){
            throw new DeleteException("服务器异常，删除失败");
        }
    }

    /**
     * 根据uid和pid删除对应的t_cart表中的数据
     * @param uid
     * @param pid
     * @return
     */
    @Override
    public Integer deleteCartByUidAndPid(Integer uid, Integer pid) {
        Integer result = cartMapper.deleteCartByUidAndPid(uid, pid);
        if (result == 0){
            throw new DeleteException("服务器异常，删除购物车商品失败!!");
        }
        return result;
    }

    /**
     * 根据cid更新用户cart数量
     * @param num
     * @param modifiedUser
     * @param modifiedTime
     * @param cid
     */
    @Override
    public void updateCartNumByCid(Integer num, String modifiedUser, Date modifiedTime, Integer cid) {
        //现根据cid查询此数据是否存在
        Cart cart = cartMapper.findCartByCid(cid);

        if (cart == null){
            throw new CartNotFoundException("购物车内无这条数据，增加失败");
        }

        cartMapper.updateNumByCid(cid, num, modifiedUser, modifiedTime);
    }


}
