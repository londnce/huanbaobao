package com.wsf.huanbaobao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 省市区实体类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("t_dict_district")
public class District {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String parent;
    private String code;
    private String name;


}
