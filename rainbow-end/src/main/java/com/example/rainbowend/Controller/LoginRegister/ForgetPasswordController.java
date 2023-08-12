package com.example.rainbowend.Controller.LoginRegister;

import com.alibaba.fastjson.JSONObject;
import com.example.rainbowend.Entity.ResponseResult;
import com.example.rainbowend.Entity.User;
import com.example.rainbowend.Service.LoginRegister.ForgetPasswordService;
import com.example.rainbowend.Utils.SecurityCodeGenerateUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * Rainbow
 *
 * @DATE:2023/8/2
 */
@RestController
@RequestMapping("/user")
public class ForgetPasswordController {
    private static final Logger logger = LoggerFactory.getLogger(RegisterController.class);
    @Resource
    private ForgetPasswordService forgetPasswordService;

    /**
     * 修改密码
     * 1、解析数据
     * 2、数据封装
     * 3、判断账号是否存在
     *  a、不存在：抛出异常
     *  b、存在：修改密码
     * @param jsonObject
     * @return
     */
    @PostMapping("/forgetPassword")
    @ApiOperation("修改密码接口")
    public ResponseResult forgetPassword(@ApiParam(value = "传入参数")@RequestBody JSONObject jsonObject) {
        System.out.println("*****修改用户密码******\n\n");
        //解析参数
        String username=jsonObject.getString("username");
        String repassword=jsonObject.getString("repassword");
        String email=jsonObject.getString("email");
        //数据封装
        User userInput = new User(username,repassword,email,null,null);
        try {
            //判断账号是否存在
            User userOutput=forgetPasswordService.isExist(userInput);
            if(userOutput==null){
                return ResponseResult.error("该账号未注册");
            }else{
                int modifyLine=forgetPasswordService.modifyPW(userInput);
                if(modifyLine>0){
                    return ResponseResult.ok("重置密码成功");
                }else{
                    return ResponseResult.error("修改密码失败");
                }
            }
        } catch (Exception e) {
            logger.error("Controller层->forgetPassword方法：修改密码失败");
            logger.error(e.getMessage());
            return ResponseResult.error("修改密码失败");
        }
    }
}
