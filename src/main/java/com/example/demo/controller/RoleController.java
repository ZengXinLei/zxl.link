package com.example.demo.controller;


import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.entity.Role;
import com.example.demo.util.R;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.example.demo.controller.BaseController;

import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zxl
 * @since 2021-07-02
 */
@RestController
@RequestMapping("/role")
public class RoleController extends BaseController {


    @RequestMapping("/list")
    public R list(@RequestBody Map<String,Integer> params){
        Page<Role> rolePage = zRoleService.listPage(params);
        return R.ok().put("data",rolePage);

    }

    @RequestMapping("/save")
    public R save(@RequestBody Role role)
    {
        Role one = zRoleService.getOne(Wrappers.<Role>lambdaQuery().eq(Role::getName, role.getName()));
        if(one!=null){
            return R.error(one.getName()+"权限已存在");
        }
        zRoleService.save(role);

        return R.ok("添加成功");
    }
    @RequestMapping("/update")
    public R update(@RequestBody Role role)
    {
        Role one = zRoleService.getOne(Wrappers.<Role>lambdaQuery().eq(Role::getName, role.getName()));
        if(one!=null){
            return R.error(one.getName()+"权限已存在");
        }
        zRoleService.updateById(role);
        return R.ok("修改成功");
    }
    @RequestMapping("/remove")
    public R remove(@RequestParam("id") Integer id)
    {

        zRoleService.removeById(id);
        return R.ok("删除成功");
    }


}

