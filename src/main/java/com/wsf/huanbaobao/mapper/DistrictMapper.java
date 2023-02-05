package com.wsf.huanbaobao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wsf.huanbaobao.entity.District;
import java.util.List;

/**
 * 省市区mapper接口
 */
public interface DistrictMapper extends BaseMapper<District> {

    //根据父代号查询区域信息
    List<District> selectDistrictByParent(String parent);

    //根据code查询当前省市区的名称
    String selectDistrictByCode(String code);
}
