package com.example.demo.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.demo.entity.Pagecomponent;
import com.example.demo.util.R;
import org.springframework.transaction.annotation.Transactional;
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
 * @since 2021-07-07
 */
@RestController
@RequestMapping("/pagecomponent")
public class PagecomponentController extends BaseController {


    @RequestMapping("/list")
    public R list(@RequestBody Map<String,Integer> params){
        IPage<Pagecomponent> page = zPagecomponentService.listPage(params);
        return R.ok().put("data",page);
    }


    @Transactional(rollbackFor = Exception.class)
    @RequestMapping("/save")
    public R save(@RequestBody Pagecomponent pagecomponent){

        zPagecomponentService.save(pagecomponent);
        return R.ok("添加组件成功");
    }
    @Transactional(rollbackFor = Exception.class)
    @RequestMapping("/update")
    public R update(@RequestBody Pagecomponent pagecomponent){

        zPagecomponentService.updateById(pagecomponent);
        return R.ok("修改成功");
    }
    @Transactional(rollbackFor = Exception.class)
    @RequestMapping("/remove")
    public R remove(@RequestParam("id") int id){

        zPagecomponentService.removeById(id);
        return R.ok("删除成功");
    }

}

