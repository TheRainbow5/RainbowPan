package com.example.rainbowend.Service.LoginRegister.Impl;

import com.example.rainbowend.Dao.LoginRegister.RegisterDao;
import com.example.rainbowend.Entity.User;
import com.example.rainbowend.Entity.UserFile;
import com.example.rainbowend.Service.LoginRegister.RegisterService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Rainbow
 *
 * @DATE:2023/8/2 0002
 */
@Service
public class RegisterServiceImpl implements RegisterService {
    @Resource
    private RegisterDao registerDao;
    @Override
    public int registerNewUser(User user) {
        return registerDao.insertNewUser(user);
    }

    /**
     * 判断用户是否存在
     * @param user
     */
    @Override
    public User isExist(User user) {
        return  registerDao.isExist(user);
    }

    /**
     * 用户对应目录路径入库
     * @param userFile
     * @return
     */
    @Override
    public int addToUserFils(UserFile userFile) {
        return registerDao.addToUserFils(userFile);
    }
}
