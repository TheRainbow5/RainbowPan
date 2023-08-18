package com.example.rainbowend.Entity;

import lombok.Data;

/**
 * Rainbow
 *
 * @DATE:2023/8/10 0010
 */
@Data
public class Files {
    private String fileId;  //文件id
    private String fileName; // 文件名
    private String email; // 邮箱
    private String filePid;  // 文件的父id
    private String filePath; // 文件存储路径
    private String fileSize;  // 文件大小
    private String fileCover;  // 文件封面存储路径
    private String createTime; // 创建时间
    private String updateTime; // 更新时间
    private int folderType; // 标识文件或目录
    private String fileCategory; // 文件种类
    private String recoveryTime;  // 回收时间
    private String status; // 状态，删除/正常
}

