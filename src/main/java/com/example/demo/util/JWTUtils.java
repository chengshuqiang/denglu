package com.example.demo.util;

import io.jsonwebtoken.*;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;

/**
 * ConfigurationProperties注解：在 Spring Boot 项目中大量的参数配置，在 application.properties
 * 或 application.yml 文件中，通过 @ConfigurationProperties 注解，我们可以方便的获取
 * 这些参数值，application.yml 文件本身支持list类型所以在application.yml 文件中可以这样配置：
 *        jwt:
 *          config:
 *            key: 自定义私钥key值
 *            timeOut: 有效时间(毫秒单位)
 */
@Component
@ConfigurationProperties("jwt.config")
public class JWTUtils {
    //签名私钥
//    @Value("jwt.config.key")
    private String key;

    //签名有效时间
//    @Value("jwt.config.timeout")
    private Long timeout;

    public String getKey() {
        return key;
    }
    public void setKey(String key) {
        this.key = key;
    }
    public long getTimeout() {
        return timeout;
    }
    public void setTimeout(long timeOut) {
        this.timeout = timeOut;
    }

    /**
     * 设置认证token
     *      id:用户id
     *      subject:用户名
     */
    public String createJwt(String id, String subject, Map<String,Object> claims) {
        //1.设置失效时间
        long expirationTime = System.currentTimeMillis() + timeout;
        //2.创建jwtBuilder
        JwtBuilder jwtBuilder = Jwts.builder();
        //3.根据map设置claims
        //当设置的是整个map时，就需放在最前面，下面的setId等参数才会有效，否则会把前面set的值置空
        jwtBuilder.setClaims(claims); //如果有私有声明，一定要先设置这个自己创建的私有的声明，这个是给builder的claim赋值，一旦写在标准的声明赋值之后，就是覆盖了那些标准的声明的
        jwtBuilder.setId(id); //设置jti(JWT ID)：是JWT的唯一标识，根据业务需要，这个可以设置为一个不重复的值，主要用来作为一次性token,从而回避重放攻击。
        jwtBuilder.setSubject(subject); //sub(Subject)：代表这个JWT的主体，即它的所有人，这个是一个json格式的字符串，可以存放什么userid，roldid之类的，作为什么用户的唯一标志。
        jwtBuilder.signWith(SignatureAlgorithm.HS256, key); //设置签名使用的签名算法和签名使用的秘钥
        jwtBuilder.setIssuedAt(new Date());//iat: jwt的签发时间
        jwtBuilder.setExpiration(new Date(expirationTime));//设置过期时间
        //4.创建token
        return jwtBuilder.compact();
    }

    /**
     * 解析token字符串获取clamis
     */
    public Claims parseJwt(String token)
            throws ExpiredJwtException, UnsupportedJwtException, MalformedJwtException, SignatureException, IllegalArgumentException{
        Claims claims = null;

            claims = Jwts.parser().setSigningKey(key).parseClaimsJws(token).getBody();
         /*catch (ExpiredJwtException e) {
            System.err.println("token已过期");
        }  catch (UnsupportedJwtException e) {
            System.out.println("Token格式错误");
        } catch (MalformedJwtException e) {
            System.out.println("Token没有被正确构造");
        } catch (SignatureException e) {
            System.out.println("签名失败");
        } catch (IllegalArgumentException e) {
            System.out.println("非法参数异常");
        } catch (Exception e) {
            e.printStackTrace();
        }*/
        return claims;
    }

}