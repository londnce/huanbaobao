package com.wsf.huanbaobao.controller.admin;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wsf.huanbaobao.controller.BaseController;
import com.wsf.huanbaobao.entity.Admin;
import com.wsf.huanbaobao.service.AdminService;
import com.wsf.huanbaobao.service.ex.AdminNullException;
import com.wsf.huanbaobao.utils.JsonResult;
import com.wsf.huanbaobao.vo.AdminPwVo;
import com.wsf.huanbaobao.vo.AdminVo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
public class AdminController extends BaseController {
    @Autowired
    private AdminService adminService;

    //管理员登录
    @PostMapping("/login")
    public JsonResult<AdminVo> login(@RequestBody AdminVo admin){
        String adminName = admin.getAdminName();
        String adminPw = admin.getAdminPw();
        if (StringUtils.isBlank(adminName) || StringUtils.isBlank(adminPw)){
            throw new AdminNullException("管理员名称或密码不能为空");
        }
        //执行登录操作
        AdminVo login = adminService.login(admin);
        return new JsonResult<>(OK,login);
    }

    //根据管理员名称获取管理员所有属性
    @GetMapping("/{adminName}")
    public JsonResult<Admin> findByAdminName(@PathVariable String adminName){
        QueryWrapper<Admin> qw = new QueryWrapper<>();
        qw.eq("admin_name",adminName);
        Admin admin = adminService.getOne(qw);
        return new JsonResult<>(OK,admin);
    }

    //编辑管理员
    @PutMapping("/editAdmin")
    public JsonResult<Void> editAdmin(@RequestBody Admin admin){
        adminService.editAdmin(admin);
        return new JsonResult<>(OK);
    }

    //修改密码
    @PostMapping("/editPw")
    public JsonResult<Void> updatePw(@RequestBody AdminPwVo adminPwVo){
        adminService.updatePw(adminPwVo);
        return new JsonResult<>(OK);
    }
}
