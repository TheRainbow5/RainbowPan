package com.example.rainbowend.Service.LoginRegister.Impl;

import com.example.rainbowend.Dao.LoginRegister.ForgetPasswordDao;
import com.example.rainbowend.Entity.User;
import com.example.rainbowend.Service.LoginRegister.ForgetPasswordService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Rainbow
 *
 * @DATE:2023/8/2 0002
 */
@Service
public class ForgetPasswordServiceImpl implements ForgetPasswordService {
    @Resource
    private ForgetPasswordDao forgetPasswordDao;

    /**
     * 判断账号是否存在
     * @param userInput
     */
    @Override
    public User isExist(User userInput) {
        return forgetPasswordDao.isExist(userInput);
    }
    /**
     * 修改密码
     */
    @Override
    public int modifyPW(User userInput) {
        return  forgetPasswordDao.modifyPW(userInput);
    }
}
