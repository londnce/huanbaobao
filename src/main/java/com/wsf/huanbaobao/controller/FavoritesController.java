package com.wsf.huanbaobao.controller;

import com.github.pagehelper.PageInfo;
import com.wsf.huanbaobao.entity.Favorites;
import com.wsf.huanbaobao.service.FavoritesService;
import com.wsf.huanbaobao.utils.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/favorites")
public class FavoritesController extends BaseController{
    @Autowired
    private FavoritesService favoritesService;

    /**
     * 查询收藏商品
     * @param session 项目启动自动生成session对象
     * @param status 查询收藏商品的状态
     * @return
     **/
    @GetMapping("/queryFavorites")
    public JsonResult<PageInfo<Favorites>> findFavorites(HttpSession session, Integer pageNum, Integer pageSize, Integer status){
        Integer uid = getUidFromSession(session);
        PageInfo<Favorites> favorites = favoritesService.findFavorites(uid, pageNum,pageSize,status);

        return new JsonResult<>(OK,favorites);

    }

    /**
     * 添加收藏商品
     * @param session 项目启动自动生成session对象
     * @param pid 当前商品的pid
     * @return
     **/
    @PostMapping("/addFavorites")
    public JsonResult<Integer> addFavorites(HttpSession session, Integer pid){
        //从session中取出uid
        Integer uid = getUidFromSession(session);
        //执行插入操作并返回fid
        Integer fid = favoritesService.addFavorites(uid, pid);
        return new JsonResult<>(OK,fid);
    }

    /**
     * 取消收藏
     * @param session session 项目启动自动生成session对象
     * @param status 更新的状态
     * @param fid 收藏品的id
     * @return
     **/
    @PostMapping("/updateStatus")
    public JsonResult<Void> cancelFavorites(HttpSession session, Integer status, Integer fid){
        Integer uid = getUidFromSession(session);
        favoritesService.updateFavoritesStatus(status,fid,uid);
        return new JsonResult<>(OK);
    }
}
