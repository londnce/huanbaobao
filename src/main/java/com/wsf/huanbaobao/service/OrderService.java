package com.wsf.huanbaobao.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.wsf.huanbaobao.entity.Order;
import com.wsf.huanbaobao.entity.OrderItem;
import com.wsf.huanbaobao.vo.OrderVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 订单相关业务层接口
 */
public interface OrderService extends IService<Order> {

    //添加order订单数据
    Order insertOrder(Integer aid, Long totalPrice, Integer uid, String username);

    //添加orderItem数据
    int insertOrderItem(Integer oid, Integer cid, Integer num, String username);

    //从product页面直接购买添加orderItem数据
    int insertOrderItemFromProductHtml(Integer oid, Integer pid, Integer num, String username);

    //根据oid查询order信息
    Order queryOrderByOid(Integer oid);

    //根据oid修改oid的订单状态
    int updateOrderStatusByOid(Integer oid, Integer uid, Integer status);

    //根据oid能从order_item表中找到对应的OrderItem信息
    List<OrderItem> queryOrderItemByOid(Integer oid);

    //根据订单oid查询订单
    List<OrderVo> queryOrderVoByOid(Integer oid);

    //根据用户uid查询订单
    List<OrderVo> queryOrderVoByUid(Integer uid, Integer status);

    //查询所有订单信息
    PageInfo<OrderVo> findAll(Integer pageNum,Integer pageSize,String recvName);
}
