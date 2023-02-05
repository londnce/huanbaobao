package com.wsf.huanbaobao.controller.admin;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wsf.huanbaobao.controller.BaseController;
import com.wsf.huanbaobao.entity.ProductCategory;
import com.wsf.huanbaobao.service.ProductCategoryService;
import com.wsf.huanbaobao.utils.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/admin/category")
public class ProductCategoryController extends BaseController {
    @Autowired
    private ProductCategoryService categoryService;

    @GetMapping("/init")
    public JsonResult<List<ProductCategory>> init(){
        QueryWrapper<ProductCategory> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("level",0);
        List<ProductCategory> list = categoryService.list(queryWrapper);
        return new JsonResult<>(OK,list);
    }

    @GetMapping("/children/{level}/{parentId}")
    public JsonResult<List<ProductCategory>> children(
            @PathVariable("level") Integer level,
            @PathVariable("parentId") Integer parentId
    ){
        QueryWrapper<ProductCategory> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("level",level);
        queryWrapper.eq("parent_id", parentId);
        List<ProductCategory> list = categoryService.list(queryWrapper);
        return new JsonResult<>(OK,list);
    }

}
