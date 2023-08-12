package com.example.rainbowend.Service.LoginRegister;

import com.example.rainbowend.Entity.User;

/**
 * Rainbow
 *
 * @DATE:2023/8/2 0002
 */
public interface ForgetPasswordService {
    int modifyPW(User userInput);

    User isExist(User userInput);
}
