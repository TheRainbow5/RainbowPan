package com.example.rainbowend.Service.Login.Impl;

import com.example.rainbowend.Dao.Login.RegisterForgetDao;
import com.example.rainbowend.Entity.Files;
import com.example.rainbowend.Entity.ResponseResult;
import com.example.rainbowend.Entity.User;
import com.example.rainbowend.Service.Login.RegisterForgetService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * Rainbow
 *
 * @DATE:2023/8/2 0002
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class RegisterForgetServiceImpl implements RegisterForgetService {
    private static final Logger logger = LoggerFactory.getLogger(RegisterForgetServiceImpl.class);
    @Resource
    private RegisterForgetDao registerForgetDao;
    @Value("${UserRoot}")
    private String UserRoot;

    /**
     * 修改密码
     *
     * @param user 用户对象，包含新密码和账号信息
     * @return 响应结果
     */
    @Override
    public ResponseResult modifyPW(User user) {
        try {
            //判断账号是否存在
            if (registerForgetDao.isExist(user) != null) {
                if (registerForgetDao.modifyPW(user) > 0) {
                    return ResponseResult.ok("重置密码成功");
                }
            }
            throw new Exception("");
        } catch (Exception e) {
            handleException(e);
            return ResponseResult.error("发生一些错误，请联系管理员");
        }
    }


    /**
     * 注册新用户
     *
     * @param user 用户信息
     * @return 响应结果
     */
    @Override
    public ResponseResult registerNewUser(User user) {
        try {
            // 检查用户是否已经存在
            User existingUser = registerForgetDao.isExist(user);
            if (existingUser != null) {
                return ResponseResult.error("该用户名已经存在");
            }

            // 添加新用户
            int affectedRows = registerForgetDao.registerNewUser(user);
            if (affectedRows > 0) {
                if (createUserFolder(user.getEmail())) {
                    //创建跟目录
                    Files files = new Files();
                    files.setFileId(UUID.randomUUID().toString().replace("-",""));
                    files.setEmail(user.getEmail());
                    files.setFileName(user.getEmail());
                    files.setFilePath(user.getEmail());
                    files.setFileSize("0 B");
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    String createTime = dateFormat.format(new Date());
                    files.setCreateTime(createTime);
                    files.setFolderType(1);
                    files.setStatus("1");
                    if(registerForgetDao.insertFile(files)>0){
                        return ResponseResult.ok("注册成功");
                    }
                    throw new IOException("文件创建失败");
                } else {
                    throw new IOException("本地文件创建失败");
                }
            } else {
                throw new IOException("数据入库失败");
            }
        } catch (Exception e) {
            handleException(e);
            return ResponseResult.error("发生了一些错误，请联系管理员");
        }
    }

    /**
     * 创建用户文件夹
     * @param email 用户邮箱
     * @return 是否成功创建文件夹
     */
    private boolean createUserFolder(String email) {
        File file = new File(UserRoot, email);
        if (!file.exists()) {
            return file.mkdir();
        }
        return false;
    }

    /**
     * 处理异常并回滚事务
     *
     * @param e 异常对象
     */
    private void handleException(Exception e) {
        logger.error("Service层发生错误", e);
        TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
    }


}
