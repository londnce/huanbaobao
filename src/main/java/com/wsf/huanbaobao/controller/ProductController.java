package com.wsf.huanbaobao.controller;

import com.github.pagehelper.PageInfo;
import com.wsf.huanbaobao.entity.Product;
import com.wsf.huanbaobao.service.ProductService;
import com.wsf.huanbaobao.utils.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 商品相关请求的控制器
 */
@RestController
@RequestMapping("/product")
public class ProductController extends BaseController{
    @Autowired
    private ProductService productService;

    /**
     * 热销商品
     **/
    @GetMapping("/hotProduct")
    public JsonResult<List<Product>> queryBestProduct(){
        //查询对应商品
        List<Product> products = productService.queryPriorityProduct();
        return new JsonResult<>(OK,products);
    }

    /**
     * 展示最新商品
     * @return
     **/
    @GetMapping("/newProduct")
    public JsonResult<List<Product>> queryNewProduct(){
        //查询对应商品
        List<Product> products = productService.queryTheNewProduct();
        return new JsonResult<>(OK,products);
    }

    /**
     * 根据商品id查询该商品信息
     * @param id 商品id
     * @return
     **/
    @GetMapping("/{id}")
    public JsonResult<Product> queryProductById(@PathVariable(value = "id",required = false) Integer id){
        Product product = productService.queryProductById(id);
        return new JsonResult<>(OK,product);
    }

    /**
     * 根据产品关键字进行模糊查询
     * @param pageNum 当前页
     * @param pageSize 每页显示数
     * @param title 查询的关键字
     * @return
     **/
    @GetMapping("/{pageNum}/{pageSize}/{title}")
    public JsonResult<PageInfo<Product>> queryByTitle(@PathVariable("pageNum") Integer pageNum,
                                                      @PathVariable("pageSize") Integer pageSize,
                                                      @PathVariable("title") String title){
        PageInfo<Product> lists = productService.queryProductByTitle(pageNum, pageSize, title);
        return new JsonResult<>(OK,lists);
    }
}
