package com.example.rainbowend.Service.Index.Impl;

import com.example.rainbowend.Dao.Index.IndexFileDao;
import com.example.rainbowend.Entity.UserFile;
import com.example.rainbowend.Service.Index.IndexFileService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Rainbow
 *
 * @DATE:2023/8/10 0010
 */
@Service
public class IndexFileServiceImpl implements IndexFileService {
    @Resource
    private IndexFileDao indexFileDao;


    /**
     * 保存文件夹访问路径
     * @param email
     * @return
     */
    @Override
    public int saveDir(UserFile userFile) {
        return indexFileDao.saveDir(userFile);
    }

    /**
     * 判断文件夹路径是否存在
     * @return
     */
    @Override
    public UserFile existDir(UserFile userFile1) {
        return indexFileDao.existDir(userFile1);
    }
}
