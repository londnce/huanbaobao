package com.wsf.huanbaobao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 收藏实体类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("t_favorites")
public class Favorites {
    @TableId(type = IdType.AUTO)
    private Integer fid;
    private Integer uid;
    private Integer pid;
    private String image;
    private Long price;
    private String title;
    private String sellPoint;
    private Integer status;
}
