package com.wsf.huanbaobao.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MpConfig {
    //@Bean
    //public MybatisPlusInterceptor paginationInterceptor(){
    //    //新建MybatisPlus拦截器
    //    MybatisPlusInterceptor mybatisPlusInterceptor = new MybatisPlusInterceptor();
    //    mybatisPlusInterceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
    //    //添加组件，大功告成！
    //    return mybatisPlusInterceptor;
    //}
}
