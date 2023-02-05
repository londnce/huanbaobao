package com.wsf.huanbaobao.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wsf.huanbaobao.entity.Favorites;
import com.wsf.huanbaobao.entity.Product;
import com.wsf.huanbaobao.mapper.FavoritesMapper;
import com.wsf.huanbaobao.service.FavoritesService;
import com.wsf.huanbaobao.service.ProductService;
import com.wsf.huanbaobao.service.ex.InsertException;
import com.wsf.huanbaobao.service.ex.UpdateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 收藏商品模块业务层接口实现类
 */
@Service
public class FavoritesServiceImpl extends ServiceImpl<FavoritesMapper,Favorites> implements FavoritesService {
    @Autowired
    private FavoritesMapper favoritesMapper;
    @Autowired
    private ProductService productService;

    /**
     * 新增收藏商品
     * @param uid 用户uid
     * @param pid 商品pid
     * @return int
     **/
    @Override
    public Integer addFavorites(Integer uid,Integer pid) {
        Favorites favorites = new Favorites();

        //根据pid查询商品信息
        Product product = productService.queryProductById(pid);

        //填充favorites对象空白字段
        favorites.setUid(uid);
        favorites.setPid(pid);
        favorites.setImage(product.getImage());
        favorites.setPrice(product.getPrice());
        favorites.setTitle(product.getTitle());
        favorites.setSellPoint(product.getSellPoint());
        favorites.setStatus(1);

        Integer result = favoritesMapper.addFavorites(favorites);
        if (result == 0){
            throw new InsertException("服务器异常，收藏商品失败");
        }

        //取出fid返回给前端页面，以便在搜索界面取消收藏使用
        return favorites.getFid();
    }

    /**
     * 根据uid和商品收藏状态查询收藏数据
     * @param uid 用户uid
     * @param status 查询商品状态
     * @return
     **/
    @Override
    public PageInfo<Favorites> findFavorites(Integer uid, Integer pageNum,Integer pageSize,Integer status) {
        //开启分页功能，pageNum是当前页，pageSize是每页显示的数据量，这两个值都可以选择让前端传或者自己调整
        PageHelper.startPage(pageNum,pageSize);
        List<Favorites> favorites = favoritesMapper.findFavoritesByUidAndStatus(uid, status);
        PageInfo<Favorites> pageInfo = new PageInfo<>(favorites);
        return pageInfo;
    }

    /**
     * 根据收藏商品pid和用户uid取消对应商品收藏
     * @param status 取消收藏的状态
     * @param fid 收藏的fid
     * @param uid 用户uid
     * @return
     **/
    @Override
    public Integer updateFavoritesStatus(Integer status, Integer fid, Integer uid) {
        Integer result = favoritesMapper.updateFavoritesStatus(status, fid, uid);

        if (result == 0){
            throw new UpdateException("服务器异常，取消收藏失败");
        }
        return result;
    }
}
