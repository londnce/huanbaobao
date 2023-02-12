package com.wsf.huanbaobao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 管理员实体类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("t_admin")
public class Admin {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String adminName;
    private String adminPw;
    private String email;
    private String role;
}
