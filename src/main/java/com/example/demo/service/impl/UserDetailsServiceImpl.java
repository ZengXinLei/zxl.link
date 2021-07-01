package com.example.demo.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.example.demo.entity.Role;
import com.example.demo.entity.User;
import com.example.demo.entity.UserRole;
import com.example.demo.service.ZRoleService;
import com.example.demo.service.ZUserRoleService;
import com.example.demo.service.ZUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @className: com.example.demo.service.impl.UserDetailsServiceImpl
 * @description: TODO
 * @author: zxl
 * @create: 2021-07-02 1:23
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private ZUserService zUserService;

    @Autowired
    ZUserRoleService zUserRoleService;

    @Autowired
    ZRoleService zRoleService;


    @Autowired
    PasswordEncoder passwordEncoder;
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {

        User user = zUserService.getOne(Wrappers.<User>lambdaQuery()
                .eq(User::getName, s));
        if(null==user)
        {
            throw new UsernameNotFoundException("用户名不存在!!!");
        }
        List<Integer> roleIds = zUserRoleService.list(
                Wrappers.<UserRole>lambdaQuery()
                        .eq(UserRole::getUid, user.getId())
        ).stream().map(UserRole::getRid).collect(Collectors.toList());


        List<Role> roleList = zRoleService.list(
                Wrappers.<Role>lambdaQuery()
                        .in(Role::getId, roleIds)
        );

        user.setRoleList(roleList);
        return new org.springframework.security.core.userdetails.User(s, passwordEncoder.encode(user.getPassword()),user.getAuthorities() );
    }
}