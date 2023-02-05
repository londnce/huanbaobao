package com.wsf.huanbaobao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import java.io.Serializable;

/**
 * 购物车实体类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("t_cart")
@EqualsAndHashCode(callSuper = true)
public class Cart extends BaseEntity implements Serializable {
    @TableId(type = IdType.AUTO)
    private Integer cid;
    private Integer uid;
    private Integer pid  ;
    private Long price;
    private Integer num;
}
