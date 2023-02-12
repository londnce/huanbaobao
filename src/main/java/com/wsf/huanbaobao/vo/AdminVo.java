package com.wsf.huanbaobao.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminVo {
    private String adminName;
    private String adminPw;
    private String email;
    private String role;
    private String token;
}
