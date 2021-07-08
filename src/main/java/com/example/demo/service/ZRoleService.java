package com.example.demo.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.entity.Role;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zxl
 * @since 2021-07-02
 */
public interface ZRoleService extends IService<Role> {

    Page<Role> listPage(Map<String,Integer> map);
}
