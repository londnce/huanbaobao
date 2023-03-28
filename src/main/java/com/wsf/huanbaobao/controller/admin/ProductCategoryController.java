package com.wsf.huanbaobao.controller.admin;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wsf.huanbaobao.controller.BaseController;
import com.wsf.huanbaobao.entity.ProductCategory;
import com.wsf.huanbaobao.mapper.ProductCategoryMapper;
import com.wsf.huanbaobao.service.ProductCategoryService;
import com.wsf.huanbaobao.utils.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/admin/category")
public class ProductCategoryController extends BaseController {
    @Autowired
    private ProductCategoryService categoryService;

    @Autowired
    private ProductCategoryMapper categoryMapper;

    //初始化
    @GetMapping("/init")
    public JsonResult<List<ProductCategory>> init(){
        QueryWrapper<ProductCategory> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("level",1);
        List<ProductCategory> list = categoryService.list(queryWrapper);
        return new JsonResult<>(OK,list);
    }

    //获取分类子类
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

    //查询所有分类
    @GetMapping("/list")
    public JsonResult<List<ProductCategory>> list() {
        List<ProductCategory> data = categoryService.findAll();
        return new  JsonResult<>(OK,data);
    }

    //根据ID查询分类
    @GetMapping("/{id}")
    public JsonResult<ProductCategory> findById(@PathVariable("id") int id) {
        ProductCategory data = categoryService.findById(id);
        return new JsonResult<>(OK,data);
    }

    //添加分类
    @PostMapping("/addCate")
    public JsonResult<Void> add(@RequestBody ProductCategory category) {
        // 查询所属分类
        ProductCategory parent = categoryMapper.selectById(category.getParentId());
        if (parent == null) {
            return new JsonResult<>(Error,"添加分类出现异常");
        }
        // 将新分类加入所属分类的子分类列表
        if (parent.getChildren() == null) {
            parent.setChildren(new ArrayList<>());
        }
        //判断是否为父分类，如果不是则改为是
        if (parent.getIsParent() == 0 || parent.getIsParent() == null){
            parent.setIsParent(1);
        }
        parent.getChildren().add(category);
        // 插入分类数据
        categoryService.add(category);
        return new JsonResult<>(OK);
    }

    //获取分类等级
    @GetMapping("/level")
    public JsonResult<List<ProductCategory>> getLevelCategories(@RequestParam("level") Integer level) {
        List<ProductCategory> categoryList = categoryService.getLevelCategories(level);
        return new JsonResult<>(OK,categoryList);
    }

    //根据分类等级和父分类id查询相应的子分类。
    @GetMapping("/child")
    public List<ProductCategory> getChildCategories(@RequestParam Integer level, @RequestParam Integer parentId) {
        return categoryService.getChildCategories(level, parentId);
    }

    //编辑分类
    @PutMapping("/edit")
    public JsonResult<ModelAndView> editCategory(@RequestParam("id") Integer id, ModelMap model) {
        ProductCategory category = categoryService.findById(id);
        if (category == null){
            throw new RuntimeException("该分类不存在");
        }
        List<ProductCategory> subCategory = categoryService.listByParentId(category.getId());
        boolean isParent = subCategory != null && !subCategory.isEmpty();
        model.addAttribute("category",category);
        model.addAttribute("subCategory",subCategory);
        model.addAttribute("isParent",isParent);
        return new JsonResult<>(OK,new ModelAndView("edit_category"));
    }

    //更新分类
    @PostMapping("/update")
    public JsonResult<Void> updateCate(@RequestBody ProductCategory category){
        categoryService.update(category);
        return new JsonResult<>(OK);
    }

    //删除分类
    @DeleteMapping("/delete/{id}")
    public JsonResult<Void> delete(@PathVariable("id") Integer id) {
        ProductCategory category = categoryMapper.selectById(id);
        if (category.getIsParent() == 0){
            categoryService.removeById(id);
            return new JsonResult<>(OK);
        }else {
            return new JsonResult<>(Error,"该分类有子分类无法删除。");
        }
    }
}
