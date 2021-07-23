package com.example.demo.controller;


import com.example.demo.entity.Article;
import com.example.demo.entity.Gitee;
import com.example.demo.util.GiteeUtil;
import com.example.demo.util.R;
import com.qcloud.cos.model.PutObjectRequest;
import com.qcloud.cos.model.PutObjectResult;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zxl
 * @since 2021-07-05
 */
@Configuration
@RestController
@RequestMapping("/gitee")
public class GiteeController extends BaseController {




    @RequestMapping(value = "/saveImg",method = RequestMethod.POST)
    public R saveImg(@RequestParam("img") MultipartFile content, @RequestParam("fileType") String fileType) throws IOException {


        Gitee gitee = new Gitee();
        Date date = new Date();
        if(null==gitee)
            return R.error("没有配置图床");
        gitee.setMessage("zxl.link的用户："+getUser().getName());
        //设置内容

        //如果上传的图片大于1MB,使用腾讯云的对象存储
        if(content.getSize()/1024>=1024){

            String key="/img/"+new Date().getTime()+"."+fileType;
            File file = new File("E:/tmp."+fileType);
            content.transferTo(file);
            PutObjectRequest putObjectRequest = new PutObjectRequest(txyConfig.getTableName(), key, file);
            cosClient.putObject(putObjectRequest);

            Map<String, Object> stringObjectMap = new HashMap<>();
            stringObjectMap.put("download_url", String.format("https://%s.cos.%s.myqcloud.com%s",txyConfig.getTableName(),txyConfig.getRegion(),key ));
            file.deleteOnExit();
            return R.ok().put("content",stringObjectMap);
        }
        gitee.setContent(Base64.encodeBase64String(content.getBytes()));
        Calendar calendar = Calendar.getInstance();

        gitee.setPath(getUserId()+"/" + calendar.get(Calendar.YEAR) + "/" + (calendar.get(Calendar.MONTH)+1) + "/" + calendar.get(Calendar.DAY_OF_MONTH) + "/" + date.getTime()+"."+fileType);



        return GiteeUtil.save(zGiteeService,restTemplate,getUser(), gitee.getContent(), gitee.getPath());
    }



}

