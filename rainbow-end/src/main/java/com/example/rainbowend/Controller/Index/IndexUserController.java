package com.example.rainbowend.Controller.Index;

import com.alibaba.fastjson.JSONObject;
import com.example.rainbowend.Entity.ResponseResult;
import com.example.rainbowend.Entity.User;
import com.example.rainbowend.Service.Index.IndexService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Rainbow
 *
 * @DATE:2023/8/7
 */

@RestController
@RequestMapping("index")
public class IndexUserController {
    private static final Logger logger = LoggerFactory.getLogger(IndexUserController.class);
    @Resource
    private IndexService indexService;
    @Value("${UserRoot}")
    private String UserRoot;

    @PostMapping("getUserInfo")
    public ResponseResult getUserInfo(@RequestBody JSONObject jsonObject) {
        Map<String, Object> map = new HashMap<>();
        try {
            //获取请求参数
            String email = (String) jsonObject.getJSONObject("user").get("email");
            //获取用户信息
            User user = indexService.getUserInfo(email);
            map.put("email", user.getEmail());
            map.put("username", user.getUsername());
            return ResponseResult.ok("验证成功", map);
        } catch (Exception e) {
            logger.error("Controller层->getUserInfo方法");
            logger.error(e.getMessage());
            return ResponseResult.error("验证失败，请重新登录");
        }
    }

    /**
     * 1、获取图片文件
     * 2、将文件请求链接存储到数据库
     * 3、返回文件的访问链接
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @PostMapping("setImage")
    public ResponseResult changePicture(HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        try {
            //1、获取参数
            MultipartHttpServletRequest mulRequest = (MultipartHttpServletRequest) request;
            String email = mulRequest.getParameter("email");   //邮箱
            String fileName = email + ".jpg";  //文件名称
            //头像
            List<MultipartFile> files = (mulRequest.getFiles("file"));
            MultipartFile multipartFile = files.get(0);
            //存储头像路径
            String imageDir = UserRoot+email+"/userImages/";

            //2、保存头像访问路径到数据库
            String imgUrl=email+"/userImages/"+fileName;
            int i=indexService.saveImgUrl(email,imgUrl);
            if(i>0){   //更新成功
                //3、本地创建文件夹
                File imageFile = new File(UserRoot+email,"userImages");
                //判断文件夹是否存在
                if(imageFile.exists()){
                    File file = new File(imageDir + fileName);
                    if(file.exists()){
                        file.delete();
                        multipartFile.transferTo(file);
                    }else{
                        multipartFile.transferTo(file);
                    }
                    return ResponseResult.ok("文件上传成功，请重新上传");
                } else{
                    imageFile.mkdir();
                    File file = new File(imageDir + fileName);
                    if(file.exists()){
                        file.delete();
                        multipartFile.transferTo(file);
                    }else{
                        multipartFile.transferTo(file);
                    }
                    return ResponseResult.ok("文件上传成功，请重新上传");
                }
            }else{
                return ResponseResult.error("文件路径更新失败，请重新上传");
            }
        } catch (Exception e) {
            logger.error("Controller层->changePicture方法");
            logger.error(e.getMessage());
            //事务回滚
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return ResponseResult.error("文件上传失败，请重新登录");
        }
    }


    /**
     * 根据用户信息，返回头像
     * 1、获取邮箱
     * 2、根据邮箱查询头像访问路径
     * @param jsonObject
     * @return
     */
    @PostMapping("userImageUrl")
    public ResponseResult UserImageUrl(@RequestBody JSONObject jsonObject) {
        Map<String, Object> map = new HashMap<>();
        try {
            //获取参数
            String email=jsonObject.getString("email");
            //获取图片的url
            User user=indexService.getUserImageUrl(email);
            if(user.getImgUrl()!=null){
                map.put("imageUrl",user.getImgUrl());
                return ResponseResult.ok("获取图片访问地址成功",map);
            }else{
                return ResponseResult.error("获取图片访问地址失败");
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            return ResponseResult.error("获取图片失败");
        }
    }
}
