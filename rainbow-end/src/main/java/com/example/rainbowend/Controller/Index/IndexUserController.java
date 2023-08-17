package com.example.rainbowend.Controller.Index;

import com.alibaba.fastjson.JSONObject;
import com.example.rainbowend.Entity.ResponseResult;
import com.example.rainbowend.Service.Index.IndexUserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Rainbow
 *
 * @DATE:2023/8/7
 */

@RestController
@RequestMapping("index")
public class IndexUserController {
    @Resource
    private IndexUserService indexUserService;

    /**
     * 获取用户信息
     * @param jsonObject 包含用户信息的JSON对象
     * @return 响应结果，包含用户信息
     */
    @PostMapping("getUserInfo")
    public ResponseResult getUserInfo(@RequestBody JSONObject jsonObject) {
        String email = (String) jsonObject.getJSONObject("user").get("email");
        return indexUserService.getUserInfo(email);
    }

    /**
     * 上传用户头像图片并更新URL
     *
     * @param request HTTP请求对象
     * @return 响应结果
     */
    @PostMapping("setImage")
    public ResponseResult changePicture(HttpServletRequest request) {
        //1、解析参数
        MultipartHttpServletRequest mulRequest = (MultipartHttpServletRequest) request;
        String email = mulRequest.getParameter("email");   //邮箱

        //头像
        List<MultipartFile> filesList = (mulRequest.getFiles("file"));
        MultipartFile multipartFile = filesList.get(0);

        return indexUserService.saveImgUrl(email, multipartFile);
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
        //解析参数
        String email = jsonObject.getString("email");

        return indexUserService.getUserImageUrl(email);
    }


    /**
     * 根据用户信息，注销账号
     * @param jsonObject 携带的用户信息
     * @return 响应结果
     */
    @PostMapping("logoffUser")
    public ResponseResult logoffUser(@RequestBody JSONObject jsonObject) {
        //解析参数
        String email = jsonObject.getString("email");
        System.out.println(email);
        return indexUserService.logoffUser(email);
    }
}
