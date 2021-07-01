package com.example.demo.config;

import com.example.demo.service.ZUserService;
import com.example.demo.service.impl.UserDetailsServiceImpl;
import com.example.demo.util.R;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.io.PrintWriter;

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
    private UserDetailsServiceImpl zUserService;

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(zUserService).passwordEncoder(new BCryptPasswordEncoder());
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        super.configure(web);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
//                .antMatchers("/**").hasRole("USER")

                .anyRequest().permitAll()
                .and()
                .formLogin()
                .permitAll()
                .successHandler(((httpServletRequest, httpServletResponse, authentication) -> {
                    PrintWriter writer = httpServletResponse.getWriter();
                    new ObjectMapper().writeValue(writer, R.ok("成功了"));
                    writer.flush();
                    writer.close();
                }))
                .failureHandler(((httpServletRequest, httpServletResponse, e) -> {
                    PrintWriter writer = httpServletResponse.getWriter();
                    new ObjectMapper().writeValue(writer, R.error());
                    writer.flush();
                    writer.close();
                }))


                .loginProcessingUrl("/login")
                .usernameParameter("name")
                .passwordParameter("password")
                .and()
                .csrf().disable().exceptionHandling()
        ;
//        .successHandler();
    }
}
