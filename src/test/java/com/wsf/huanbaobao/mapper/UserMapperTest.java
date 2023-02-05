package com.wsf.huanbaobao.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wsf.huanbaobao.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;
import java.util.Map;

@SpringBootTest
@RunWith(SpringRunner.class)
public class UserMapperTest {
    @Autowired
    private UserMapper userMapper;

    @Test
    public void insertUser(){
        User user = new User();
        user.setUsername("test");
        user.setPassword("123");
        int result = userMapper.insert(user);
        System.out.println(result);
    }

    @Test
    public void selectUser(){
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        User user = new User();
        queryWrapper.eq("username","test");
        List<Map<String, Object>> result = userMapper.selectMaps(queryWrapper);
        System.out.println(result);
    }

    @Test
    public void updateInfo(){
        User user = new User();
        user.setUid(8);
        user.setPhone("152468245632");
        user.setEmail("qqqccs@qq.com");
        user.setGender(1);
        userMapper.updateInfoByUid(user);
    }

    @Test
    public void updateAvatar(){
        userMapper.updateAvatarByUid(8,"/upload/avatar.png","test03",new Date());
    }
}
