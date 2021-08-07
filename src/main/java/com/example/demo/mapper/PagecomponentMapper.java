package com.example.demo.mapper;

import com.example.demo.entity.Pagecomponent;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.entity.Permission;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zxl
 * @since 2021-07-07
 */
@Mapper
public interface PagecomponentMapper extends BaseMapper<Pagecomponent> {

    List<Permission> listByRoleId(@Param("roleIds") List<Integer> roleIds);
}
