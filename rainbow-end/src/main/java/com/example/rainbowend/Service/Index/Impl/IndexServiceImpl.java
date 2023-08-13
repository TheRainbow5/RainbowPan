package com.example.rainbowend.Service.Index.Impl;

import com.example.rainbowend.Dao.Index.IndexDao;
import com.example.rainbowend.Entity.User;
import com.example.rainbowend.Service.Index.IndexService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Rainbow
 *
 * @DATE:2023/8/7
 */
@Service
public class IndexServiceImpl implements IndexService {
    @Resource
    private IndexDao indexDao;

    /**
     * 获取用户信息
     * @return
     */
    @Override
    public User getUserInfo(String email) {
        return indexDao.getUserInfo(email);
    }

    /**
     * 保存图片路径
     * @param imgUrl
     * @return
     */
    @Override
    public int saveImgUrl(String email,String imgUrl) {
        return indexDao.saveImgUrl(email,imgUrl);
    }

    /**
     * 获取图片访问路径
     * @param email
     * @return
     */
    @Override
    public User getUserImageUrl(String email) {
        return indexDao.getUserImageUrl(email);
    }
}
