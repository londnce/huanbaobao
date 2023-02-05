package com.wsf.huanbaobao.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wsf.huanbaobao.entity.Address;
import com.wsf.huanbaobao.mapper.AddressMapper;
import com.wsf.huanbaobao.service.AddressService;
import com.wsf.huanbaobao.service.DistrictService;
import com.wsf.huanbaobao.service.ex.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 地址模块业务层接口实现类
 */
@Service
public class AddressServiceImpl extends ServiceImpl<AddressMapper, Address> implements AddressService {
    @Autowired
    private AddressMapper addressMapper;
    @Autowired
    private DistrictService districtService;

    @Value("${user.address.max-count}")
    private Integer maxCount;

    /**
     * 添加新地址
     * @param uid
     * @param username
     * @param address
     */
    @Override
    public void addNewAddress(Integer uid, String username, Address address) {
        //调用收货地址统计方法
        Integer count = addressMapper.countByUid(uid);
        if (count >= maxCount){
            throw new AddressCountLimitException("用户收货地址超出上限，请先删除一些地址");
        }

        //uid、isDelete、isDefault
        address.setUid(uid);
        Integer isDefault = count == 0 ? 1 : 0;
        address.setIsDefault(isDefault);

        //获取并设置其他三个地址为null的字段
        String provinceName = districtService.getNameByCode(address.getProvinceCode());
        String cityName = districtService.getNameByCode(address.getCityCode());
        String areaName = districtService.getNameByCode(address.getAreaCode());

        address.setProvinceName(provinceName);
        address.setCityName(cityName);
        address.setAreaName(areaName);

        //补全4项日志
        address.setCreatedUser(username);
        address.setCreatedTime(new Date());
        address.setModifiedUser(username);
        address.setModifiedTime(new Date());

        //插入收货地址的方法
        int rows = addressMapper.insert(address);
        if (rows != 1){
            throw new InsertException("插入用户收货地址时产生未知的异常");
        }
    }

    /**
     * 根据用户的id查询收货地址
     * @param uid 用户id
     * @return 用户的收货地址
     **/
    @Override
    public List<Address> getByUid(Integer uid) {
        //返回查询的用户地址信息
        return addressMapper.findByUid(uid);
    }

    /**
     * 根据aid查询地址
     * @param aid 地址aid
     * @return 用户收货地址
     **/
    @Override
    public Address findAddressByAid(Integer aid) {
        return addressMapper.findAddressByAid(aid);
    }

    /**
     * 设置用户所有地址为非默认
     * @param uid 用户uid
     **/
    @Override
    public void setNotDefaultAddress(Integer uid) {
        addressMapper.setAllAddressNotDefault(uid);
    }

    /**
     * 设置某个地址为默认地址
     * @param aid 地址aid
     * @param modifiedTime 修改时间
     * @param modifiedUser 修改者
     * @return int
     **/
    @Override
    public Integer setOneAddressDefault(Integer aid,String modifiedUser, Date modifiedTime) {
        return addressMapper.setOneAddressDefault(aid,modifiedUser,modifiedTime);
    }

    /**
     * 根据aid删除地址
     * @param aid 地址aid
     **/
    @Override
    public void deleteAddressByAid(Integer aid) {
        Address address = addressMapper.findAddressByAid(aid);
        if (address == null){
            throw new AddressNotFoundException("地址不存在，删除失败");
        }
        int result = addressMapper.deleteAddressByAid(aid);

        if (result == 0){
            throw new DeleteException("服务器或数据出现异常，删除失败");
        }
    }

    /**
     * 修改指定地址
     * @param address 实体类对象
     * @return 受影响的行数
     **/
    @Override
    public Integer updateAddress(Address address,String modifiedUser) {

        //获取并设置其他三个地址为null的字段
        String provinceName = districtService.getNameByCode(address.getProvinceCode());
        String cityName = districtService.getNameByCode(address.getCityCode());
        String areaName = districtService.getNameByCode(address.getAreaCode());

        address.setProvinceName(provinceName);
        address.setCityName(cityName);
        address.setAreaName(areaName);

        //补全表单中没有的其他字段
        address.setModifiedUser(modifiedUser);
        address.setModifiedTime(new Date());
        int result = addressMapper.updateAddressByAid(address);

        return result;
    }


}
