package com.wsf.huanbaobao.controller.admin;

import com.github.pagehelper.PageInfo;
import com.wsf.huanbaobao.controller.BaseController;
import com.wsf.huanbaobao.entity.Product;
import com.wsf.huanbaobao.mapper.ProductMapper;
import com.wsf.huanbaobao.service.ProductService;
import com.wsf.huanbaobao.utils.JsonResult;
import com.wsf.huanbaobao.vo.ProductVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin/product")
public class ProductManageController extends BaseController {
    @Autowired
    private ProductService productService;
    @Autowired
    private ProductMapper productMapper;

    //查询所有商品
    @GetMapping("/list")
    public JsonResult<List<ProductVo>> queryAll(){
        List<ProductVo> products = productService.findAll();
        return new JsonResult<>(OK,products);
    }

    //新增商品
    @PostMapping("/save")
    public JsonResult<Void> addPro(@RequestBody Product product){
        productService.save(product);
        return new JsonResult<>(OK);
    }

    //编辑商品
    @PutMapping("/edit")
    public JsonResult<Void> editPro(@RequestBody Product product){
        productService.editPro(product);
        return new JsonResult<>(OK);
    }

    //删除商品
    @DeleteMapping("/delete/{id}")
    public JsonResult<Void> deleteUser(@PathVariable("id") Integer id){
        productService.removeById(id);
        return new JsonResult<>(OK);
    }

    //分页查询
    @GetMapping("/page")
    public JsonResult<Map<String , Object>> page(@RequestParam Integer pageNum,
                                                 @RequestParam Integer pageSize,
                                                 @RequestParam(defaultValue = "") String title){
        List<ProductVo> data = productService.queryPage(pageNum, pageSize,title);
        Integer total = productMapper.count(title);
        Map<String , Object> res = new HashMap<>();
        res.put("data", data);
        res.put("total",total);
        return new JsonResult<>(OK,res);
    }
}
