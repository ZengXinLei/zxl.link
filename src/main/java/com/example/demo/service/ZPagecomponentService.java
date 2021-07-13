package com.example.demo.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.demo.entity.Pagecomponent;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zxl
 * @since 2021-07-07
 */
public interface ZPagecomponentService extends IService<Pagecomponent> {

    IPage<Pagecomponent> listPage(Map<String,Integer> params);

    List<String> listByRoleId(List<Integer> roleIds);
}
