package com.example.demo.config;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.example.demo.component.RoleAccessDecisionManager;
import com.example.demo.component.ZxlMetadataSource;
import com.example.demo.entity.User;
import com.example.demo.filter.JWTAuthenticationFilter;
import com.example.demo.filter.ValidateCodeFilter;
import com.example.demo.service.ZUserService;
import com.example.demo.service.impl.UserDetailsServiceImpl;
import com.example.demo.util.R;
import com.example.demo.util.RedisUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
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




    @Autowired
    RoleAccessDecisionManager roleAccessDecisionManager;

    @Autowired
    ZxlMetadataSource zxlMetadataSource;
    @Autowired
    RedisUtils redisUtils;
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .addFilterBefore(new ValidateCodeFilter(redisUtils), UsernamePasswordAuthenticationFilter.class).authorizeRequests()

                .withObjectPostProcessor(new ObjectPostProcessor<FilterSecurityInterceptor>() {
                    @Override
                    public <O extends FilterSecurityInterceptor> O postProcess(O o) {
                        o.setSecurityMetadataSource(zxlMetadataSource);
                        o.setAccessDecisionManager(roleAccessDecisionManager);
                        return o;
                    }
                }).and()
                .csrf().disable().exceptionHandling()
                .and()
                .authorizeRequests()
//                .antMatchers("/**").hasRole("USER")

                .anyRequest().permitAll()
                .and()
                .formLogin()
                .permitAll()
                .successHandler(((httpServletRequest, httpServletResponse, authentication) -> {
                    // ??????jwt token??????????????????
                    String name = httpServletRequest.getParameter("name").toString();


                    User user = zUserService.getOne(Wrappers.<User>lambdaQuery().eq(User::getName, name));
                    Jwts.claims().clear();

                    String token = SecurityConstant.TOKEN_SPLIT + Jwts.builder()
//                        ?????? ???????????????
                            .setSubject(httpServletRequest.getParameter("name"))
//                        ???????????????,??????????????????????????????
                            .claim(SecurityConstant.AUTHORITIES, authentication.getAuthorities())

                            .claim(SecurityConstant.ID,user.getId())
                            //????????????
                            .setExpiration(new Date(System.currentTimeMillis() + SecurityConstant.EXPIRATION_TIME))
//                                ?????????????????????
                            .signWith(SignatureAlgorithm.HS512, SecurityConstant.JWT_SIGN_KEY)
                            .compact();
                    PrintWriter writer = httpServletResponse.getWriter();
                    // ??????????????????????????????
                    HashMap<String, Object> map = new HashMap<>();
                    map.put("token", token);
                    new ObjectMapper().writeValue(writer, R.ok(map));
                    writer.flush();
                    writer.close();
                }))
                .failureHandler(((httpServletRequest, httpServletResponse, e) -> {
                    httpServletResponse.setCharacterEncoding("utf-8");
                    PrintWriter writer = httpServletResponse.getWriter();
                    new ObjectMapper().writeValue(writer, R.error("?????????????????????"));
                    writer.flush();
                    writer.close();
                }))


                .loginProcessingUrl("/login")
                .usernameParameter("name")
                .passwordParameter("password")
                .and()
                //??????

                .cors().configurationSource(corsConfigurationSource())
                .and()
                //jwt???????????????
                .addFilter(new JWTAuthenticationFilter(authenticationManager(), 7))


        ;
        // ????????????

        http.headers().cacheControl();
//        .successHandler();
    }

    private CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.addAllowedOrigin("*");    //???????????????*????????????????????????????????????????????????ip???????????????????????????localhost???8080?????????????????????????????????
        corsConfiguration.addAllowedHeader("*");//header???????????????header????????????????????????token???????????????*?????????token???
        corsConfiguration.addAllowedMethod("*");    //????????????????????????PSOT???GET???
        corsConfiguration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration);
        return source;
    }
}
