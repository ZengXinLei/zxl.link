package com.example.demo.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * @className: com.example.demo.component.RoleAccessDecisionManager
 * @description: TODO
 * @author: zxl
 * @create: 2021-07-06 2:34
 */
@Component
public class RoleAccessDecisionManager implements AccessDecisionManager {
    /**
     * decide 方法是判定是否拥有权限的决策方法，
     *
     * @param authentication 当前用户的信息
     * @param o              包含客户端发起的请求的requset信息
     * @param collection     当前路径对应的权限
     * @throws AccessDeniedException
     * @throws InsufficientAuthenticationException
     */

    @Autowired
    RoleHierarchy roleHierarchy;

    /**
     * @param authentication
     * @param o
     * @param collection    访问当前url需要的角色
     * @throws AccessDeniedException
     * @throws InsufficientAuthenticationException
     */
    @Override
    public void decide(Authentication authentication, Object o, Collection<ConfigAttribute> collection) throws AccessDeniedException, InsufficientAuthenticationException {



        //当前登录用户的角色
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

        List<? extends GrantedAuthority> hasRoles = authorities.stream().collect(Collectors.toList());
        List<ConfigAttribute> urlNeedRoles=(ArrayList)collection;

        List<? extends GrantedAuthority> reachableGrantedAuthorities = (ArrayList)roleHierarchy.getReachableGrantedAuthorities(authorities);
        AtomicInteger index=new AtomicInteger(0);
        //当前角色的继承关系
        Map<String, Integer> map = reachableGrantedAuthorities.stream().collect(Collectors.toMap(GrantedAuthority::getAuthority, w -> index.getAndIncrement()));

        boolean match = urlNeedRoles.stream().allMatch(e->map.get(e.getAttribute())!=null);
        if(match){
            return;
        }

        throw  new AccessDeniedException("权限不足");
    }

    @Override
    public boolean supports(ConfigAttribute configAttribute) {
        return false;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return false;
    }
}
