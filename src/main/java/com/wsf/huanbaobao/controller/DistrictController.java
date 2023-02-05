package com.wsf.huanbaobao.controller;

import com.wsf.huanbaobao.entity.District;
import com.wsf.huanbaobao.service.DistrictService;
import com.wsf.huanbaobao.utils.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

/**
 * 省市区相关请求的控制层的实现
 */
@RestController
@RequestMapping("/district")
public class DistrictController extends BaseController{
    @Autowired
    private DistrictService districtService;

    /**
     * 父代号查询省市区的请求
     * @param parent 父代号
     * @return com.wsf.huanbaobao.utils.JsonResult<java.util.List<com.wsf.huanbaobao.entity.District>>
     **/
    @GetMapping("/parent")
    public JsonResult<List<District>> getDistrictByParent(String parent){
        //查询数据
        List<District> list = districtService.getSpecifyDistrictByParent(parent);
        //返回数据
        return new JsonResult<>(OK,list);
    }

    /**
     * 查询省市区名称的请求
     * @param code 省市区的code
     * @return com.wsf.huanbaobao.utils.JsonResult<java.lang.String>
     **/
    @GetMapping
    public String getDistrictNameByCode(String code){
        return districtService.getNameByCode(code);
    }
}
