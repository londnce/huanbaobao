package com.wsf.huanbaobao.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminPwVo {
    private String adminName;
    private String adminPw;
    private String newPw;
}
