package com.wsf.huanbaobao.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductCategoryVo {
    private Integer id;
    private Integer parentId;
    private String name;
    private Integer status;
    private Integer isParent;
    private Integer level;
    private List<ProductCategoryVo> children;
}
