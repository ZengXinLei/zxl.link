package com.example.demo.config;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.example.demo.entity.User;
import com.example.demo.filter.JWTAuthenticationFilter;
import com.example.demo.service.ZUserService;
import com.example.demo.service.impl.UserDetailsServiceImpl;
import com.example.demo.util.R;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;

/**
 * @className: com.example.demo.config.SecurityConfig
 * @description: TODO
 * @author: zxl
 * @create: 2021-07-02 0:47
 */
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private ZUserService zUserService;
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder());
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        super.configure(web);
    }

    @Bean
    public RoleHierarchy roleHierarchy(){
        RoleHierarchyImpl roleHierarchy = new RoleHierarchyImpl();
        String hierarchy = "ROLE_DBA > ROLE_ADMIN \n ROLE_ADMIN > ROLE_USER";
        roleHierarchy.setHierarchy(hierarchy);
        return roleHierarchy;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable().exceptionHandling()
                .and()
                .authorizeRequests()
//                .antMatchers("/**").hasRole("USER")

                .anyRequest().permitAll()
                .and()
                .formLogin()
                .permitAll()
                .successHandler(((httpServletRequest, httpServletResponse, authentication) -> {
                    // 获取jwt token，返回给前端
                    String name = httpServletRequest.getParameter("name").toString();


                    User user = zUserService.getOne(Wrappers.<User>lambdaQuery().eq(User::getName, name));
                    Jwts.claims().clear();

                    String token = SecurityConstant.TOKEN_SPLIT + Jwts.builder()
//                        主题 放入用户名
                            .setSubject(httpServletRequest.getParameter("name"))
//                        自定义属性,放入用户拥有请求权限
                            .claim(SecurityConstant.AUTHORITIES, authentication.getAuthorities())

                            .claim(SecurityConstant.ID,user.getId())
                            //失效时间
                            .setExpiration(new Date(System.currentTimeMillis() + SecurityConstant.EXPIRATION_TIME))
//                                签名算法和密钥
                            .signWith(SignatureAlgorithm.HS512, SecurityConstant.JWT_SIGN_KEY)
                            .compact();
                    PrintWriter writer = httpServletResponse.getWriter();
                    // 将登陆信息写回到前端
                    HashMap<String, Object> map = new HashMap<>();
                    map.put("token", token);
                    new ObjectMapper().writeValue(writer, R.ok(map));
                    writer.flush();
                    writer.close();
                }))
                .failureHandler(((httpServletRequest, httpServletResponse, e) -> {
                    httpServletResponse.setCharacterEncoding("utf-8");
                    PrintWriter writer = httpServletResponse.getWriter();
                    new ObjectMapper().writeValue(writer, R.error("账号或密码错误"));
                    writer.flush();
                    writer.close();
                }))


                .loginProcessingUrl("/login")
                .usernameParameter("name")
                .passwordParameter("password")
                .and()
                //跨域

                .cors().configurationSource(corsConfigurationSource())
                .and()
                //jwt拦截器验证
                .addFilter(new JWTAuthenticationFilter(authenticationManager(), 7))


        ;
        // 禁用缓存

        http.headers().cacheControl();
//        .successHandler();
    }

    private CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.addAllowedOrigin("*");    //同源配置，*表示任何请求都视为同源，若需指定ip和端口可以改为如“localhost：8080”，多个以“，”分隔；
        corsConfiguration.addAllowedHeader("*");//header，允许哪些header，本案中使用的是token，此处可将*替换为token；
        corsConfiguration.addAllowedMethod("*");    //允许的请求方法，PSOT、GET等
        corsConfiguration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration);
        return source;
    }
}
