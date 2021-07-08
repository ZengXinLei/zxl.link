package com.example.demo.controller;


import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.example.demo.entity.Permission;
import com.example.demo.entity.PermissionRole;
import com.example.demo.util.R;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.example.demo.controller.BaseController;

import java.util.List;
import java.util.Map;
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



    @RequestMapping("/listByRoleId")
    public R listByRoleId(@RequestParam("rid") Integer rid)
    {
        List<Integer> list = zPermissionRoleService.list(Wrappers.<PermissionRole>lambdaQuery().eq(PermissionRole::getRid, rid)).stream().map(PermissionRole::getPid).collect(Collectors.toList());
        if(list.size()==0)
            return R.ok().put("data",list);

        List<Integer> permissions = zPermissionService.list(Wrappers.<Permission>lambdaQuery().in(Permission::getId,list)).stream().map(Permission::getId).collect(Collectors.toList());
        return R.ok().put("data",permissions);

    }

    @Transactional(rollbackFor = Exception.class)
    @RequestMapping("/updateByRoleId")
    public R updateByRoleId(@RequestBody Map<String,Object> map){
        Integer rid = Integer.valueOf(map.get("rid").toString());
        List<Integer> pIds = JSON.parseArray(map.get("pIds").toString(), Integer.class);

        zPermissionRoleService.remove(Wrappers.<PermissionRole>lambdaQuery().eq(PermissionRole::getRid,rid));

        List<PermissionRole> collect = pIds.stream().map(e -> {
            PermissionRole permissionRole = new PermissionRole();
            permissionRole.setPid(e);
            permissionRole.setRid(rid);
            return permissionRole;
        }).collect(Collectors.toList());
        zPermissionRoleService.saveBatch(collect);


        return R.ok("修改成功");
    }
    @RequestMapping("/save")
    public R save(@RequestBody Permission permission){
        zPermissionService.save(permission);
        return R.ok("添加成功");
    }
    @Transactional(rollbackFor = Exception.class)
    @RequestMapping("/update")
    public R upddate(@RequestBody Permission permission){
        zPermissionService.updateById(permission);
        return R.ok("修改成功");
    }
    @Transactional(rollbackFor = Exception.class)
    @RequestMapping("/remove")
    public R remove(@RequestBody Permission permission){
        zPermissionService.removeById(permission.getId());
        return R.ok("删除成功");
    }
}

