package com.example.rainbowend.Dao.LoginRegister;


import com.example.rainbowend.Entity.User;
import com.example.rainbowend.Entity.UserFile;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * Rainbow
 *
 * @DATE:2023/8/2 0002
 */
@Mapper
public interface RegisterDao {
    /**
     * 添加新用户
     * @param user
     * @return
     */
    @Insert("INSERT INTO users " +
            "(USERNAME,PASSWORD,EMAIL) " +
            "VALUES(#{user.username},#{user.password},#{user.email})")
    int insertNewUser(@Param("user") User user);

    /**
     * 判断账号是否存在
     */
    @Select("SELECT t.* FROM users t " +
            "WHERE t.EMAIL=#{user.email}")
    User isExist(@Param("user") User user);


    /**
     * 用户对应目录路径入库
     * @param userfile)
     * @return
     */
    @Insert("INSERT INTO userfiles " +
            "(id,email,root,userimage,dir,file) " +
            "VALUES(#{userfile.id},#{userfile.email},#{userfile.root}," +
            "#{userfile.userimage},#{userfile.dir},#{userfile.file})")
    int addToUserFils(@Param("userfile") UserFile userfile);
}
