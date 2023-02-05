package com.wsf.huanbaobao.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wsf.huanbaobao.entity.District;
import com.wsf.huanbaobao.mapper.DistrictMapper;
import com.wsf.huanbaobao.service.DistrictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 *
 * 省市区业务层接口的实现类
 */
@Service
public class DistrictServiceImpl extends ServiceImpl<DistrictMapper, District> implements DistrictService {
    @Autowired
    private DistrictMapper districtMapper;

    /**
     * 根据父代号查询省市区信息
     * @param parent 父代号
     * @return java.util.List<com.wsf.huanbaobao.entity.District>
     **/
    @Override
    public List<District> getSpecifyDistrictByParent(String parent) {
        List<District> districts = districtMapper.selectDistrictByParent(parent);

        //过滤无效字段数据，提高传输效率
        for (District ad: districts) {
            ad.setId(null);
            ad.setParent(null);
        }

        //返回数据
        return districts;
    }

    /**
     * 根据code查询省市区名字
     * @param code 省市区的code
     * @return java.lang.String
     **/
    @Override
    public String getNameByCode(String code) {
        return districtMapper.selectDistrictByCode(code);
    }
}
