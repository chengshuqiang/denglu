package com.example.demo.config;

import com.example.demo.util.JWTUtils;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class LoginInterceptor implements HandlerInterceptor {
    private static Logger log = LoggerFactory.getLogger(HandlerInterceptor.class);

    private final JWTUtils jwtUtils;

    public LoginInterceptor(JWTUtils jwtUtils) {
        this.jwtUtils = jwtUtils;
    }

    /**
     * 进入controller层之前拦截请求
     * @param request 请求
     * @param response 响应
     * @param o 对象
     * @return 是否拦截
     * @throws Exception exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {
        //log.info("---------------------开始进入请求地址拦截----------------------------");
        /*if("OPTIONS".equals(request.getMethod().toUpperCase())) {
            log.debug("放行OPTIONS请求");
            return true;
        }*/
        /*boolean flag = false;
        String url = httpServletRequest.getRequestURL().toString();

        for (String s : IGNORE_URI) {
            if (url.contains(s)) {
                flag = true;
                break;
            }
        }
        if (!flag) {
            String token = httpServletRequest.getHeader("token");
            if(!"".equals(token)) {
                try {
                    JwtUtils.getUID(token);
                }catch(Exception e) {
                    log.error("token失效，当前url：" + url);
                    e.printStackTrace();
                    httpServletResponse.setHeader("tokenstatus", "timeout");//在响应头设置token状态
                    httpServletResponse.setCharacterEncoding("utf-8");
                    httpServletResponse.setContentType("text/html;charset=utf-8");
                    httpServletResponse.getWriter().print("this token is time out");
                    return false;
                }
            }
        }
        return true;*/

        /*String url = request.getRequestURL().toString();
        log.info(url);*/
        String token = request.getHeader("Token");
        //401
        if (token == null || token.equals("")) {
            log.error("401 Unauthorized token == null");
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.sendError(HttpStatus.UNAUTHORIZED.value(), "401 Unauthorized token == null");
            return false;
        }

        Claims claims;
        try {
            claims = jwtUtils.parseJwt(token);
        } catch (ExpiredJwtException e) {
            log.error("401 Unauthorized token已过期");
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.setCharacterEncoding("utf-8");
            response.setContentType("text/html;charset=gb2312");
            response.getOutputStream().print("401 Unauthorized Token Expired");
            return false;
        }  catch (UnsupportedJwtException e) {
            log.error("401 Unauthorized Token格式错误");
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.setCharacterEncoding("utf-8");
            response.setContentType("text/html;charset=gb2312");
            response.getOutputStream().print("401 Unauthorized Token Format Error");
            return false;
        } catch (MalformedJwtException e) {
            log.error("401 Unauthorized Token没有被正确构造");
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.setCharacterEncoding("utf-8");
            response.setContentType("text/html;charset=gb2312");
            response.getOutputStream().print("401 Unauthorized Token Format Error");
            return false;
        } catch (SignatureException e) {
            log.error("401 Unauthorized token不正确");
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.setCharacterEncoding("utf-8");
            response.setContentType("text/html;charset=gb2312");
            response.getOutputStream().print("401 Unauthorized Signature Error");
            return false;
        } catch (IllegalArgumentException e) {
            log.error("401 Unauthorized 非法参数异常");
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.setCharacterEncoding("utf-8");
            response.setContentType("text/html;charset=gb2312");
            response.getOutputStream().print("401 Unauthorized Parameter Error");
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        //403
        if (claims.get("adminId") == null) {
            log.error("403 Forbidden");
            response.setStatus(HttpStatus.FORBIDDEN.value());
            response.sendError(HttpStatus.FORBIDDEN.value(), "403 Forbidden");
            response.setHeader("Error", "403 Forbidden");
            return false;
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
        //log.info("--------------处理请求完成后视图渲染之前的处理操作---------------");
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
        //log.info("---------------视图渲染之后的操作-------------------------");
    }
}