package com.example.rainbowend.Service.Index.Impl;

import com.example.rainbowend.Dao.Index.IndexUserDao;
import com.example.rainbowend.Entity.ResponseResult;
import com.example.rainbowend.Entity.User;
import com.example.rainbowend.Service.Index.IndexUserService;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

/**
 * Rainbow
 *
 * @DATE:2023/8/7
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class IndexUserServiceImpl implements IndexUserService {
    private static final Logger logger = LoggerFactory.getLogger(IndexUserServiceImpl.class);
    @Resource
    private IndexUserDao indexUserDao;

    @Value("${UserRoot}")
    private String UserRoot;


    /**
     * 获取用户信息
     *
     * @param email 用户邮箱
     * @return 响应结果，包含用户信息
     */
    @Override
    public ResponseResult getUserInfo(String email) {
        Map<String, Object> map = new HashMap<>();
        try {
            // 获取用户信息
            User user = indexUserDao.getUserInfo(email);
            if (user != null) {
                map.put("email", user.getEmail());
                map.put("username", user.getUsername());
                return ResponseResult.ok("验证成功", map);
            } else {
                return ResponseResult.error("用户信息不存在");
            }
        } catch (Exception e) {
            handleException(e);
            return ResponseResult.error("发生一些错误，请联系管理员");
        }
    }


    /**
     * 保存用户头像图片的URL并上传图片文件
     *
     * @param email         用户邮箱
     * @param multipartFile 图片文件
     * @return 响应结果
     */
    @Override
    public ResponseResult saveImgUrl(String email, MultipartFile multipartFile) {
        try {
            String fileName = email + ".jpg";   //文件名称
            String localImagePath = UserRoot + email + "/userImages/";
            String imgUrl = email + "/userImages/" + fileName;  //数据库存储路径

            // 1. 更新数据库中的图片路径
            int i = indexUserDao.saveImgUrl(email, imgUrl);
            if (i <= 0) {
                return ResponseResult.error("文件路径更新失败，请重新上传");
            }

            // 2. 创建或确认用户头像文件夹
            Path imageFolderPath = Paths.get(UserRoot, email, "userImages");
            if (!Files.exists(imageFolderPath)) {
                Files.createDirectories(imageFolderPath);  //创建文件夹
            }

            // 3. 删除旧的图片文件（如果存在）
            Path filePath = imageFolderPath.resolve(fileName);
            if (Files.exists(filePath)) {
                Files.delete(filePath);
            }

            // 4. 保存新的图片文件到本地
            multipartFile.transferTo(filePath.toFile());

            // 5. 返回成功响应
            return ResponseResult.ok("文件上传成功，请重新上传");
        } catch (Exception e) {
            handleException(e);
            return ResponseResult.error("文件上传失败，请重新登录");
        }
    }


    /**
     * 获取用户的图片访问地址
     *
     * @param email 用户的邮箱
     * @return 包含图片访问地址的响应结果
     */
    @Override
    public ResponseResult getUserImageUrl(String email) {
        Map<String, Object> map = new HashMap<>();
        try {
            // 从数据库中查询用户信息，包括图片URL
            User user = indexUserDao.getUserImageUrl(email);
            if (user.getImgUrl() != null) {
                map.put("imageUrl", user.getImgUrl());
                // 返回成功响应，附带图片访问地址
                return ResponseResult.ok("获取图片访问地址成功", map);
            }
            // 如果用户没有图片URL，返回错误响应
            return ResponseResult.error("获取图片访问地址失败");
        } catch (Exception e) {
            // 处理异常并返回错误响应
            handleException(e);
            return ResponseResult.error("发生一些错误，请联系管理员");
        }
    }


    /**
     * 注销用户
     *
     * @param email 用户邮箱
     * @return 返回响应结果
     */
    @Override
    public ResponseResult logoffUser(String email) {
        try {
            //删除用户表数据
            int userRow = indexUserDao.deleteUser(email);
            //删除文件表数据
            int fileRow = indexUserDao.deleteFile(email);
            //删除本地数据
            String localPath = UserRoot + email;
            File file = new File(localPath);
            if (file.exists()) {
                if (file.isDirectory()) {
                    FileUtils.deleteDirectory(file); // 递归删除目录及其内容
                } else {
                    file.delete(); // 删除单个文件
                }
            }
            if (userRow > 0 && fileRow > 0) {
                return ResponseResult.ok("用户注销成功");
            } else {
                return ResponseResult.error("注销用户失败，请联系管理员");
            }
        } catch (Exception e) {
            // 处理异常并返回错误响应
            handleException(e);
            return ResponseResult.error("发生一些错误，请联系管理员");
        }
    }

    /**
     * 处理异常并记录日志
     *
     * @param e 异常对象
     */
    private void handleException(Exception e) {
        logger.error(String.valueOf(e));
        TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();  // 事务回滚
    }

}
