package com.example.demo.util;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.example.demo.entity.Gitee;
import com.example.demo.entity.User;
import com.example.demo.service.ZGiteeService;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @className: com.example.demo.util.GiteeSave
 * @description: TODO
 * @author: zxl
 * @create: 2021-07-17 1:01
 */
public class GiteeUtil {

    public static R save(ZGiteeService zGiteeService,RestTemplate restTemplate,User user,String content, String path){
        Gitee gitee = zGiteeService.getOne(Wrappers.<Gitee>lambdaQuery()
                .eq(Gitee::getEnable, 1));
        Date date = new Date();
        if(null==gitee)
            return R.error("没有配置图床");
        gitee.setMessage("zxl.link的用户："+user.getName());
        //设置内容
        gitee.setContent(content);
        //userId/year/mounth/day/timestamp.fileType

        gitee.setPath(path);


        String url=String.format("https://gitee.com/api/v5/repos/%s/%s/contents/%s",gitee.getOwner(),gitee.getRepo(),gitee.getPath());
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        headers.add("Referer","https://gitee.com/api/v5/swagger");

        HashMap<String, Object> hashMap = new HashMap<>();




        hashMap.put("access_token",gitee.getAccessToken());
        hashMap.put("content",gitee.getContent());
        hashMap.put("message",gitee.getMessage());

        HttpEntity<String> httpEntity = new HttpEntity<>(JSONObject.toJSONString(hashMap), headers);
        ResponseEntity<Map> responseEntity = restTemplate.postForEntity(url, httpEntity, Map.class);




        return R.ok().put("content",responseEntity.getBody().get("content"));
    }
}
