package com.example.demo.filter;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.example.demo.config.SecurityConstant;
import com.example.demo.entity.Role;
import com.example.demo.entity.User;
import com.example.demo.service.ZRoleService;
import com.example.demo.util.R;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
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
//            UsernamePasswordAuthenticationToken ?????? AbstractAuthenticationToken ?????? Authentication
//            ????????????????????????????????????????????????????????????????????? UsernamePasswordAuthenticationToken?????? (Authentication)???
            UsernamePasswordAuthenticationToken authentication = getAuthentication(header, response);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (Exception e) {
            e.toString();
        }

        chain.doFilter(request, response);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(String header, HttpServletResponse response) throws IOException {

        response.setCharacterEncoding("utf-8");
        User user = new User();

//        ??????
        List<GrantedAuthority> authorities = new ArrayList<>();


        try {
//            ??????token
            Claims claims = Jwts.parser()
                    .setSigningKey(SecurityConstant.JWT_SIGN_KEY)
                    .parseClaimsJws(header.replace(SecurityConstant.TOKEN_SPLIT, ""))
                    .getBody();
//            logger.info("claims???" + claims);
//            ???????????????
            user.setName(claims.getSubject());
            user.setId((Integer) claims.get(SecurityConstant.ID));
            user.setRoleList(new ArrayList<Role>());
//            username = claims.getSubject();
//            id = (int) claims.get(SecurityConstant.ID);
//            logger.info("username???" + username);
//            ????????????
            List<Map<String, String>> mapList = (List<Map<String, String>>) claims.get(SecurityConstant.AUTHORITIES);
            if (mapList.size() != 0) {
                mapList.forEach(e -> {
                    Role role = new Role();
                    role.setName(e.get("authority"));
                    authorities.add(new SimpleGrantedAuthority(e.get("authority")));
                    user.getRoleList().add(role);

                });
            }





//            System.out.println(o);
        } catch (ExpiredJwtException e) {

            PrintWriter writer = response.getWriter();
            // ??????????????????????????????
            new ObjectMapper().writeValue(writer, R.error(401, "?????????????????????????????????"));
            writer.flush();
            writer.close();
        } catch (Exception e) {

            e.printStackTrace();
            PrintWriter writer = response.getWriter();
            // ??????????????????????????????
            new ObjectMapper().writeValue(writer, R.error("??????token??????"));
            writer.flush();
            writer.close();
        }

        if (!"".equals(user.getName())) {
//          ??????password?????????null
            user.setPassword("");

            return new UsernamePasswordAuthenticationToken(user, null, authorities);
        }
        return null;
    }
}