package com.example.demo.component;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.example.demo.entity.Permission;
import com.example.demo.entity.PermissionRole;
import com.example.demo.entity.Role;
import com.example.demo.service.ZPermissionRoleService;
import com.example.demo.service.ZPermissionService;
import com.example.demo.service.ZRoleService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @className: com.example.demo.filter.FilterInvocation
 * @description: TODO
 * @author: zxl
 * @create: 2021-07-06 1:58
 */
@Component
public class ZxlMetadataSource implements FilterInvocationSecurityMetadataSource {

    @Autowired
    ZPermissionRoleService zPermissionRoleService;
    @Autowired
    ZPermissionService zPermissionService;

    @Autowired
    ZRoleService zRoleService;



    //获取当前url对应的权限
    @Override
    public Collection<ConfigAttribute> getAttributes(Object o) throws IllegalArgumentException {
        String requestUrl = ((FilterInvocation) o).getRequestUrl();
        //获取数据库中当前url对应的权限
        Permission one = zPermissionService.getOne(Wrappers.<Permission>lambdaQuery()
                .eq(Permission::getType, 1)
                .eq(Permission::getValue, requestUrl));

        //如果没有就返回空
        if(one==null){
            return null;

        }
        //获取权限角色中间表
        List<PermissionRole> permissionRoles = zPermissionRoleService.list(Wrappers.<PermissionRole>lambdaQuery()
                .eq(PermissionRole::getPid, one.getId()));
        if(permissionRoles.size()==0)
            return null;
        List<java.lang.String> list = zRoleService.list(Wrappers.<Role>lambdaQuery()
                .in(Role::getId, permissionRoles.stream().map(PermissionRole::getRid).collect(Collectors.toList()))).stream().map(Role::getName).collect(Collectors.toList());


        if(list.size()==0)
            return null;
        String[] roleNames = new String[list.size()];
        list.toArray(roleNames);
        //返回当前url所需需要的角色
        return SecurityConfig.createList(roleNames);


    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }
}
