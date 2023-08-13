package com.example.rainbowend.Service.Index.Impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.rainbowend.Dao.Index.IndexFileDao;
import com.example.rainbowend.Entity.Files;
import com.example.rainbowend.Service.Index.IndexFileService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

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
     * 判断文件夹路径是否存在
     * @return
     */
    @Override
    public Files existDir(Files files) {
        return indexFileDao.existDir(files);
    }

    /**
     * 保存文件夹访问路径
     * @return
     */
    @Override
    public int saveDir(Files files) {
        return indexFileDao.saveDir(files);
    }

    /**
     * 获取目录下所有子文件
     * @param filePid
     * @return
     */
    @Override
    public List<Files> getAllFiles(Page page,String filePid) {
        return indexFileDao.getAllFiles(page,filePid);
    }

    /**
     * 查询一共多少条数据
     * @param filePid
     * @return
     */
    @Override
    public List<Files> getAllFilesNum(String filePid) {
        return indexFileDao.getAllFilesNum(filePid);
    }

    /**
     * 判断同类型文件是否重名
     * @param files
     * @return
     */
    @Override
    public Files existFile(Files files) {
        return indexFileDao.existFile(files);
    }

    /**
     * 文件入库
     * @param files
     * @return
     */
    @Override
    public int saveFile(Files files) {
        return indexFileDao.saveFile(files);
    }
}
