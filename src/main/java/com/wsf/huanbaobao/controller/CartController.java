package com.wsf.huanbaobao.controller;

import com.wsf.huanbaobao.entity.Cart;
import com.wsf.huanbaobao.service.CartService;
import com.wsf.huanbaobao.service.ex.InsertException;
import com.wsf.huanbaobao.utils.JsonResult;
import com.wsf.huanbaobao.vo.CartVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/cart")
public class CartController extends BaseController {
    @Autowired
    private CartService cartService;

    /**
     * 添加购物车
     * @param cart
     * @param session
     * @return
     */
    @PostMapping("/addCart")
    public JsonResult<Void> addCart(Cart cart, HttpSession session){
        //从session中区域uid和用户名
        Integer uid = getUidFromSession(session);
        String username = getUsernameFromSession(session);
        cart.setUid(uid);
        Date date = new Date();
        int result = cartService.addCart(cart, username, date, username, date);

        if (result == 0){
            throw  new InsertException("服务器或数据库异常，加入购物车失败");
        }
        return new JsonResult<>(OK);
    }

    /**
     * 根据获取session中的uid展示购物车信息
     * @param session
     * @return
     */
    @GetMapping("/showCarts")
    public JsonResult<List<CartVo>> findCarts(HttpSession session){
        Integer uid = getUidFromSession(session);
        List<CartVo> carts = cartService.findCartByUid(uid);
        return new JsonResult<>(OK,carts);
    }

    /**
     * 更新购物车数量信息
     * @param num
     * @param cid
     * @param session
     * @return
     */
    @PostMapping("/updateCart")
    public JsonResult<Void> updateCateByCid(Integer num,Integer cid,HttpSession session){
        String modifiedUser = getUsernameFromSession(session);
        Date modifiedTime = new Date();
        cartService.updateCartNumByCid(num,modifiedUser,modifiedTime,cid);
        return new JsonResult<>(OK);
    }

    /**
     * 处理cids数组的内容查询购物车信息
     * @param cids
     * @return
     */
    @GetMapping("/queryCids")
    public JsonResult<List<CartVo>> findCids(Integer[] cids){
        List<CartVo> list = cartService.findCartByCids(cids);
        System.out.println(list.toString());
        if (list.size() == 0){
            return new JsonResult<>(4007);
        }
        return new JsonResult<>(OK,list);
    }

    /**
     * 根据cids内的指定cid删除购物车
     * @param cids
     * @return
     */
    @PostMapping("/deleteCart")
    public JsonResult<Void> deleteCartByCid(Integer[] cids){
        //遍历执行删除操作
        for (Integer cid: cids) {
            cartService.deleteCartByCid(cid);
        }

        return new JsonResult<>(OK);
    }
}
