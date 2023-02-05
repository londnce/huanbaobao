package com.wsf.huanbaobao.controller;

import com.wsf.huanbaobao.entity.Address;
import com.wsf.huanbaobao.service.AddressService;
import com.wsf.huanbaobao.service.ex.AddressNotFoundException;
import com.wsf.huanbaobao.service.ex.InsertException;
import com.wsf.huanbaobao.utils.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/address")
public class AddressController extends BaseController {
    @Autowired
    private AddressService addressService;

    /**
     * 添加用户新的收货地址
     * @param address
     * @param session
     * @return
     */
    @PostMapping
    public JsonResult<Void> addNewAddress(Address address, HttpSession session){
        //从session中取出uid和用户名
        Integer uid = getUidFromSession(session);
        String username = getUsernameFromSession(session);

        //调用业务层方法，新增地址
        addressService.addNewAddress(uid,username,address);
        return new JsonResult<>(OK);
    }

    /**
     * 根据用户id获取收货地址的列表
     * @param session
     * @return
     */
    @GetMapping
    public JsonResult<List<Address>> getByUid(HttpSession session){
        //获取uid
        Integer uid = getUidFromSession(session);
        //查询数据
        List<Address> list = addressService.getByUid(uid);

        for (Address address: list) {
            address.setUid(null);
            address.setProvinceCode(null);
            address.setCityCode(null);
            address.setAreaCode(null);
            address.setZip(null);
            address.setTel(null);
            address.setModifiedTime(null);
            address.setModifiedUser(null);
            address.setCreatedTime(null);
            address.setCreatedUser(null);
        }
        //返回数据
        return new JsonResult<>(OK,list);
    }

    /**
     * 处理设置默认地址的请求
     * @param aid
     * @param session
     * @return
     */
    @PostMapping("/setAddress")
    public JsonResult<Void> setUserDefaultAddress(Integer aid,HttpSession session){
        //查询要修改的地址是否存在
        Address address = addressService.findAddressByAid(aid);

        if (address == null){
            throw new AddressNotFoundException("该地址不存在，设置失败");
        }
        //从session中取出用户的uid和名字
        Integer uid = getUidFromSession(session);
        String modifiedUser = getUsernameFromSession(session);

        Date date = new Date();

        //将该用户的所有地址设为非默认值
        addressService.setNotDefaultAddress(uid);

        //将需要修改的地址设为默认值
        int result = addressService.setOneAddressDefault(aid, modifiedUser, date);

        if (result == 0){
            throw new InsertException("设置地址时服务器或数据库产生未知异常");
        }

        return new JsonResult<>(OK);
    }

    /**
     * 根据aid删除指定地址
     * @param aid
     * @return
     */
    @PostMapping("/deleteAddress")
    public JsonResult<Void> deleteAddress(Integer aid){
        //将指定地址删除
        addressService.deleteAddressByAid(aid);
        return new JsonResult<>(OK);
    }

    /**
     * 查询某个指定aid地址
     * @param aid
     * @return
     */
    @GetMapping("/getAddress")
    public JsonResult<Address> getAddress(Integer aid){
        Address address = addressService.findAddressByAid(aid);
        //过滤部分不需要的字段
        address.setModifiedTime(null);
        address.setModifiedUser(null);
        address.setCreatedTime(null);
        address.setCreatedUser(null);
        address.setIsDelete(0);

        return new JsonResult<>(OK,address);
    }

    /**
     * 修改地址
     * @param address
     * @param session
     * @return
     */
    @PostMapping("/updateAddress")
    public JsonResult<Void> updateOneAddress(Address address,HttpSession session){
        //取出session中用户名
        String modifiedUser = getUsernameFromSession(session);

        int result = addressService.updateAddress(address, modifiedUser);

        if (result == 0){
            throw new InsertException("修改地址时，服务器或数据库异常");
        }
        return new JsonResult<>(OK);
    }
}
