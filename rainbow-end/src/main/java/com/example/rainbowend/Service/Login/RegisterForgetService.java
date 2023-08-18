package com.example.rainbowend.Service.Login;

import com.example.rainbowend.Entity.ResponseResult;
import com.example.rainbowend.Entity.User;

/**
 * Rainbow
 *
 * @DATE:2023/8/2 0002
 */
public interface RegisterForgetService {
    ResponseResult registerNewUser(User user);

    ResponseResult modifyPW(User user);
}
