package com.example.rainbowend.Dao.LoginRegister;

import com.example.rainbowend.Entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * Rainbow
 *
 * @DATE:2023/8/2
 */
@Mapper
public interface ForgetPasswordDao {
    /**
     * 判断账号是否存在
     */
    @Select("SELECT t.* FROM users t " +
            "WHERE t.EMAIL=#{user.email}")
    User isExist(@Param("user") User userInput);
    /**
     * 修改密码
     */
    @Update("UPDATE users t " +
            "SET t.PASSWORD=#{user.password} " +
            "WHERE t.EMAIL=#{user.email}")
    int modifyPW(@Param("user") User userInput);

}
