package com.example.rainbowend.Controller.LoginRegister;

import com.alibaba.fastjson.JSONObject;
import com.example.rainbowend.Entity.ResponseResult;
import com.example.rainbowend.Entity.User;
import com.example.rainbowend.Service.LoginRegister.RegisterService;
import com.example.rainbowend.Utils.SecurityCodeGenerateUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.File;
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

public class RegisterController {
    private static final Logger logger = LoggerFactory.getLogger(RegisterController.class);
    @Resource
    private JavaMailSender mailSender;  //发送邮箱
    @Value("${spring.mail.username}")  //从yaml文件中获取属性值
    private String fromEmail;
    @Value("${UserRoot}")
    private String UserRoot;
    @Resource
    private RegisterService registerService;

    /**
     * 注册，账号入库
     * 1、解析数据
     * 2、数据封装
     * 3、数据入库
     *
     * @param jsonObject
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @PostMapping("/register")
    @ApiOperation("注册新用户接口")
    public ResponseResult register(@RequestBody JSONObject jsonObject) {
        //解析参数
        String username = jsonObject.getString("username");
        String password = jsonObject.getString("password");
        String email = jsonObject.getString("email");

        //数据封装
        User user = new User(username, password, email, null, email);//判断用户是否存在
        try {
            User userOutput = registerService.isExist(user);
            if (userOutput == null) {
                //新用户数据入库
                int i = registerService.registerNewUser(user);
                if (i > 0) {   //注册成功
                    //创建账号专属文件夹（邮箱号命名的文件夹）
                    File file = new File(UserRoot, email);
                    if (file.mkdir()) {
                        return ResponseResult.ok("注册成功");
                    } else {
                        return ResponseResult.ok("文件创建失败");
                    }
                } else {
                    //注册失败
                    return ResponseResult.error("注册失败");
                }
            } else {
                return ResponseResult.error("该用户名已经存在");
            }
        } catch (Exception e) {
            logger.error("Controller层->register方法");
            logger.error(e.getMessage());
            //手动事务回滚
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return ResponseResult.error("出现了一些错误，请联系管理员");
        }
    }


    /**
     * 发送验证码
     * 1、解析请求参数
     * 2、设置发送方
     * 3、设置接收方
     * 5、通过工具类获取验证码
     * 4、设置邮件信息
     *
     * @param jsonObject
     * @return
     */
    @PostMapping("/securityCode")
    @ApiOperation("发送邮箱接口")
    public ResponseResult sendSecurityCode(@RequestBody JSONObject jsonObject) {
        Map<String, Object> map = new HashMap<>();
        System.out.println("******发送邮箱******\n\n");
        //解析参数
        String toEmail = (String) jsonObject.get("email");  //获取接收方邮箱地址
        //创建邮箱信息
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(fromEmail);  //设置发送方
        message.setTo(toEmail);  //设置接收方
        //设置邮箱信息
        message.setSubject("您本次的验证码是");  //设置邮箱主题
        String securityCode = SecurityCodeGenerateUtil.generateVerCode();  //获取验证码
        //设置邮件内容
        message.setText("尊敬的" + toEmail + ",您好:\n"
                + "\n本次请求的邮件验证码为:" + securityCode + "\n,本验证码 5 分钟内效，请及时输入。（请勿泄露此验证码）\n"
                + "\n如非本人操作，请忽略该邮件。\n(这是一封通过自动发送的邮件，请不要直接回复）");

        try {
            mailSender.send(message);   //发送邮箱
            map.put("securityCode", securityCode);
            return ResponseResult.ok("验证码发送成功", map);  //返回验证码到前端
        } catch (Exception e) {
            logger.error("Controller层->sendSecurityCode方法：发送邮件失败");
            logger.error(e.getMessage());
            return ResponseResult.error("出现了一些错误，请联系管理员");
        }
    }


}
