package com.example.rainbowend.Entity;

import io.swagger.annotations.ApiModel;
import lombok.*;
import org.springframework.lang.Nullable;


/**
 * Rainbow
 *
 * @DATE:2023/8/2 0002
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private String username;   //用户名
    private String password;   //密码
    private String email;      //邮箱
    private String imgurl;  //图片访问链接
    private String root;  //根目录
}


