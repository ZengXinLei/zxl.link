package com.example.demo.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.entity.Tag;
import com.example.demo.mapper.TagMapper;
import com.example.demo.service.ZTagService;
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
 * @since 2021-07-03
 */
@Service
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag> implements ZTagService {
    @Override
    public IPage<Tag> pageList(Map<String,Integer> map){

        Page<Tag> tagIPage=new Page<>(map.get("page"),map.get("limit"));
        IPage<Tag> page = baseMapper.selectPage(tagIPage, Wrappers.<Tag>lambdaQuery()
                .eq(Tag::getParentId, map.get("parentId"))
        );
        return page;
    }

    @Override
    public List<Tag> listByUid(Integer uid) {
        return baseMapper.listByUid(uid);
    }


}
