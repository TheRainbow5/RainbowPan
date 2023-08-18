package com.example.rainbowend.Service.Login.Impl;

import com.example.rainbowend.Dao.Login.LoginUserDao;
import com.example.rainbowend.Entity.User;
import com.example.rainbowend.Service.Login.LoginService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Rainbow
 *
 * @DATE:2023/8/2 0002
 */
@Service
public class LoginServiceImpl implements LoginService {
    @Resource
    private LoginUserDao loginUserDao;

    /**
     * 查询用户是否存在
     */
    @Override
    public User getUser(String email) {
        return loginUserDao.getUser(email);
    }
}
