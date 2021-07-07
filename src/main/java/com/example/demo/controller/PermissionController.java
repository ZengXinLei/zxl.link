package com.example.demo.controller;


import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.example.demo.entity.Permission;
import com.example.demo.util.R;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;
import com.example.demo.controller.BaseController;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zxl
 * @since 2021-07-06
 */
@RestController
@RequestMapping("/permission")
public class PermissionController extends BaseController {


    public void dfs(Permission p){
        if(p.getChildren()!=null)
            return;
        p.setChildren(zPermissionService.list(Wrappers.<Permission>lambdaQuery()
                .eq(Permission::getParentId, p.getId())));
        p.getChildren().forEach(this::dfs);

    }

    @RequestMapping("/list")
    public R list(){

        List<Permission> list = zPermissionService.list(Wrappers.<Permission>lambdaQuery()
                .eq(Permission::getParentId, 0));





        list.forEach(this::dfs);

        return R.ok().put("list",list);

    }

}

