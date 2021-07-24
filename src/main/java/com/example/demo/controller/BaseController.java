package com.example.demo.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.example.demo.config.TXYConfig;
import com.example.demo.entity.Role;
import com.example.demo.entity.User;
import com.example.demo.service.*;
import com.example.demo.util.RedisUtils;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.http.HttpProtocol;
import com.qcloud.cos.region.Region;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.client.RestTemplate;
import org.thymeleaf.TemplateEngine;

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
    ZArticleCategoryService zArticleCategoryService;
    @Autowired
    ZTagService zTagService;

    @Autowired
    ZCategoryService zCategoryService;

    @Autowired
    ZGiteeService zGiteeService;

    @Autowired
    RestTemplate restTemplate;


    @Autowired
    ZPermissionService zPermissionService;
    @Autowired
    ZPermissionRoleService zPermissionRoleService;

    @Autowired
    ZPagecomponentService zPagecomponentService;

    @Autowired
    TXYConfig txyConfig;


    @Autowired
    COSClient cosClient;


    @Autowired
    RedisUtils redisUtils;

    @Autowired
    TemplateEngine templateEngine;

    @Autowired
    JavaMailSender javaMailSender;
    @Bean
    public RestTemplate restTemplate() {

        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        requestFactory.setConnectTimeout(30000);// 设置连接超时，单位毫秒
        requestFactory.setReadTimeout(30000);  //设置读取超时
        RestTemplate restTemplate = new RestTemplate();

        restTemplate.setRequestFactory(requestFactory);
        System.out.println("RestTemplate初始化完成");

        return restTemplate;
    }


    @Bean
    public COSClient cosClient() {
        // 1 初始化用户身份信息（secretId, secretKey）。
        // SECRETID和SECRETKEY请登录访问管理控制台进行查看和管理
        String secretId = txyConfig.getSecretId();
        String secretKey = txyConfig.getSecretKey();
        COSCredentials cred = new BasicCOSCredentials(secretId, secretKey);
        // 2 设置 bucket 的地域, COS 地域的简称请参照 https://cloud.tencent.com/document/product/436/6224
        // clientConfig 中包含了设置 region, https(默认 http), 超时, 代理等 set 方法, 使用可参见源码或者常见问题 Java SDK 部分。
        Region region = new Region(txyConfig.getRegion());
        ClientConfig clientConfig = new ClientConfig(region);
        // 这里建议设置使用 https 协议
        clientConfig.setHttpProtocol(HttpProtocol.https);
        // 3 生成 cos 客户端。
        COSClient cosClient = new COSClient(cred, clientConfig);
        System.out.println(" cos 客户端初始化完成");
        return cosClient;
    }



    protected User getUser() {
        User user = null;
        try {
            user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            user.setRoleList(zRoleService.list(Wrappers.<Role>lambdaQuery().in(Role::getName, user.getRoleList().stream().map(Role::getName).collect(Collectors.toList()))));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }


    protected int getUserId() {
        return getUser().getId();
    }


//
//    protected List<Long> getPermissionGroup(){
//
//    }
}
