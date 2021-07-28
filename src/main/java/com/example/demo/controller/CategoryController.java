package com.example.demo.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.example.demo.entity.Category;
import com.example.demo.util.R;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.example.demo.controller.BaseController;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zxl
 * @since 2021-07-04
 */
@RestController
@RequestMapping("/category")
public class CategoryController extends BaseController {



//    @PreAuthorize("hasRole('ROLE_USER')")
    @RequestMapping("/list")
    public R list(){
        List<Category> list = zCategoryService.list(Wrappers.<Category>lambdaQuery().eq(Category::getUid, getUserId()));
        return R.ok().put("list",list);
    }


    @RequestMapping("/listMap")
    public R listMap(@RequestParam("uid") Integer uid){
        List<Map<String, Object>> maps = zCategoryService.listMap(uid);
        return R.ok().put("data",maps);
    }

}

