package com.example.rainbowend.Utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.rainbowend.Entity.UserJwt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Rainbow
 *
 * @DATE:2023/7/31 0031
 */
public class JwtUtil {
    private static final Logger logger = LoggerFactory.getLogger(JwtUtil.class);
    /**
     * 密钥
     */
    private static final String SECRET = "Rainbow";
    /**
     * 过期时间
     **/
    private static final long EXPIRATION = 3600*24*7;//秒为单位

    /**
     * 生成用户token,设置token超时时间
     */
    public static String createToken(UserJwt userJwt) {
        //过期时间
        Date expireDate = new Date(System.currentTimeMillis() + EXPIRATION * 1000);

        Map<String, Object> map = new HashMap<>();
        map.put("alg", "HS256");
        map.put("typ", "JWT");
        String token = JWT.create()
                .withHeader(map)// 添加头部
                //可以将基本信息放到claims中
                .withClaim("id", String.valueOf(UUID.randomUUID()))//userId
                .withClaim("email", userJwt.getData())//userName
                .withClaim("password", userJwt.getPassword())//password
                .withExpiresAt(expireDate) //超时设置,设置过期的日期
                .withIssuedAt(new Date()) //签发时间
                .sign(Algorithm.HMAC256(SECRET)); //SECRET加密

        return token;
    }

    /**
     * 校验token并解析token
     */
    public static Map<String, Claim> verifyToken(String token) {
        try {
            //解析
            JWTVerifier verifier = JWT.require(Algorithm.HMAC256(SECRET)).build();
            //验证token
            DecodedJWT jwt = verifier.verify(token);
            //decodedJWT.getClaim("属性").asString()  获取负载中的属性值
            return jwt.getClaims();
        } catch (Exception e) {
            logger.error("JwtUtil工具类—>verifyToken方法，token验证失败，原因如下：");
            logger.error(e.getMessage());
            //解码异常则抛出异常
            return null;
        }
    }
}
