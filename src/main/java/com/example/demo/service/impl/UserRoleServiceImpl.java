package com.example.demo.service.impl;

import com.example.demo.entity.UserRole;
import com.example.demo.mapper.UserRoleMapper;
import com.example.demo.service.ZUserRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zxl
 * @since 2021-07-02
 */
@Service
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements ZUserRoleService {

}
