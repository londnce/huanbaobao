package com.wsf.huanbaobao.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wsf.huanbaobao.entity.User;

import java.util.List;

/**
 * 用户的业务层接口
 */
public interface UserService extends IService<User> {
    /**
     * 用户注册方法
     * @param user
     */
    void reg(User user);

    /**
     * 用户登录方法
     * @param user
     * @return
     */
    User login(User user);

    /**
     * 用户修改密码方法
     * @param uid
     * @param username
     * @param oldPassword
     * @param newPassword
     */
    void changePassword(Integer uid,String username,String oldPassword,String newPassword);

    /**
     * 根据用户的id查询用户的信息
     * @param uid
     * @return
     */
    User getByUid(Integer uid);

    /**
     * 更新用户的数据操作
     * @param uid   用户的id
     * @param username  用户的名称
     * @param user  用户的对象数据
     */
    void changeInfo(Integer uid, String username, User user);

    /**
     * 修改用户的头像
     * @param uid   用户的id
     * @param avatar    用户的头像
     */
    void changeAvatar(String avatar, Integer uid);

    /**
     * 分页查询
     * @param pageNum
     * @param pageSize
     * @return
     */
    List<User> page(Integer pageNum, Integer pageSize, String username);

    /**
     * 编辑用户
     * @param user
     */
    int editUser(User user);

}
