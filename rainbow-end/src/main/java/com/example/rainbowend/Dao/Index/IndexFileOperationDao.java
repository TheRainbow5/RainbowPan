package com.example.rainbowend.Dao.Index;

import com.example.rainbowend.Entity.Files;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * Rainbow
 *
 * @DATE:2023/8/14 0014
 */
@Mapper
public interface IndexFileOperationDao {


    /**
     * 查看是否存在
     *
     * @param fileId
     */
    @Select("SELECT t.* FROM files t " +
            "WHERE t.FILE_ID=#{fileId}")
    Files exist(String fileId);


    /**
     * 修改文件名
     */
    @Update("UPDATE files t " +
            "SET t.FILE_NAME=#{newFileName},t.FILE_PATH=#{newFilePath} " +
            "WHERE t.FILE_ID=#{fileId}")
    int resetFileName(String fileId, String newFileName, String newFilePath);


    /**
     * 删除目录
     *
     * @return
     */
    @Delete("DELETE FROM files t " +
            "WHERE t.FILE_PATH=#{filePath} OR t.FILE_PID=#{filePath}")
    int delete(String filePath);

    /**
     * 删除子文件
     *
     * @param filePid
     */
    @Delete("DELETE FROM files t " +
            "WHERE t.FILE_PID=#{filePid}")
    int deleteFile(String filePid);


    /**
     * 判断文件是否存在
     *
     * @param filePid
     * @param newFileName
     * @return
     */
    @Select("SELECT t.* FROM files t " +
            "WHERE t.FILE_PID=#{filePid} AND t.FILE_NAME=#{newFileName}")
    Files existFile(String filePid, String newFileName);

    /**
     * 修改子文件的父路径
     *
     * @param oldSubFilePid
     * @param newSubFilePid
     * @return
     */
    @Update("UPDATE files t " +
            "SET t.FILE_PID=#{newSubFilePid} " +
            "WHERE t.FILE_PID=#{oldSubFilePid}")
    int resetSubFilePid(String oldSubFilePid, String newSubFilePid);
}
