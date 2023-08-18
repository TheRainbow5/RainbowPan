package com.example.rainbowend.Dao.Login;


import com.example.rainbowend.Entity.Files;
import com.example.rainbowend.Entity.User;
import org.apache.ibatis.annotations.*;

/**
 * Rainbow
 *
 * @DATE:2023/8/2 0002
 */
@Mapper
public interface RegisterForgetDao {
    /**
     * 判断账号是否存在
     */
    @Select("SELECT t.* FROM users t " +
            "WHERE t.EMAIL=#{user.email}")
    User isExist(@Param("user") User user);

    /**
     * 添加新用户
     *
     * @param user
     */
    @Insert("INSERT INTO users " +
            "(EMAIL,USERNAME,PASSWORD) " +
            "VALUES(#{user.email},#{user.username},#{user.password})")
    int registerNewUser(@Param("user") User user);


    /**
     * 修改密码
     */
    @Update("UPDATE users t " +
            "SET t.PASSWORD=#{user.password} " +
            "WHERE t.EMAIL=#{user.email}")
    int modifyPW(@Param("user") User userInput);

    @Insert("INSERT INTO files " +
            "(FILE_ID,EMAIL,FILE_NAME,FILE_PATH,FILE_SIZE,CREATE_TIME,FOLDER_TYPE,STATUS) " +
            "VALUES(#{files.fileId},#{files.email},#{files.fileName},#{files.filePath}," +
            "#{files.fileSize},#{files.createTime},#{files.folderType},#{files.status})")
    int insertFile(@Param("files")Files files);
}
