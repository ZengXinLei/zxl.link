package com.example.demo.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.entity.*;
import com.example.demo.util.GiteeUtil;
import com.example.demo.util.R;
import com.qcloud.cos.model.PutObjectRequest;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author zxl
 * @since 2021-07-02
 */
@RestController
@RequestMapping("/article")
public class ArticleController extends BaseController {

    @Value(value = "${file.path}")
    private String path;

    //    @PreAuthorize(value = "hasRole('ROLE_USER')")
    @Deprecated
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public R upload(@RequestParam("img") MultipartFile file) {
        if (file.getSize() >= 5 * 1024 * 1024) {
            return R.ok(500, "图片大小不能超过5M");
        }
        String filename = new Date().getTime() + "_" + file.getOriginalFilename();

        File dest = new File(path + "/" + filename);
        try {
            file.transferTo(dest);
            return R.ok().put("fileName", filename).put("pre", "article/getImg?fileName=");
        } catch (IOException e) {
            return R.error();

        }


    }

    @Deprecated
    @RequestMapping(value = "/getImg", method = RequestMethod.GET, produces = MediaType.IMAGE_JPEG_VALUE)
    public byte[] getImg(@RequestParam("fileName") String fileName) {
        File file = new File(path + "/" + fileName);
        byte[] bytes = new byte[0];
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            bytes = new byte[fileInputStream.available()];
            fileInputStream.read(bytes, 0, fileInputStream.available());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bytes;
    }


    @Transactional(rollbackFor = Exception.class)
    @RequestMapping(value = "/saveMarkdown", method = RequestMethod.POST)
    public R saveMarkdown(@RequestBody Article article) throws IOException {


        InputStream inputStream = IOUtils.toInputStream(article.getContentHtml());
        int length = article.getContentHtml().length();
        byte[] bytes = new byte[length * 2];
        inputStream.read(bytes);
        Gitee gitee = new Gitee();
        gitee.setContent(Base64.encodeBase64String(bytes));
        gitee.setPath("/article/" + new Date().getTime());


        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("E:/tmp"));
        bufferedWriter.write(article.getContentHtml());
        bufferedWriter.close();
        String key="/article/"+new Date().getTime();
        cosClient.putObject(new PutObjectRequest(txyConfig.getTableName(),key,new File("E:/tmp")));

//        R save = GiteeUtil.save(zGiteeService, restTemplate, getUser(), gitee.getContent(), gitee.getPath());

//        Map content = (Map) save.get("content");
        article.setContentUrl(String.format("https://%s.cos.%s.myqcloud.com%s",txyConfig.getTableName(),txyConfig.getRegion(),key ));
        article.setUid(getUserId());
        article.setTime(new Date().getTime()/1000);
        zArticleService.save(article);
        zArticleTagService.saveBatch(article.getTags().stream().map(e -> {

            ArticleTag articleTag = new ArticleTag();
            articleTag.setAid(article.getId());
            articleTag.setTid(e.getId());
            return articleTag;
        }).collect(Collectors.toList()));
        zArticleCategoryService.saveBatch(article.getCategories().stream().map(e -> {
            ArticleCategory articleCategory = new ArticleCategory();
            articleCategory.setAid(article.getId());
            articleCategory.setCid(e.getId());
            return articleCategory;
        }).collect(Collectors.toList()));

        return R.ok();
    }


    @RequestMapping(value = "/getArticleByUserId", method = RequestMethod.POST)
    public R getArticleByUserId(@RequestBody Article article) {
        Article one = zArticleService.getOne(Wrappers.<Article>lambdaQuery().eq(Article::getId, article.getId()).eq(Article::getUid, article.getUid()));
        if (one == null) {
            return R.error("这篇博客不存在");

        }

        one.setTags(
                zArticleTagService.list(Wrappers.<ArticleTag>lambdaQuery()
                        .eq(ArticleTag::getAid, one.getId())).stream()
                        .map(e -> zTagService.getById(e.getTid())).collect(Collectors.toList()));
        one.setCategories(
                zArticleCategoryService.list(Wrappers.<ArticleCategory>lambdaQuery()
                        .eq(ArticleCategory::getAid, one.getId())).stream()
                        .map(e -> zCategoryService.getById(e.getCid())).collect(Collectors.toList()));
        return R.ok().put("article", one);

    }


    @RequestMapping(value = "/list",method = RequestMethod.POST)
    public R list(@RequestBody Map<String,Integer> map){
        Integer uid=map.get("uid");
        IPage<Article> iPage = new Page<>(map.get("page"),map.get("limit"));
        User user = getUser();
        IPage<Article> page = zArticleService.page(iPage, Wrappers.<Article>lambdaQuery().eq(Article::getUid, uid).eq(user == null, Article::getPublishType, 1));
        return R.ok().put("data",page);

    }


    @RequestMapping(value = "/listByCategory",method = RequestMethod.POST)
    public R listByCategory(@RequestBody Map<String,Object> map){
        Integer current = Integer.valueOf(map.get("current").toString());
        Integer size = Integer.valueOf(map.get("size").toString());
        Page<Article> page = new Page<>(current, size);


        Page<Article> data = zArticleService.listByCategory(page, map);
        return R.ok().put("data",data);

    }

    @RequestMapping("/archives")
    public R archives(@RequestParam("uid") Integer uid){
        String pattern = "yyyy-MM-dd";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

        User user = getUser();
        List<Map<String, Object>> maps = zArticleService.listMaps(
                new QueryWrapper<Article>()
                        .select("id,title,FROM_UNIXTIME(time/1000,'%Y年%m月') as time,FROM_UNIXTIME(time/1000,'%d日') as time1")
                        .eq("uid",uid)
                .eq(user==null,"publish_type",1)
        );
        Map<Object, List<Map<String, Object>>> time = maps.stream().collect(Collectors.groupingBy(e -> e.get("time")));
        return R.ok().put("data",time);
    }
}

