package com.example.demo;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.example.demo.entity.Article;
import com.example.demo.service.ZArticleService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Map;

/**
 * @className: com.example.demo.Test
 * @description: TODO
 * @author: zxl
 * @create: 2021-07-26 9:12
 */
@SpringBootTest
public class Main {
    @Autowired
    ZArticleService zArticleService;
    @Test
    public void test(){
        System.out.println("测试");
        List<Map<String, Object>> maps = zArticleService.listMaps(new QueryWrapper<Article>().select("FROM_UNIXTIME(time/1000,'%Y-%m-%d') as time,count(*) as count").groupBy("FROM_UNIXTIME(time/1000,'%Y-%m-%d')"));
        System.out.println(maps);
    }
}
