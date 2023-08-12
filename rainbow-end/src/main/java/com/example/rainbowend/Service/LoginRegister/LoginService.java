package com.example.rainbowend.Service.LoginRegister;

import com.example.rainbowend.Entity.User;

/**
 * Rainbow
 *
 * @DATE:2023/8/2 0002
 */
public interface LoginService {
    User getUser(String email);
}
