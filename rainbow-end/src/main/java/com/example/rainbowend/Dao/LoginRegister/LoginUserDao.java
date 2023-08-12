package com.example.rainbowend.Dao.LoginRegister;

import com.example.rainbowend.Entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Service;

/**
 * Rainbow
 *
 * @DATE:2023/8/2 0002
 */
@Mapper
public interface LoginUserDao {

    /**
     * 根据邮箱，查询用户是否存在
     */
    @Select("SELECT t.* FROM users t " +
            "WHERE t.EMAIL=#{email}")
    User getUser(String email);
}
