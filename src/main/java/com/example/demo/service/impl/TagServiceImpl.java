package com.example.demo.service.impl;

import com.example.demo.entity.Tag;
import com.example.demo.mapper.TagMapper;
import com.example.demo.service.ZTagService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

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

}
