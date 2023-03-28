package com.wsf.huanbaobao.controller;

import com.wsf.huanbaobao.controller.ex.*;
import com.wsf.huanbaobao.service.ex.*;
import com.wsf.huanbaobao.utils.JsonResult;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.apache.tomcat.util.http.fileupload.impl.FileUploadIOException;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpSession;
import java.nio.file.AccessDeniedException;

/** 控制器类的基类 */
public class BaseController {
    /** 操作成功的状态码 */
    public static final int OK = 200;

    /** 操作失败的状态码 */
    public static final int Error = 400;

    /**
     * 从HttpSession对象中获取uid
     * @param session HttpSession对象
     * @return 当前登录的用户的id
     */
    protected final Integer getUidFromSession(HttpSession session) {
        return Integer.valueOf(session.getAttribute("uid").toString());
    }

    /**
     * 从HttpSession对象中获取用户名
     * @param session HttpSession对象
     * @return 当前登录的用户名
     */
    protected final String getUsernameFromSession(HttpSession session) {
        return session.getAttribute("username").toString();
    }

    /**
     * 从HttpSession对象中获取id
     * @param session HttpSession对象
     * @return 当前登录的管理员的id
     */
    protected final Integer getIdFromSession(HttpSession session) {
        return Integer.valueOf(session.getAttribute("id").toString());
    }

    /**
     * 从HttpSession对象中获取管理员名称
     * @param session HttpSession对象
     * @return 当前登录的管理员
     */
    protected final String getAdminNameFromSession(HttpSession session) {
        return session.getAttribute("adminName").toString();
    }

    /** @ExceptionHandler 用于统一处理方法抛出的异常 */
    @ExceptionHandler({ServiceException.class, FileUploadException.class, ValidCodeNotMatchException.class})
    public JsonResult<Void> handleException(Throwable e) {
        JsonResult<Void> result = new JsonResult<Void>(e);
        if (e instanceof UsernameDuplicateException) {
            result.setStatus(4000);//用户名被占用的异常
        } else if (e instanceof UserNotFoundException) {
            result.setStatus(4001);//用户数据不存在的异常
        } else if (e instanceof PasswordNotMatchException) {
            result.setStatus(4002);//用户的密码错误的异常
        } else if (e instanceof AddressCountLimitException) {
            result.setStatus(4003);//收货地址数量达到上限的异常
        } else if (e instanceof AddressNotFoundException) {
            result.setStatus(4004);//地址不存在异常
        } else if (e instanceof AccessDeniedException) {
            result.setStatus(4005);//非法访问的异常
        } else if (e instanceof ProductNotFoundException) {
            result.setStatus(4006);//商品数据不存在的异常
        } else if (e instanceof CartNotFoundException) {
            result.setStatus(4007);//购物车数据不存在的异常
        } else if (e instanceof InsertException) {
            result.setStatus(5000);//插入数据时产生的未知的异常
        } else if (e instanceof UpdateException) {
            result.setStatus(5001);//更新数据时产生了异常
        } else if (e instanceof DeleteException) {
            result.setStatus(5002);//删除数据失败的异常
        } else if (e instanceof FileEmptyException) {
            result.setStatus(6000);//上传的文件为空的异常
        } else if (e instanceof FileSizeException) {
            result.setStatus(6001);//上传的文件的大小超出了限制值的异常
        } else if (e instanceof FileTypeException) {
            result.setStatus(6002);//上传的文件类型超出了限制的异常
        } else if (e instanceof FileStateException) {
            result.setStatus(6003);//上传的文件状态异常
        } else if (e instanceof FileUploadIOException) {
            result.setStatus(6004);//上传文件时读写异常
        }else if (e instanceof ValidCodeNotMatchException){
            result.setStatus(1001); //表示验证码错误
        }else if (e instanceof OrderNotExistsException) {
            result.setStatus(3000); //表示查询的order数据不存在
        }else if (e instanceof CateException) {
            result.setStatus(3001); //表示查询的分类数据失败
        }
        return result;
    }
}
