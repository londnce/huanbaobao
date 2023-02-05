package com.wsf.huanbaobao.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.wsf.huanbaobao.entity.Favorites;

/**
 * 收藏商品的业务层接口
 */
public interface FavoritesService extends IService<Favorites> {

    /**
     * 添加收藏商品
     * @param uid
     * @param pid
     * @return
     */
    Integer addFavorites(Integer uid, Integer pid);

    /**
     * 查询收藏商品
     * @param uid
     * @param pageNum
     * @param pageSize
     * @param status
     * @return
     */
    PageInfo<Favorites> findFavorites(Integer uid, Integer pageNum, Integer pageSize, Integer status);

    /**
     * 根据收藏商品fid和用户uid取消对应商品收藏
     * @param status
     * @param fid
     * @param uid
     * @return
     */
    Integer updateFavoritesStatus(Integer status, Integer fid, Integer uid);
}
