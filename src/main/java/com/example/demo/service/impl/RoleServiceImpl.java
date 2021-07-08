package com.example.demo.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.entity.Role;
import com.example.demo.mapper.RoleMapper;
import com.example.demo.service.ZRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zxl
 * @since 2021-07-02
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements ZRoleService {

    @Override
    public Page<Role> listPage(Map<String, Integer> map) {
        Page<Role> rolePage = new Page<>(map.get("page"), map.get("limit"));
        Page<Role> page = baseMapper.selectPage(rolePage, Wrappers.<Role>lambdaQuery());
        return page;
    }
}
