package com.example.rainbowend.Controller.LoginRegister;

import com.alibaba.fastjson.JSONObject;
import com.auth0.jwt.interfaces.Claim;
import com.example.rainbowend.Entity.ResponseResult;
import com.example.rainbowend.Entity.User;
import com.example.rainbowend.Entity.UserJwt;
import com.example.rainbowend.Service.LoginRegister.LoginService;
import com.example.rainbowend.Utils.JwtUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * Rainbow
 * 实现登录注册功能
 * @DATE:2023/7/31w
 */
@RestController
@RequestMapping("/user")
@Api(value = "登录界面管理")
public class LoginController {
    //日志信息
    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
    @Resource
    private LoginService loginService;
    /**
     * 登录验证
     * 1、解析数据
     * 2、验证账号是否存在（后续通过加密验证）
     * 3、封装数据
     * 4、生成token
     * @param jsonObject
     * @return
     */
    @PostMapping("/login")
    @ApiOperation("登录请求接口")
    public ResponseResult Login(@ApiParam(value = "用户传入的参数")@RequestBody JSONObject jsonObject) {
        System.out.println("******登录请求******");
        Map<String, Object> map = new HashMap<>();
        System.out.println(jsonObject);
        System.out.println("\n\n");
        try {
            //解析数据
            String email = (String) jsonObject.get("email");
            String password = (String) jsonObject.get("password");
            //查询数据库，判断是否存在该账号
            User user=loginService.getUser(email);
            if(user==null){
                return ResponseResult.error("账号未注册");
            }else{
                //密码校验
                if(password.equals(user.getPassword())){  //密码正确
                    //封装数据
                    UserJwt userJwt = new UserJwt(email,password);
                    //生成token
                    String token = JwtUtil.createToken(userJwt);
                    map.put("token", token);
                    return ResponseResult.ok("登录成功",map);
                }else{   //密码错误
                    return ResponseResult.error("密码错误，请重新登录");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseResult.error("发生一些错误，请联系管理员");
        }
    }

    /**
     * 验证token
     * 1、获取请求头中的token
     * 2、验证token是否生效
     * a、判断token是否过期
     * b、判断token是否正确
     * 3、返回验证结果
     * @param request
     * @return
     */
    @GetMapping("/verifyToken")
    @ApiOperation("校验token接口")
    public ResponseResult verifyToken(HttpServletRequest request) {
        System.out.println("******验证token******\n\n");
        try {
            //获取请求参数
            String token = request.getHeader("token");
            //验证token
            Map<String, Claim> verifyMap = JwtUtil.verifyToken(token);
            if (verifyMap.isEmpty()) {
                return ResponseResult.error("验证失败");
            } else {
                return ResponseResult.ok("验证成功");
            }
        } catch (Exception e) {
            logger.error("Controller层->verifyToken方法：token验证失败");
            logger.error(e.getMessage());
            return ResponseResult.error("验证失败，请重新登录");
        }
    }

}
