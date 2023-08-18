package com.example.rainbowend.Dao.Index;

import com.example.rainbowend.Entity.Files;
import org.apache.ibatis.annotations.*;

import java.util.List;

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
            "WHERE t.FILE_PATH=#{filePath}")
    int delete(String filePath);


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
     * 根据FILE_PATH查询子文件
     *
     * @param oldFilePath
     * @return 返回List集合
     */
    @Select("SELECT * FROM files t " +
            "WHERE t.FILE_PID=#{oldFilePath}")
    List<Files> getSubFiles(String oldFilePath);


    /**
     * 修改子文件路径
     *
     * @param item 子文件对象
     * @return
     */
    @Update("UPDATE files t " +
            "SET t.FILE_PID=#{file.filePid},t.FILE_PATH=#{file.filePath} " +
            "WHERE t.FILE_ID=#{file.fileId}")
    int resetsub(@Param("file") Files item);

    /**
     * 修改当前文件夹名称
     *
     * @param files
     * @return
     */
    @Update("UPDATE files t " +
            "SET t.FILE_NAME=#{file.fileName},t.FILE_PATH=#{file.filePath} " +
            "WHERE t.FILE_ID=#{file.fileId}")
    int resetDir(@Param("file") Files files);
}
