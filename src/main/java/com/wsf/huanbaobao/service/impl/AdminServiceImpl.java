package com.wsf.huanbaobao.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.log.Log;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wsf.huanbaobao.entity.Admin;
import com.wsf.huanbaobao.mapper.AdminMapper;
import com.wsf.huanbaobao.service.AdminService;
import com.wsf.huanbaobao.service.ex.AdminErrorException;
import com.wsf.huanbaobao.service.ex.PasswordNotMatchException;
import com.wsf.huanbaobao.service.ex.ServiceException;
import com.wsf.huanbaobao.service.ex.UpdateException;
import com.wsf.huanbaobao.utils.TokenUtils;
import com.wsf.huanbaobao.vo.AdminPwVo;
import com.wsf.huanbaobao.vo.AdminVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 管理员业务层接口实现类
 */
@Service
@Slf4j
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements AdminService {
    public static final Log LOG = Log.get();
    @Autowired
    private AdminMapper adminMapper;

    /**
     * 管理员登录
     * @param admin
     * @return
     */
    @Override
    public AdminVo login(AdminVo admin) {
        QueryWrapper<Admin> qw = new QueryWrapper<>();
        qw.eq("admin_name",admin.getAdminName());
        qw.eq("admin_pw",admin.getAdminPw());
        Admin one;
        try {
            one = getOne(qw);
        }catch (Exception e){
            LOG.error(e);
            throw new ServiceException("系统出现未知的异常");
        }
        if (one != null){
            BeanUtil.copyProperties(one,admin,true);
            //设置token
            String token = TokenUtils.genToken(one.getId().toString(), one.getAdminPw());
            admin.setToken(token);
            return admin;
        }else {
            throw new AdminErrorException("管理员名称或密码错误");
        }
    }

    /**
     * 管理员修改密码
     * @param adminPwVo
     */
    @Override
    public int updatePw(AdminPwVo adminPwVo) {
        int adminPw = adminMapper.updateAdminPw(adminPwVo);
        if (adminPw != 1){
            throw new UpdateException("管理员密码修改失败");
        }
        return adminPw;
    }

    /**
     * 编辑管理员
     * @param admin
     * @return
     */
    @Override
    public int editAdmin(Admin admin) {
        int rows = adminMapper.updateAdmin(admin);
        if (rows != 1){
            throw new UpdateException("编辑管理员时产生未知的异常");
        }
        return rows;
    }
}
