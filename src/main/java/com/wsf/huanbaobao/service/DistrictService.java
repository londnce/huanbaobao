package com.wsf.huanbaobao.service;

import com.wsf.huanbaobao.entity.District;
import java.util.List;

/**
 * 省市区业务层接口
 */
public interface DistrictService {

    //查询某个特定省份的信息
    List<District> getSpecifyDistrictByParent(String parent);

    //根据code查询当前省市区的名称
    String getNameByCode(String code);

}
