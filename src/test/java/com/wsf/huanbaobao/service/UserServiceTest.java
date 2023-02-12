package com.wsf.huanbaobao.service;

import com.wsf.huanbaobao.entity.Product;
import com.wsf.huanbaobao.entity.User;
import com.wsf.huanbaobao.mapper.ProductMapper;
import com.wsf.huanbaobao.mapper.UserMapper;
import org.assertj.core.util.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class UserServiceTest {
    @Autowired
    private UserService userService;

    @Autowired
    private UserMapper userMapper;

    @Test
    public void reg(){
        try {
            User user = new User();
            user.setUsername("test3");
            user.setPassword("123");
            userService.reg(user);
            System.out.println();
        } catch (Exception e) {
            //获取类的对象，再获取类的名称
            System.out.println(e.getClass().getSimpleName());
            //获取异常的具体描述信息
            System.out.println(e.getMessage());
        }
    }

    //@Test
    //public void login(){
    //    User user = userService.login("test02", "123456");
    //    System.out.println(user);
    //}

    @Test
    public void updatePasswordByUid(){
        userMapper.updatePasswordByUid(7,"321","管理员",new Date());
    }

    @Test
    public void findByUid(){
        System.out.println(userMapper.selectById(7));;
    }

    @Test
    public void changePassword(){
        userService.changePassword(8,"test03","123456","654321");
    }

    @Test
    public void getByUid() {
        System.out.println(userService.getByUid(8));
    }

    @Test
    public void changeInfo() {
        User user = new User();
        user.setPhone("17842000000");
        user.setEmail("test08@qq.com");
        user.setGender(0);
            userService.changeInfo(8,"test03",user);
    }
    @Autowired
    ProductMapper productMapper;

    @Test
    public void test1() {
        List<Product> products = productMapper.queryProductByTitle("日程");
        System.out.println(products.toString());
    }

    @Test
    public void test() {
        List<Integer> list = Lists.newArrayList();
        list.add(1);
        list.add(2);
        list.add(2);
        list.add(2);
        list.add(2);
        for(int i=list.size()-1;i>=0;i--){
            if(list.get(i)==2){
                list.remove(i);
                System.out.println("集合："+list+"数量："+i);
            }
        }
    }
}
