package com.example.demo.filter;

import com.example.demo.config.SecurityConstant;
import com.example.demo.entity.User;
import com.example.demo.util.R;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.util.StringUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @className: com.example.demo.filter.JWTAuthenticationFilter
 * @description: TODO
 * @author: zxl
 * @create: 2021-07-02 5:44
 */
public class JWTAuthenticationFilter extends BasicAuthenticationFilter {

    private Integer tokenExpireTime;

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager, Integer tokenExpireTime) {
        super(authenticationManager);
        this.tokenExpireTime = tokenExpireTime;
    }

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager, AuthenticationEntryPoint authenticationEntryPoint) {
        super(authenticationManager, authenticationEntryPoint);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {

        String header = request.getHeader(SecurityConstant.HEADER);
        if (header == null || "".equals(header)) {
            header = request.getParameter(SecurityConstant.HEADER);
        }
        boolean notValid = header == null || "".equals(header) || (!header.startsWith(SecurityConstant.TOKEN_SPLIT));
        if (notValid) {
            chain.doFilter(request, response);
            return;
        }
        try {
//            UsernamePasswordAuthenticationToken 继承 AbstractAuthenticationToken 实现 Authentication
//            所以当在页面中输入用户名和密码之后首先会进入到 UsernamePasswordAuthenticationToken验证 (Authentication)，
            UsernamePasswordAuthenticationToken authentication = getAuthentication(header, response);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (Exception e) {
            e.toString();
        }

        chain.doFilter(request, response);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(String header, HttpServletResponse response) throws IOException {

//        用户名
        String username = null;
//        权限
        List<GrantedAuthority> authorities = new ArrayList<>();


        try {
//            解析token
            Claims claims = Jwts.parser()
                    .setSigningKey(SecurityConstant.JWT_SIGN_KEY)
                    .parseClaimsJws(header.replace(SecurityConstant.TOKEN_SPLIT, ""))
                    .getBody();
            logger.info("claims：" + claims);
//            获取用户名
            username = claims.getSubject();
            logger.info("username：" + username);
//            获取权限
//            String authority = claims.get(SecurityConstant.AUTHORITIES).toString();
//            logger.info("authority：" + authority);
//            if (!StringUtils.isEmpty(authority)) {
//                Arrays.stream(authority.split(",")).forEach(e -> {
//                    authorities.add(new SimpleGrantedAuthority(e));
//
//                });
//            }
            List<Map<String, String>> mapList = (List<Map<String, String>>) claims.get(SecurityConstant.AUTHORITIES);
            if(mapList.size()!=0){
                mapList.forEach(e->{
                    authorities.add(new SimpleGrantedAuthority(e.get("authority")));
                });
            }

//            System.out.println(o);
        } catch (ExpiredJwtException e) {

            PrintWriter writer = response.getWriter();
            // 将登陆信息写回到前端
            new ObjectMapper().writeValue(writer, R.error(401, "登录已失效，请重新登录"));
            writer.flush();
            writer.close();
        } catch (Exception e) {

            PrintWriter writer = response.getWriter();
            // 将登陆信息写回到前端
            new ObjectMapper().writeValue(writer, R.error("解析token错误"));
            writer.flush();
            writer.close();
        }

        if (!"".equals(username)) {
//            踩坑提醒 此处password不能为null
            User user = new User();
            user.setName(username);
            user.setPassword("");
            return new UsernamePasswordAuthenticationToken(user, null, authorities);
        }
        return null;
    }
}