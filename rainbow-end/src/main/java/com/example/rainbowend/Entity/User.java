package com.example.rainbowend.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * Rainbow
 *
 * @DATE:2023/8/2 0002
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private String email;      //邮箱
    private String username;   //用户名
    private String password;   //密码
    private String imgUrl;  //图片访问链接
}


