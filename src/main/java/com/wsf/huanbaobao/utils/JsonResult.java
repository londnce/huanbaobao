package com.wsf.huanbaobao.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Json格式的数据进行响应
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class JsonResult<T> implements Serializable {
    /*状态码*/
    private Integer status;
    /*描述信息*/
    private String message;
    /*数据*/
    private T data;

    public JsonResult(Integer status){
        this.status = status;
    }

    public JsonResult(Throwable e){
        this.message = e.getMessage();
    }

    public JsonResult(Integer status,T data){
        this.status = status;
        this.data = data;
    }

}
