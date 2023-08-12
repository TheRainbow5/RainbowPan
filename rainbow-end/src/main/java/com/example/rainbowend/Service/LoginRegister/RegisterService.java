package com.example.rainbowend.Service.LoginRegister;

import com.example.rainbowend.Entity.User;
import com.example.rainbowend.Entity.UserFile;

/**
 * Rainbow
 *
 * @DATE:2023/8/2 0002
 */
public interface RegisterService {

    int registerNewUser(User user);

    User isExist(User user);

    int addToUserFils(UserFile userFile);
}
