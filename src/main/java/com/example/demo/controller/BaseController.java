package com.example.demo.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.example.demo.entity.Role;
import com.example.demo.entity.User;
import com.example.demo.service.*;
import org.apache.catalina.security.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @className: com.example.demo.controller.BaseController
 * @description: TODO
 * @author: zxl
 * @create: 2021-07-02 3:37
 */

public abstract class BaseController {

    @Autowired
    ZRoleService zRoleService;
    @Autowired
    ZUserRoleService zUserRoleService;
    @Autowired
    ZUserService zUserService;

    @Autowired
    ZArticleService zArticleService;
    @Autowired
    ZArticleTagService zArticleTagService;
    @Autowired
    ZTagService zTagService;

    @Autowired
    ZCategoryService zCategoryService;

    @Autowired
    ZGiteeService zGiteeService;

    @Autowired
    RestTemplate restTemplate;
    @Bean
    public RestTemplate restTemplate(){

        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        requestFactory.setConnectTimeout(30000);// 设置连接超时，单位毫秒
        requestFactory.setReadTimeout(30000);  //设置读取超时
        RestTemplate restTemplate = new RestTemplate();

        restTemplate.setRequestFactory(requestFactory);
        System.out.println("RestTemplate初始化完成");
        return restTemplate;
    }
    protected User getUser(){
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        user.setRoleList(zRoleService.list(Wrappers.<Role>lambdaQuery().in(Role::getName,user.getRoleList().stream().map(Role::getName).collect(Collectors.toList()))));
        return user;
    }


    protected int getUserId(){
        return getUser().getId();
    }
//
//    protected List<Long> getPermissionGroup(){
//
//    }
}
