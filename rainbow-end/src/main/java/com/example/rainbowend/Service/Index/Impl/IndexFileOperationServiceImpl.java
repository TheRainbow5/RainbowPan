package com.example.rainbowend.Service.Index.Impl;

import com.example.rainbowend.Controller.Index.IndexFilesOperationController;
import com.example.rainbowend.Dao.Index.IndexFileOperationDao;
import com.example.rainbowend.Entity.Files;
import com.example.rainbowend.Entity.ResponseResult;
import com.example.rainbowend.Service.Index.IndexFileOperationService;
import com.example.rainbowend.Utils.FileOperationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Rainbow
 *
 * @DATE:2023/8/14 0014
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class IndexFileOperationServiceImpl implements IndexFileOperationService {
    private static final Logger logger = LoggerFactory.getLogger(IndexFilesOperationController.class);
    @Value("${UserRoot}")
    private String UserRoot;
    @Resource
    private IndexFileOperationDao indexFileOperationDao;

    /**
     * 删除文件
     * 1、获取filePid、fileName
     * 2、判断文件是否存在
     * 不存在：抛出异常
     * 3、存在
     * 删除数据
     * 4、判断本地文件是否存在
     * 不存在：事务回滚，抛出异常
     * 5、存在：删除文件
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public ResponseResult deleteFileAndFolder(String fileId, String filePath) {
        try {
            //1、判断文件是否存在
            Files files = indexFileOperationDao.exist(fileId);
            if (files != null) {   //文件存在
                //2、删除数据库中数据（文件夹和子文件）
                int num = indexFileOperationDao.delete(filePath);
                if (num > 0) {
                    //3、删除本地文件
                    if (deleteLocalFile(UserRoot + filePath)) {
                        return ResponseResult.ok("删除成功");
                    } else {
                        throw new IOException("本地文件删除失败");
                    }
                }
            }
            return ResponseResult.error("发生一些错误，请联系管理员");
        } catch (Exception e) {
            handleExceptionAndRollback(e);
            return ResponseResult.error("发生一些错误，请联系管理员");
        }
    }

    /**
     * 删除本地文件
     *
     * @param filePath
     * @return 是否删除成功
     */
    private boolean deleteLocalFile(String filePath) throws IOException {
        File file = new File(filePath);
        if (file.exists()) {
            FileOperationUtil.deleteFolder(file);
            return true;
        }
        return false;
    }


    /**
     * 文件重命名
     *
     * @param newFileName 新的文件名（不包含后缀）
     * @param files       文件信息
     * @return 响应结果
     */
    @Override
    public ResponseResult resetFileName(String newFileName, Files files) {
        try {
            //判读文件是否存在
            Files existingFile = indexFileOperationDao.exist(files.getFileId());
            if (existingFile == null) {
                return ResponseResult.error("文件不存在");
            }
            //判断新文件名称是否存在
            Files existNewFile = indexFileOperationDao.existFile(files.getFilePid(), newFileName);
            if (existNewFile != null) {
                return ResponseResult.error("新文件不存在");
            }
            //数据格式化
            String suffix = files.getFileName().substring(files.getFileName().lastIndexOf(".")); //获取文件类型后缀
            newFileName = newFileName + suffix;   //新文件名称
            String newFilePath = files.getFilePid() + "/" + newFileName;
            //修改文件名
            int updateResult = indexFileOperationDao.resetFileName(files.getFileId(), newFileName, newFilePath);
            if (updateResult <= 0) {
                return ResponseResult.error("文件重命名失败");
            }
            //修改本地文件名
            String filePid = files.getFilePid();  //当前文件夹
            String oldFilePath = files.getFilePath();   //获取全路径
            File oldFile = new File(UserRoot, oldFilePath);
            File newFile = new File(UserRoot + filePid, newFileName);
            // 执行文件重命名
            if (oldFile.exists() && !newFile.exists()) {
                if (oldFile.renameTo(newFile)) {
                    return ResponseResult.ok("文件重命名成功");
                }
            }
            throw new Exception("文件重命名失败");
        } catch (FileNotFoundException e) {
            return ResponseResult.error("文件不存在");
        } catch (IOException e) {
            handleExceptionAndRollback(e);
            return ResponseResult.error("文件操作失败，请联系管理员");
        } catch (Exception e) {
            handleExceptionAndRollback(e);
            return ResponseResult.error("发生一些错误，请联系管理员");
        }
    }

    /**
     * 文件夹重命名
     *
     * @param newFileName
     * @param files
     * @return
     */
    @Override
    public ResponseResult resetFolderName(String newFileName, Files files) {
        try {
            //判读文件夹是否存在
            Files existingFile = indexFileOperationDao.exist(files.getFileId());
            if (existingFile == null) {
                return ResponseResult.error("文件不存在");
            }
            //判断新文件夹名称是否存在
            Files existNewFile = indexFileOperationDao.existFile(files.getFilePid(), newFileName);
            if (existNewFile != null) {
                return ResponseResult.error("新文件不存在");
            }
            //数据格式化
            String suffix = files.getFileName().substring(files.getFileName().lastIndexOf(".")); //获取文件类型后缀
            newFileName = newFileName + suffix;   //新文件名称
            String newFilePath = files.getFilePid() + "/" + newFileName;
            //修改文件名
            int updateResult = indexFileOperationDao.resetFileName(files.getFileId(), newFileName, newFilePath);
            if (updateResult <= 0) {
                return ResponseResult.error("文件重命名失败");
            }
            //修改本地文件名
            String filePid = files.getFilePid();  //当前文件夹
            String oldFilePath = files.getFilePath();   //获取全路径
            File oldFile = new File(UserRoot, oldFilePath);
            File newFile = new File(UserRoot + filePid, newFileName);
            // 执行文件重命名
            if (oldFile.exists() && !newFile.exists()) {
                if (oldFile.renameTo(newFile)) {
                    return ResponseResult.ok("文件重命名成功");
                }
            }
            return ResponseResult.error("文件重命名失败");
        } catch (Exception e) {
            handleExceptionAndRollback(e);
            return ResponseResult.error("发生一些错误，请联系管理员");
        }
    }

    /**
     * 处理异常并执行事务回滚
     *
     * @param e 异常
     */
    private void handleExceptionAndRollback(Exception e) {
        logger.error(e.getMessage());
        TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
    }
}
