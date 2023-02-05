package com.wsf.huanbaobao.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wsf.huanbaobao.entity.User;
import com.wsf.huanbaobao.mapper.UserMapper;
import com.wsf.huanbaobao.service.UserService;
import com.wsf.huanbaobao.service.ex.*;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * 用户模块业务层接口实现类
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Autowired
    private UserMapper userMapper;

    /**
     * 用户注册
     * @param user
     */
    @Override
    public void reg(User user) {
        //判断参数是否为空
        if (user == null){
           throw new UserParameterException("用户名或密码不能为空");
        }
        //通过user参数来获取传递过来的用户名和密码
        String username = user.getUsername();
        String password = user.getPassword();
        //设置查询条件
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username",username);
        //调用selectOne判断用户是否被注册过
        User result = userMapper.selectOne(queryWrapper);
        //判断结果集是否不为null则显示异常信息
        if (result != null){
            throw new UsernameDuplicateException("用户名被占用");
        }
        //判断用户密码是否为空
        if (StringUtils.isBlank(password)){
            throw new PasswordNotNullException("密码不能为空");
        }

        //密码加密处理的实现：MD5算法
        //串 + password + 串 ------ MD5算法进行加密,连续加载三次
        //盐值 + password + 盐值  ------盐值就是一个随机的字符串
        String oldPassword = user.getPassword();
        //获取盐值（随机生成一个盐值）
        String salt = UUID.randomUUID().toString().toUpperCase();
        //补全数据： 盐值的记录
        user.setSalt(salt);
        //将密码和盐值作为一个整体进行加密处理,忽略原有密码强度提升了数据的安全性
        String md5Password = getMD5Password(oldPassword, salt);
        //将加密之后的密码重新补全设置到user对象中
        user.setPassword(md5Password);

        //补全数据： is_delete设置为0
        user.setIsDelete(0);
        //补全数据： 4个日志字段信息
        user.setCreatedUser(user.getUsername());
        user.setModifiedUser(user.getUsername());
        Date date = new Date();
        user.setCreatedTime(date);
        user.setModifiedTime(date);
        //执行注册业务功能的实现
        int rows = userMapper.insert(user);
        if (rows != 1){
            //注册失败
            throw new InsertException("注册时产生未知的错误");
        }
    }

    /**
     * 用户登录
     * @param user
     * @return
     */
    @Override
    public User login(User user) {
        //根据用户名称来查询用户的数据是否存在，如果不存在则抛出异常
        //设置查询条件
        //用户名
        String username = user.getUsername();
        //用户输入的密码
        String password = user.getPassword();
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username",username);
        //调用selectOne查看用户是否存在
        User result = userMapper.selectOne(queryWrapper);
        if (result == null){
            throw new UserNotFoundException("用户数据不存在");
        }
        //检测用户的密码是否匹配
        //1.先获取到数据库中的加密之后的密码
        String oldPassword = result.getPassword();
        //2.和用户传递过来的密码进行比较
        //2.1 先获取盐值：上一次在注册时所自动生成的盐值
        String salt = result.getSalt();
        //2.2 将用户的密码按照相同的md5算法的规则进行加密
        String newPassword = getMD5Password(password,salt);
        //3. 将密码进行比较
        if (!newPassword.equals(oldPassword)){
            throw new PasswordNotMatchException("用户密码错误");
        }
        //判断is_delete字段是否为一表示被标记为删除
        if (result.getIsDelete() == 1){
            throw new UserNotFoundException("用户数据不存在");
        }
        //调用mapper层的selectOne来查询用户的数据，提高系统的性能
        User newUser = new User();
        newUser.setUid(result.getUid());
        newUser.setUsername(result.getUsername());
        newUser.setAvatar(result.getAvatar());
        //将当前的用户数据返回，返回的数据是为了辅助其他页面做数据展示使用
        return newUser;
    }

    /**
     * 用户修改密码方法
     * @param uid
     * @param username
     * @param oldPassword
     * @param newPassword
     */
    @Override
    public void changePassword(Integer uid, String username, String oldPassword, String newPassword) {
        User result = userMapper.selectById(uid);
        if (result == null || (result.getIsDelete() == 1 && result.getIsDelete() != null)){
            throw new UserNotFoundException("用户数据不存在");
        }
        //原始密码和数据库中的密码进行比较
        String oldMd5Password = getMD5Password(oldPassword,result.getSalt());
        if (!result.getPassword().equals(oldMd5Password)){
            throw new PasswordNotMatchException("密码错误");
        }
        //将新的密码设置到数据库中，将新的密码进行加密再去更新
        String newMd5Password = getMD5Password(newPassword, result.getSalt());
        Integer rows = userMapper.updatePasswordByUid(uid, newMd5Password, username, new Date());
        if (rows != 1){
            throw new UpdateException("数据更新数据产生未知的异常");
        }
    }

    /**
     * 根据用户的uid来获取用户的数据
     * @param uid
     * @return
     */
    @Override
    public User getByUid(Integer uid) {
        User result = userMapper.selectById(uid);
        if (result == null || (result.getIsDelete() ==1 && result.getIsDelete() != null)){
            throw new UserNotFoundException("用户数据不存在");
        }
        User user = new User();
        user.setUsername(result.getUsername());
        user.setUid(result.getUid());
        user.setGender(result.getGender());
        user.setPhone(result.getPhone());
        user.setEmail(result.getEmail());
        user.setAvatar(result.getAvatar());
        return user;
    }

    /**
     * User对象中的数据phone/email/gender，手动将uid/username封装
     * @param uid   用户的id
     * @param username  用户的名称
     * @param user  用户的对象数据
     */
    @Override
    public void changeInfo(Integer uid, String username, User user) {
        User result = userMapper.selectById(uid);
        if (result == null || (result.getIsDelete() ==1 && result.getIsDelete() != null)){
            throw new UserNotFoundException("用户数据不存在");
        }
        user.setUid(uid);
        user.setModifiedUser(username);
        user.setModifiedTime(new Date());
        Integer rows = userMapper.updateInfoByUid(user);
        if (rows != 1){
            throw new UpdateException("更新用户信息时产生未知的异常");
        }
    }

    /**
     * 修改用户头像
     * @param uid   用户的id
     * @param avatar    用户的头像
     */
    @Override
    public void changeAvatar(String avatar, Integer uid) {
        //查询当前的用户数据是否存在
        User result = userMapper.selectById(uid);
        if (result == null || (result.getIsDelete().equals(1) && result.getIsDelete() != null)){
            throw new UserNotFoundException("用户数据不存在");
        }
        Integer rows = userMapper.updateAvatarByUid(uid, avatar, result.getUsername(), new Date());
        if (rows != 1){
            throw new UpdateException("更新用户头像时产生未知的异常");
        }

    }

    /**
     * 分页查询
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public List<User> page(Integer pageNum, Integer pageSize, String username) {
        pageNum = (pageNum - 1) * pageSize;
        List<User> page = userMapper.page(pageNum,pageSize,username);
        return page;
    }

    /**
     * 编辑用户
     * @param user
     * @return
     */
    @Override
    public int editUser(User user) {
        int rows = userMapper.updateUser(user);
        if (rows != 1){
            throw new UpdateException("编辑用户时产生未知的异常");
        }
        return rows;
    }

    /**
     * 定义MD5算法的加密处理
     */
    private String getMD5Password(String password,String salt){
        //md5加密算法方法调用(进行三次加密)
        for (int i=0;i<3;i++){
           password = DigestUtils.md5DigestAsHex((salt+password).getBytes()).toUpperCase();
        }
        //返回加密之后的密码
        return password;
    }
}
