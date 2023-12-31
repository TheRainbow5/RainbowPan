package com.example.rainbowend.Dao.Index;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.rainbowend.Entity.Files;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Rainbow
 *
 * @DATE:2023/8/10 0010
 */
@Mapper
public interface IndexFileDao {
    /**
     * 判断文件夹路径是否存在
     */
    @Select("SELECT t.* FROM files t " +
            "WHERE t.FILE_NAME=#{files.fileName} AND t.FILE_PID=#{files.filePid} AND t.FOLDER_TYPE=#{files.folderType}")
    Files existDir(@Param("files") Files files);

    /**
     * 保存文件夹访问路径
     */
    @Insert("INSERT INTO files " +
            "(FILE_ID,FILE_NAME,EMAIL,FILE_PID,FILE_PATH," +
            "FILE_SIZE,CREATE_TIME,UPDATE_TIME,FOLDER_TYPE,STATUS) " +
            "VALUES(#{files.fileId},#{files.fileName},#{files.email},#{files.filePid},#{files.filePath}," +
            "#{files.fileSize},#{files.createTime},#{files.updateTime},#{files.folderType},#{files.status})")
    int saveDir(@Param("files") Files files);


    /**
     * 支持分页查询目录下所有子文件
     *
     * @param filePid
     * @return
     */
    @Select("SELECT t.* FROM files t " +
            "WHERE t.FILE_PID=#{filePid}" +
            "ORDER BY t.FOLDER_TYPE DESC")
    List<Files> getAllFiles(Page page, String filePid);


    /**
     * 查询一共多少条数据
     *
     * @param filePid
     * @return
     */
    @Select("SELECT * FROM  files " +
            "WHERE FILE_PID=#{filePid}")
    List<Files> getAllFilesNum(String filePid);


    /**
     * 判断同类型的文件是否重名
     *
     * @param files
     * @return
     */
    @Select("SELECT t.* FROM files t " +
            "WHERE t.FILE_NAME=#{files.fileName} AND t.FILE_PID=#{files.filePid}" +
            " AND t.FOLDER_TYPE=#{files.folderType} AND t.FILE_CATEGORY=#{files.fileCategory}")
    Files existFile(@Param("files") Files files);

    /**
     * 保存文件信息
     *
     * @param files
     * @return
     */
    @Insert("INSERT INTO files " +
            "(FILE_ID,FILE_NAME,EMAIL,FILE_PID,FILE_PATH,FILE_SIZE," +
            "CREATE_TIME,UPDATE_TIME,FOLDER_TYPE,FILE_CATEGORY,STATUS) " +
            "VALUES(#{files.fileId},#{files.fileName},#{files.email},#{files.filePid},#{files.filePath},#{files.fileSize}," +
            "#{files.createTime},#{files.updateTime},#{files.folderType},#{files.fileCategory},#{files.status})")
    int saveFile(@Param("files") Files files);


    @Select("SELECT COUNT(*) FROM files t " +
            "WHERE t.FILE_NAME LIKE CONCAT('%',#{fileName},'%')")
    int getAllFilesNumByFileName(String fileName);

    @Select("SELECT t.* FROM files t " +
            "WHERE t.FILE_NAME LIKE CONCAT('%',#{fileName},'%') " +
            "ORDER BY t.FOLDER_TYPE DESC")
    List<Files> getAllFilesByFileName(Page page, String fileName);
}
