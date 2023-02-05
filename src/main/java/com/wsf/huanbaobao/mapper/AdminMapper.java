package com.wsf.huanbaobao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wsf.huanbaobao.entity.Admin;
import com.wsf.huanbaobao.vo.AdminPwVo;
import org.apache.ibatis.annotations.Update;

/**
 * 管理员mapper接口
 */
public interface AdminMapper extends BaseMapper<Admin> {

    /**
     * 编辑管理员
     * @param admin
     * @return
     */
    int updateAdmin(Admin admin);

    /**
     * 修改管理员密码
     * @param adminPwVo
     * @return
     */
    int updateAdminPw(AdminPwVo adminPwVo);
}
