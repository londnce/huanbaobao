package com.wsf.huanbaobao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Objects;

/**
 * 订单详情实体类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("t_order_item")
@EqualsAndHashCode(callSuper = true)
public class OrderItem extends BaseEntity{
    @TableId(type = IdType.AUTO)
    private Integer id;
    private Integer oid;
    private Integer pid;
    private String title;
    private String image;
    private Long price;
    private Integer num;
}
