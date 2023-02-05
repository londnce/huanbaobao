package com.wsf.huanbaobao.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wsf.huanbaobao.entity.ProductCategory;
import com.wsf.huanbaobao.mapper.ProductCategoryMapper;
import com.wsf.huanbaobao.service.ProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductCategoryServiceImpl extends ServiceImpl<ProductCategoryMapper, ProductCategory> implements ProductCategoryService {
    @Autowired
    private ProductCategoryMapper categoryMapper;

    //@Override
    ///**
    // * java8新特性
    // * sorted 方法用于对流进行排序。以下代码片段使用 sorted 方法对输出的 10 个随机数进行排序：
    // * Collectors
    // Collectors 类实现了很多归约操作，例如将流转换成集合和聚合元素。Collectors 可用于返回列表或字符串：
    // filter
    // filter 方法用于通过设置的条件过滤出元素。以下代码片段使用 filter 方法过滤出空字符串：
    // */
    //public List<ProductCategory> listWithTree() {
    //    //1：查出所有分类
    //    List<ProductCategory> lists = categoryMapper.selectList(null);
    //
    //    //2：组装成父子的树形结构
    //    List<ProductCategory> level1Menu = lists.stream().filter( productCategory ->
    //            productCategory.getParentId() == 0
    //    ).map( (menu) -> {
    //        menu.setChildren(getChildren(menu,lists));
    //        return menu;
    //    }).sorted(Comparator.comparingInt(menu -> (menu.getSortOrder() == null ? 0 : menu.getSortOrder()))).collect(Collectors.toList());
    //    return level1Menu;
    //}
    //
    ///**
    // * 所有的子菜单在哪里，我们可以写一个递归的方法，来找到每一个菜单的子菜单
    // * 我们写一个方法：获取某一个菜单的子菜单，（递归查找所有一级菜单的子菜单）
    // */
    //private List<ProductCategory> getChildren(ProductCategory root,List<ProductCategory> all){
    //    /*
    //     * collect整个菜单就是我们要用的子菜单，但是每个子菜单还是会有可有子菜单
    //     */
    //    List<ProductCategory> children = all.stream().filter(productCategory -> {
    //        //让当前菜单（ProductCategory）的parentid等于我们指定菜单（root）的id,说明当前菜单就是它的子菜单
    //        return productCategory.getParentId() == root.getId();
    //    }).map(productCategory->{
    //        //1：继续找到子菜单
    //        productCategory.setChildren(getChildren(productCategory,all));
    //        return productCategory;
    //    }).sorted(Comparator.comparingInt(menu -> (menu.getSortOrder() == null ? 0 : menu.getSortOrder()))).collect(Collectors.toList());
    //    return children;
    //}

}
