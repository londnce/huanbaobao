package com.wsf.huanbaobao.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wsf.huanbaobao.entity.Address;

import java.util.Date;
import java.util.List;

/**
 * 地址的业务层接口
 */
public interface AddressService extends IService<Address> {

    /**
     * 添加新地址
     * @param uid 用户的id
     * @param username  用户的名称
     * @param address   用户的收货地址
     */
    void addNewAddress(Integer uid, String username, Address address);

    /**
     * 通过uid获取收货地址
     * @param uid   用户的id
     * @return  用户的地址
     */
    List<Address> getByUid(Integer uid);

    /**
     *根据aid查询地址
     * @param aid 地址aid
     * @return 用户收货地址
     */
    Address findAddressByAid(Integer aid);

    /**
     * 设置用户所有地址为非默认
     * @param uid 用户uid
     **/
    void setNotDefaultAddress(Integer uid);

    /**
     * 设置某个地址为默认地址
     * @param aid   地址aid
     * @param modifiedUser  修改人
     * @param modifiedTime  修改时间
     * @return  受影响的行数
     */
    Integer setOneAddressDefault(Integer aid,String modifiedUser, Date modifiedTime);

    /**
     *  删除某个指定的地址
     * @param aid   地址aid
     */
    void deleteAddressByAid(Integer aid);

    /**
     * 修改地址
     * @param address   地址
     * @param modifiedUser  修改人
     * @return  受影响的行数
     */
    Integer updateAddress(Address address,String modifiedUser);
}
