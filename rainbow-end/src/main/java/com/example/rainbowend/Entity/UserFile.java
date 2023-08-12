package com.example.rainbowend.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Rainbow
 *
 * @DATE:2023/8/10 0010
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserFile {
    private String email;    //邮箱
    private String dir;    //文件夹
    private String file;   //文件路径
}
