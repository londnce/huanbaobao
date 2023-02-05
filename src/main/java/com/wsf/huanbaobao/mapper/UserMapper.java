package com.wsf.huanbaobao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.pagehelper.PageInfo;
import com.wsf.huanbaobao.entity.User;
import org.apache.ibatis.annotations.Select;

import java.util.Date;
import java.util.List;

/**
 * 用户mapper接口
 */
//@Mapper
public interface UserMapper extends BaseMapper<User> {

    /**
     * 根据用户的uid来修改用户的密码
     * @param uid   用户的uid
     * @param modifiedUser  表示修改的执行者
     * @param modifiedTime  表示数据修改的时间
     * @return  返回值为受影响的行数
     */
    Integer updatePasswordByUid(Integer uid, String password, String modifiedUser, Date modifiedTime);

    /**
     * 更新用户的个人信息
     * @param user  用户的数据
     * @return      返回受影响的行数
     */
    Integer updateInfoByUid(User user);

    /**
     * 修改用户的头像
     * @param uid
     * @param avatar
     * @param modifiedUser
     * @param modifiedTime
     * @return
     */
    Integer updateAvatarByUid(Integer uid,String avatar,String modifiedUser,Date modifiedTime);

    /**
     * 分页查询
     * @param pageNum
     * @param pageSize
     * @param username
     * @return
     */
    @Select("select * from t_user where username like concat('%',#{username},'%') limit #{pageNum},#{pageSize}")
    List<User> page(Integer pageNum, Integer pageSize, String username);

    /**
     * 统计查询
     * @param username
     * @return
     */
    @Select("select count(*) from t_user where username like concat('%',#{username},'%')")
    Integer count(String username);

    /**
     * 更新用户
     * @param user
     * @return
     */
    int updateUser(User user);
}
