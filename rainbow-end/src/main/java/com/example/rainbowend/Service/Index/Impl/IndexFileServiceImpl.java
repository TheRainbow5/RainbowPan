package com.example.rainbowend.Service.Index.Impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.rainbowend.Dao.Index.IndexFileDao;
import com.example.rainbowend.Entity.Files;
import com.example.rainbowend.Entity.ResponseResult;
import com.example.rainbowend.Service.Index.IndexFileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Rainbow
 *
 * @DATE:2023/8/10 0010
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class IndexFileServiceImpl implements IndexFileService {
    private static final Logger logger = LoggerFactory.getLogger(IndexFileServiceImpl.class);
    @Value("${UserRoot}")
    private String UserRoot;
    @Resource
    private IndexFileDao indexFileDao;


    /**
     * 创建新的文件夹。
     * 如果文件夹已存在，则返回错误信息；
     * 如果文件夹不存在，则在本地和数据库中创建文件夹并返回成功信息。
     *
     * @param files 包含文件信息的对象
     * @return 响应结果，包含文件夹路径和相应信息
     */
    @Override
    public ResponseResult createNewDir(Files files) {
        Map<String, Object> map = new HashMap<>();
        map.put("filePath", files.getFilePath());
        map.put("filePid", files.getFilePid());

        // 构建本地存储路径
        File file = new File(UserRoot + files.getFilePid(), files.getFileName());

        try {
            // 判断文件夹是否在数据库中已存在
            if (indexFileDao.existDir(files) != null) {
                // 文件夹已存在，返回错误信息
                if (file.mkdir() && file.exists()) {
                    return ResponseResult.error("文件夹已经存在", map);
                } else {
                    return ResponseResult.error("无法创建文件夹或文件夹已存在");
                }
            } else {
                // 在数据库中保存文件夹信息
                int i = indexFileDao.saveDir(files);
                if (i > 0) {
                    // 创建本地文件夹
                    if (file.mkdir() && file.exists()) {
                        return ResponseResult.ok("文件夹创建成功", map);  // 返回存储路径
                    }
                }
                // 创建失败，抛出异常
                throw new RuntimeException("无法创建文件夹");
            }
        } catch (Exception e) {
            // 处理异常并回滚事务
            handleException(e);
            return ResponseResult.error("发生一些错误，请联系管理员");
        }
    }

    /**
     * 获取指定目录下的所有文件列表，支持分页查询。
     *
     * @param page         分页信息
     * @param email        用户的电子邮件
     * @param absolutePath 绝对路径（可为空）
     * @return 响应结果，包含文件列表和总数
     */
    public ResponseResult getAllFiles(Page page, String email, String absolutePath) {
        Map<String, Object> map = new HashMap<>();
        String filePid; // 当前父路径
        try {
            // 根据是否提供绝对路径，设置当前目录
            filePid = (absolutePath.isEmpty()) ? email : email + absolutePath;

            // 查询当前目录下所有子文件的数量
            List<Files> filesNum = indexFileDao.getAllFilesNum(filePid);
            int fileNum = filesNum.size();

            // 分页查询当前目录下的文件列表
            List<Files> filesList = indexFileDao.getAllFiles(page, filePid);

            map.put("fileList", filesList);
            map.put("totalNum", fileNum);

            // 根据文件数量判断是否获取成功
            if (fileNum == 0) {
                return ResponseResult.error("目录为空", map);
            } else {
                return ResponseResult.ok("获取成功", map);
            }
        } catch (Exception e) {
            // 处理异常并回滚事务
            handleException(e);
            return ResponseResult.error("发生一些错误，请联系管理员");
        }
    }

    /**
     * 上传文件
     *
     * @param files         要上传的文件信息
     * @param multipartFile 实际上传的文件
     * @return 包含操作结果和相关信息的 ResponseResult 对象
     */
    @Override
    public ResponseResult uploadNewFile(Files files, MultipartFile multipartFile) {
        Map<String, Object> resultMap = new HashMap<>();
        String filePid = files.getFilePid();
        String fileName = files.getFileName();

        try {
            // 构建本地文件对象路径
            File localFile = new File(UserRoot + filePid, fileName);

            // 判断文件是否已存在于数据库
            Files existingFile = indexFileDao.existFile(files);

            if (existingFile != null && localFile.exists()) {
                // 如果数据库中存在该文件，且本地已存在同名文件，返回错误
                return ResponseResult.error("文件夹已经存在", resultMap);
            }

            if (existingFile == null) {
                // 在数据库中保存文件信息
                int insertionResult = indexFileDao.saveFile(files);
                if (insertionResult > 0) {
                    if (!localFile.exists()) {
                        localFile.mkdirs();  // 创建文件夹
                    }
                    // 将上传的文件保存到本地
                    multipartFile.transferTo(localFile);
                    return ResponseResult.ok("文件夹创建成功", resultMap);
                }
            }
            throw new Exception();  // 如果前面的逻辑不满足，抛出通用异常
        } catch (IOException e) {
            handleException(e);
            return ResponseResult.error("文件上传时发生IO错误");
        } catch (RuntimeException e) {
            handleException(e);
            return ResponseResult.error("发生本地文件不存在错误");
        } catch (Exception e) {
            handleException(e);
            return ResponseResult.error("发生一些错误，请联系管理员");
        }
    }


    /**
     * 处理异常并执行事务回滚
     *
     * @param e 异常
     */
    private void handleException(Exception e) {
        logger.error(e.getMessage());
        TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
    }
}
