package com.example.rainbowend.Utils;

import java.io.File;

/**
 * Rainbow
 * 文件工具类
 *
 * @DATE:2023/8/15 0015
 */

public class FileOperationUtil {


    /**
     * 递归删除文件即子文件（完美的算法！！！！！！！！！！！！！！）
     *
     * @param folder
     */
    public static void deleteFolder(File folder) {
        if (folder.isDirectory()) {   //如果是文件夹
            File[] files = folder.listFiles();  //所有子文件和子文件夹
            if (files != null) {   //递归删除子文件
                for (File file : files) {
                    deleteFolder(file);
                }
            }
        }
        //删除文件和文件夹
        folder.delete();
    }
}
