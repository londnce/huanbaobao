package com.wsf.huanbaobao.vo;

import com.wsf.huanbaobao.entity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ProductVo extends BaseEntity{
    private Integer id;
    private String title;
    private Long price;
    private Integer num;
    private String image;
    private Integer status;
    private String categoryOneName;
    private String categoryTwoName;
    private String categoryThreeName;
}
