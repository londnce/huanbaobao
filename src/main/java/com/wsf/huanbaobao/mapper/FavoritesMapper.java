package com.wsf.huanbaobao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wsf.huanbaobao.entity.Favorites;
import java.util.List;

/**
 * 收藏mapper接口
 */
public interface FavoritesMapper extends BaseMapper<Favorites> {

    /**
     * 新增收藏商品
     * @param favorites
     * @return
     */
    Integer addFavorites(Favorites favorites);

    /**
     * 根据uid和收藏商品状态查询收藏的商品信息
     * @param uid
     * @param status
     * @return
     */
    List<Favorites> findFavoritesByUidAndStatus(Integer uid, Integer status);

    /**
     * 根据收藏商品pid和用户uid取消对应商品收藏
     * @param status
     * @param fid
     * @param uid
     * @return
     */
    Integer updateFavoritesStatus(Integer status, Integer fid, Integer uid);

}
