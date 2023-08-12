package com.example.rainbowend.Dao.Index;

import com.example.rainbowend.Entity.User;
import com.example.rainbowend.Entity.UserFile;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * Rainbow
 *
 * @DATE:2023/8/7
 */
@Mapper
public interface IndexDao {

    /**
     * 获取用户名
     */
    @Select("SELECT t.* FROM users t " +
            "WHERE t.email=#{email}")
    User getUserInfo(String email);

    /**
     * 更新图片访问路径
     * @param imgUrl
     * @return
     */
    @Update("UPDATE users t " +
            "SET t.imgurl=#{imgUrl} " +
            "WHERE t.email=#{email}")
    int saveImgUrl(String email,String imgUrl);


    /**
     * 获取图片访问路径
     * @param email
     * @return
     */
    @Select("SELECT t.* FROM users t " +
            "WHERE t.email=#{email}")
    User getUserImageUrl(String email);
}
