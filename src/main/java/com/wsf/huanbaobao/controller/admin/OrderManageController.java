package com.wsf.huanbaobao.controller.admin;

import com.github.pagehelper.PageInfo;
import com.wsf.huanbaobao.controller.BaseController;
import com.wsf.huanbaobao.service.OrderService;
import com.wsf.huanbaobao.utils.JsonResult;
import com.wsf.huanbaobao.vo.OrderVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/order")
public class OrderManageController extends BaseController {
    @Autowired
    private OrderService orderService;

    //查询所有订单
    @GetMapping("/list")
    public JsonResult<PageInfo<OrderVo>> queryAll(@RequestParam Integer pageNum,@RequestParam Integer pageSize,String recvName){
        PageInfo<OrderVo> all = orderService.findAll(pageNum, pageSize,recvName);
        return new JsonResult<>(OK,all);
    }

    //编辑订单
    @PutMapping("/editOrder")
    public JsonResult<Void> updateOrder(){
        return new JsonResult<>(OK);
    }

    //取消订单
    @PostMapping("/cancel")
    public JsonResult<Void> cancelOrder(){
        return new JsonResult<>(OK);
    }
}
