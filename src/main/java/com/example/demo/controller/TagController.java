package com.example.demo.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.example.demo.entity.Tag;
import com.example.demo.util.R;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import com.example.demo.controller.BaseController;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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


//    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "/list",method = RequestMethod.POST)
    public R list(@RequestBody Map<String,Integer> map){
        IPage<Tag> parents = zTagService.pageList(map);

        parents.getRecords().forEach(e->{
            e.setChildren(zTagService.list(
                    Wrappers.<Tag>lambdaQuery()
                            .eq(Tag::getParentId,e.getId())
            ));
        });
        return R.ok().put("list",parents);

    }

    @Transactional(rollbackFor = Exception.class)
//    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "/save",method = RequestMethod.POST)
    public R save(@RequestBody Tag tag){

        List<Tag> tags = zTagService.list(Wrappers.<Tag>lambdaQuery()
                .eq(Tag::getParentId, tag.getParentId())
                .eq(Tag::getLabel, tag.getLabel())
        );
        if(!tags.isEmpty())
            return R.error("标签不能重复");
        zTagService.save(tag);
        return R.ok("添加成功");
    };
    @Transactional(rollbackFor = Exception.class)
//    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "/remove",method = RequestMethod.POST)
    public R remove(@RequestParam("ids") String tagIds)
    {
        List<String> ids = Arrays.stream(tagIds.split(",")).collect(Collectors.toList());
        zTagService.removeByIds(ids);

        //删除子标签
      ids.stream().filter(e->!"0".equals(e)).map(Integer::parseInt).forEach(e->{
            zTagService.remove(Wrappers.<Tag>lambdaQuery()
            .eq(Tag::getParentId,e)
            );
        });

        return R.ok("删除成功");
    }



    @RequestMapping("listByUid")
    public R listByUid(@RequestParam("uid") Integer uid){
        List<Tag> tags = zTagService.listByUid(uid);
        return R.ok().put("tag",tags);
    }
}

