package com.wsf.huanbaobao.controller;

import com.wsf.huanbaobao.entity.Order;
import com.wsf.huanbaobao.service.OrderService;
import com.wsf.huanbaobao.utils.JsonResult;
import com.wsf.huanbaobao.vo.OrderVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * 订单相关请求的控制器
 */
@RestController
@RequestMapping("/order")
public class OrderController extends BaseController{
    @Autowired
    private OrderService orderService;

    /**
     * 用户创建order订单
     * @param aid 用户选中的地址aid
     * @param totalPrice 商品的总金额
     * @param session 项目启动自动生成的session对象
     * @return
     **/
    @PostMapping("/createOrder")
    public JsonResult<Order> createOrder(Integer aid, Long totalPrice, HttpSession session){
        //从session中取出用户名和uid
        Integer uid = getUidFromSession(session);
        String username = getUsernameFromSession(session);

        //调用业务层方法执行插入操作
        Order order = orderService.insertOrder(aid, totalPrice, uid, username);

        return new JsonResult<>(OK,order);
    }

    /**
     * 创建具体orderItem订单
     * @param oid 已创建订单的oid
     * @param cid 每个商品的cod
     * @param pid 从商品界面直接购买生成订单的商品pid
     * @param num 商品的总数量
     * @param session 项目启动自动生成的session对象
     * @return
     **/
    @PostMapping("/createOrderItem")
    public JsonResult<Void> createOrderItem(Integer oid, Integer cid, Integer pid, Integer num, HttpSession session){
        //从session中取出用户名
        String username = getUsernameFromSession(session);

        if (pid == null){ //当pid等于null代表是从购物车进入界面发来的请求
            //调用业务层方法执行插入操作
            orderService.insertOrderItem(oid,cid,num,username);
        }else {//当pid不等于null代表是从商品详情进入界面发来的请求
            //调用业务层方法执行插入操作
            orderService.insertOrderItemFromProductHtml(oid,pid,num,username);
        }

        return new JsonResult<>(OK);
    }

    /**
     * 根据订单oid查询order信息
     * @param oid 订单oid
     * @return
     **/
    @GetMapping("/queryOrder")
    public JsonResult<Order> queryOrderByOid(Integer oid){
        Order order = orderService.queryOrderByOid(oid);
        return new JsonResult<>(OK,order);
    }

    /**
     * 根据订单oid修改订单状态
     * @param oid 订单oid
     * @param session 项目启动自动生成的session对象
     * @param status 订单状态
     * @return
     **/
    @PostMapping("/updateStatus")
    public JsonResult<Void> updateStatusByOid(Integer oid, HttpSession session, Integer status){
        //获取uid
        Integer uid = getUidFromSession(session);
        //修改订单状态
        orderService.updateOrderStatusByOid(oid,uid,status);

        return new JsonResult<>(OK);
    }

    /**
     * 根据oid查询订单
     * @param oid 订单oid
     * @return
     **/
    @GetMapping("/queryOrderVo")
    public JsonResult<List<OrderVo>>  queryOrderVo(Integer oid){
        List<OrderVo> orderVos = orderService.queryOrderVoByOid(oid);
        return new JsonResult<>(OK,orderVos);
    }

    /**
     * 根据用户uid查询订单
     * @param session 项目启动自动生成的session对象
     * @param status 要查询的订单状态类型
     * @return
     **/
    @GetMapping("/uidOrderVo")
    public JsonResult<List<OrderVo>>  queryOrderVoByUid(HttpSession session, Integer status){
        Integer uid = getUidFromSession(session);
        List<OrderVo> orderVos = orderService.queryOrderVoByUid(uid,status);
        return new JsonResult<>(OK,orderVos);
    }

}
