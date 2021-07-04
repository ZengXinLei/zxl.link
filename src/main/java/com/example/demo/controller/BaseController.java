package com.example.demo.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.example.demo.entity.Role;
import com.example.demo.entity.User;
import com.example.demo.service.*;
import org.apache.catalina.security.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

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
