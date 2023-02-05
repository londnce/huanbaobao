package com.wsf.huanbaobao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wsf.huanbaobao.entity.Address;

import java.util.Date;
import java.util.List;

/**
 * 收货地址mapper接口
 */
public interface AddressMapper extends BaseMapper<Address> {
    /**
     * 插入用户的收货地址数据
     * @param address   收货地址
     * @return  受影响行数
     */
    int insert(Address address);

    /**
     * 根据uid统计收货地址数量
     * @param uid   用户的id
     * @return  受影响行数
     */
    Integer countByUid(Integer uid);

    /**
     * 根据用户的id查询用户的收货地址数据
     * @param uid   用户的id
     * @return  收货地址数据
     */
    List<Address> findByUid(Integer uid);

    /**
     * 根据地址aid查询某条数据
     * @param aid   地址的id
     * @return  收货地址
     */
    Address findAddressByAid(Integer aid);

    /**
     * 将用户的所有地址的默认值设置为非默认
     * @param uid   用户的id
     * @return  受影响的行数
     */
    Integer setAllAddressNotDefault(Integer uid);

    /**
     * 设置某个地址的默认值为默认
     * @param aid   地址的id
     * @param modifiedUser  修改人
     * @param modifiedTime  修改时间
     * @return  受影响的行数
     */
    Integer setOneAddressDefault(Integer aid,String modifiedUser, Date modifiedTime);

    /**
     * 逻辑删除某个地址
     * @param aid   地址的id
     * @return  受影响的行数
     */
    Integer deleteAddressByAid(Integer aid);

    /**
     * 修改地址
     * @param address   地址参数
     * @return  受影响的行数
     */
    Integer updateAddressByAid(Address address);
}
