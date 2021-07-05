package com.example.demo.controller;


import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.example.demo.entity.Gitee;
import com.example.demo.util.R;
import org.apache.commons.codec.binary.Base64;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.example.demo.controller.BaseController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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
@RestController
@RequestMapping("/gitee")
public class GiteeController extends BaseController {




    @RequestMapping(value = "/saveImg",method = RequestMethod.POST)
    public R saveImg(@RequestParam("img") MultipartFile content, @RequestParam("fileType") String fileType) throws IOException {


        Gitee gitee = zGiteeService.getOne(Wrappers.<Gitee>lambdaQuery()
                .eq(Gitee::getEnable, 1));
        Date date = new Date();
        if(null==gitee)
            return R.error("没有配置图床");
        gitee.setMessage("zxl.link的用户："+getUser().getName());
        //设置内容
        gitee.setContent(Base64.encodeBase64String(content.getBytes()));
        Calendar calendar = Calendar.getInstance();
        //userId/year/mounth/day/timestamp.fileType

        gitee.setPath(getUserId()+"/" + calendar.get(Calendar.YEAR) + "/" + (calendar.get(Calendar.MONTH)+1) + "/" + calendar.get(Calendar.DAY_OF_MONTH) + "/" + date.getTime()+"."+fileType);


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

