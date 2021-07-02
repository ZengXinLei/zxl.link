package com.example.demo.controller;

import com.example.demo.entity.User;
import com.example.demo.service.*;
import org.apache.catalina.security.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

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

//
//    protected User getUser(){
//        if()
//    }
//
//    protected List<Long> getPermissionGroup(){
//
//    }
}
