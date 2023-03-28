package com.wsf.huanbaobao.controller.admin;

import com.wsf.huanbaobao.controller.BaseController;
import com.wsf.huanbaobao.entity.User;
import com.wsf.huanbaobao.mapper.UserMapper;
import com.wsf.huanbaobao.service.UserService;
import com.wsf.huanbaobao.utils.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin/user")
public class UserManageController extends BaseController {
    @Autowired
    private UserService userService;
    @Autowired
    private UserMapper userMapper;

    //查询所有用户
    @GetMapping("/findAll")
    public JsonResult<List<User>> queryAll(){
        List<User> users = userService.list();
        return new JsonResult<>(OK,users);
    }

    //编辑用户
    @PutMapping("/edit")
    public JsonResult<Void> editUser(@RequestBody User user){
        userService.editUser(user);
        return new JsonResult<>(OK);
    }

    //删除用户
    @DeleteMapping("/delete/{uid}")
    public JsonResult<Void> deleteUser(@PathVariable("uid") Integer uid){
        userService.removeById(uid);
        return new JsonResult<>(OK);
    }

    //分页查询
    @GetMapping("/page")
    public JsonResult<Map<String , Object>> page(@RequestParam Integer pageNum,
                                           @RequestParam Integer pageSize,
                                           @RequestParam(defaultValue = "") String username){
        List<User> data = userService.page(pageNum, pageSize,username);
        Integer total = userMapper.count(username);
        Map<String , Object> res = new HashMap<>();
        res.put("data", data);
        res.put("total",total);
        return new JsonResult<>(OK,res);
    }


}
