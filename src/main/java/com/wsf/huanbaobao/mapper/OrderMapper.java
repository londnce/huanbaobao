package com.wsf.huanbaobao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wsf.huanbaobao.entity.Order;
import com.wsf.huanbaobao.entity.OrderItem;
import com.wsf.huanbaobao.vo.OrderVo;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * Order对应的mapper接口
 */
public interface OrderMapper extends BaseMapper<Order> {
    /**
     * 插入一条order订单数据
     * @param order 订单数据
     * @return
     */
    int insertOneOrder(Order order);

    /**
     * 向order_item表中插入一条orderItem数据
     * @param orderItem 订单详情
     * @return
     */
    int insertOneOrderItem(OrderItem orderItem);

    /**
     * 根据订单id查询订单详情
     * @param oid   订单id
     * @return
     */
    Order queryOrderByOid(Integer oid);

    /**
     * 根据订单号修改支付状态和支付时间
     * @param oid   订单id
     * @param status    订单状态
     * @param payTime   支付时间
     * @return  受影响行数
     */
    int updateStatusByOidInt(Integer oid, Integer status, Date payTime);

    /**
     * 根据oid能从order_item表中找到对应的pid信息
     * @param oid   订单id
     * @return  订单列表
     */
    List<OrderItem> queryOrderItemByOid(Integer oid);

    /**
     * 根据oid查询值对象
     * @param oid   订单id
     * @return
     */
    List<OrderVo> queryOrderVoByOid(Integer oid);

    /**
     * 根据uid查询值对象
     * @param uid   用户id
     * @param status    订单状态
     * @return
     */
    List<OrderVo> queryOrderVoByUid(Integer uid, Integer status);

    /**
     * 查询所有订单信息
     */
    List<OrderVo> selectAll(String recvName);
}
