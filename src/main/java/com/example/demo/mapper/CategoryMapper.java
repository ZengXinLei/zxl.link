package com.example.demo.mapper;

import com.example.demo.entity.Category;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zxl
 * @since 2021-07-04
 */
@Mapper
public interface CategoryMapper extends BaseMapper<Category> {

    List<Map<String,Object>> listMap(@Param("uid") Integer uid);

}
