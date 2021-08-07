package com.example.demo.mapper;

import com.example.demo.entity.Tag;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author zxl
 * @since 2021-07-03
 */
@Mapper
public interface TagMapper extends BaseMapper<Tag> {


    List<Tag> listByUid(@Param("uid") Integer uid);
}
