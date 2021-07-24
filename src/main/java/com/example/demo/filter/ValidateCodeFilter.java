package com.example.demo.filter;

import com.example.demo.util.R;
import com.example.demo.util.RedisUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @className: com.example.demo.filter.ValidateCodeFilter
 * @description: TODO
 * @author: zxl
 * @create: 2021-07-24 20:18
 */
public class ValidateCodeFilter extends OncePerRequestFilter {
    RedisUtils redisUtils;
    public ValidateCodeFilter(RedisUtils redisUtils){
        this.redisUtils=redisUtils;
    }
    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {

        String path = httpServletRequest.getServletPath();
        if(path.startsWith("/login")||path.equals("/user/register")){
            String uuid = httpServletRequest.getParameter("uuid");
            String code=httpServletRequest.getParameter("code");
            String s = redisUtils.get(uuid);
            if (!code.equals(s)){
                httpServletResponse.setCharacterEncoding("utf-8");
                PrintWriter writer = httpServletResponse.getWriter();
                new ObjectMapper().writeValue(writer, R.error("验证码错误"));
                return;
            }
        }
        filterChain.doFilter(httpServletRequest,httpServletResponse);
    }
}
