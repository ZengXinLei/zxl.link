package com.example.demo.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.demo.entity.Tag;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zxl
 * @since 2021-07-03
 */
public interface ZTagService extends IService<Tag> {

    public IPage<Tag> pageList(Map<String,Integer> map);

}
