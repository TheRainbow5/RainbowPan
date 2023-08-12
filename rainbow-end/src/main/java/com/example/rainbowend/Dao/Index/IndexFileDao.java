package com.example.rainbowend.Dao.Index;

import com.example.rainbowend.Entity.UserFile;
import org.apache.ibatis.annotations.*;

/**
 * Rainbow
 * @DATE:2023/8/10 0010
 */
@Mapper
public interface IndexFileDao {
    /**
     * 判断文件夹路径是否存在
     * @return
     */
    @Select("SELECT t.* FROM userdirs t " +
            "WHERE t.dir=#{userFile.dir}")
    UserFile existDir(@Param("userFile") UserFile userFile1);

    /**
     * 保存文件夹访问路径
     * @return
     */
    @Insert("INSERT INTO  userdirs " +
            "(dir,email) " +
            "VALUES(#{userFile.dir},#{userFile.email})")
    int saveDir(@Param("userFile")UserFile userFile);


}
