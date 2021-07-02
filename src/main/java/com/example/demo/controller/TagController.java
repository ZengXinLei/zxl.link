package com.example.demo.controller;


import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.example.demo.entity.Tag;
import com.example.demo.util.R;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;
import com.example.demo.controller.BaseController;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zxl
 * @since 2021-07-03
 */
@RestController
@RequestMapping("/tag")
public class TagController extends BaseController {


    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping("/list")
    public R list(){
        List<Tag> parents = zTagService.list(
                Wrappers.<Tag>lambdaQuery()
                        .eq(Tag::getParentId, 0)
        );
        parents.forEach(e->{
            e.setChildren(zTagService.list(
                    Wrappers.<Tag>lambdaQuery()
                            .eq(Tag::getParentId,e.getId())
            ));
        });
        return R.ok().put("list",parents);

    }
}

