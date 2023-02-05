package com.wsf.huanbaobao.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wsf.huanbaobao.entity.Admin;
import com.wsf.huanbaobao.entity.User;
import com.wsf.huanbaobao.vo.AdminPwVo;
import com.wsf.huanbaobao.vo.AdminVo;

/**
 * 管理员业务层接口
 */
public interface AdminService extends IService<Admin> {
    //管理员登录
    AdminVo login(AdminVo admin);

    //管理员修改密码
    int updatePw(AdminPwVo adminPwVo);

    /**
     * 编辑管理员
     * @param admin
     */
    int editAdmin(Admin admin);
}
