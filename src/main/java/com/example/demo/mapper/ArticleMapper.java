package com.example.demo.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.entity.Article;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zxl
 * @since 2021-07-02
 */
@Mapper
public interface ArticleMapper extends BaseMapper<Article> {

    public Page<Article> listByCategory(Page<Article> page,@Param("param") Map<String,Object> param);

}
