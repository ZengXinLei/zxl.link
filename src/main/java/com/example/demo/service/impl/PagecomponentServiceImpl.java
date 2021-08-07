package com.example.demo.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.entity.Pagecomponent;
import com.example.demo.entity.Permission;
import com.example.demo.mapper.PagecomponentMapper;
import com.example.demo.service.ZPagecomponentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zxl
 * @since 2021-07-07
 */
@Service
public class PagecomponentServiceImpl extends ServiceImpl<PagecomponentMapper, Pagecomponent> implements ZPagecomponentService {

    @Override
    public IPage<Pagecomponent> listPage(Map<String, Integer> params) {
        Page<Pagecomponent> objectPage = new Page<>(params.get("page"), params.get("limit"));
        IPage<Pagecomponent> page=baseMapper.selectPage(objectPage, Wrappers.<Pagecomponent>lambdaQuery());
        return page;
    }

    @Override
    public List<Permission> listByRoleId(List<Integer> roleIds) {
        return baseMapper.listByRoleId(roleIds);
    }
}
