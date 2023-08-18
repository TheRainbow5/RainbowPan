package com.example.rainbowend.Controller.Login;

import com.alibaba.fastjson.JSONObject;
import com.example.rainbowend.Entity.ResponseResult;
import com.example.rainbowend.Entity.User;
import com.example.rainbowend.Service.Login.RegisterForgetService;
import com.example.rainbowend.Utils.SecurityCodeGenerateUtil;
import io.swagger.annotations.Api;
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
import java.util.HashMap;
import java.util.Map;

/**
 * Rainbow
 * 注册
 *
 * @DATE:2023/8/2 0002
 */
@RestController
@RequestMapping("/user")
@Api(value = "注册界面管理")

public class RegisterForgetController {
    private static final Logger logger = LoggerFactory.getLogger(RegisterForgetController.class);
    @Resource
    private JavaMailSender mailSender;  //发送邮箱
    @Value("${spring.mail.username}")  //从yaml文件中获取属性值
    private String fromEmail;
    @Resource
    private RegisterForgetService registerForgetService;

    /**
     * 注册新用户
     */
    @PostMapping("/register")
    public ResponseResult register(@RequestBody JSONObject jsonObject) {
        //解析参数
        String username = jsonObject.getString("username");
        String password = jsonObject.getString("password");
        String email = jsonObject.getString("email");
        User user = new User(email, username, password, null);
        return registerForgetService.registerNewUser(user);
    }


    /**
     * 修改密码
     */
    @PostMapping("/forgetPassword")
    public ResponseResult forgetPassword(@RequestBody JSONObject jsonObject) {
        //解析参数
        String username = jsonObject.getString("username");
        String repassword = jsonObject.getString("repassword");
        String email = jsonObject.getString("email");
        //数据封装
        User user = new User(username, repassword, email, null);
        return registerForgetService.modifyPW(user);
    }


    /**
     * 发送验证码
     * @param jsonObject
     * @return
     */
    @PostMapping("/securityCode")
    public ResponseResult sendSecurityCode(@RequestBody JSONObject jsonObject) {
        Map<String, Object> map = new HashMap<>();
        try {
            String toEmail = jsonObject.getString("email");  // 获取接收方邮箱地址

            // 创建邮箱信息
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(fromEmail);  // 设置发送方
            message.setTo(toEmail);  // 设置接收方
            message.setSubject("您本次的验证码是");  // 设置邮箱主题

            // 生成验证码
            String securityCode = SecurityCodeGenerateUtil.generateVerCode();

            // 设置邮件内容
            String emailContent = "尊敬的 " + toEmail + "，您好：\n"
                    + "\n本次请求的邮件验证码为：" + securityCode + "，该验证码5分钟内有效，请及时输入。（请勿泄露此验证码）\n"
                    + "\n如非本人操作，请忽略该邮件。\n（这是一封通过自动发送的邮件，请不要直接回复）";
            message.setText(emailContent);

            // 发送邮件
            mailSender.send(message);

            map.put("securityCode", securityCode);
            return ResponseResult.ok("验证码发送成功", map);  // 返回验证码到前端
        } catch (Exception e) {
            logger.error("Controller层->sendSecurityCode方法：发送邮件失败", e);
            return ResponseResult.error("出现了一些错误，请联系管理员");
        }
    }

}
