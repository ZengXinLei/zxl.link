package com.example.demo.service;

import com.example.demo.entity.Category;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zxl
 * @since 2021-07-04
 */
public interface ZCategoryService extends IService<Category> {

    List<Map<String,Object>> listMap(Integer uid);
}
