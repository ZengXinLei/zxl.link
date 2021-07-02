package com.example.demo.controller;


import com.example.demo.util.R;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;

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

    @PreAuthorize(value = "hasRole('ROLE_USER')")
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public R upload(@RequestParam("img") MultipartFile file) {
        if (file.getSize() >= 5 * 1024 * 1024) {
            return R.ok(500, "图片大小不能超过5M");
        }
        String filename = new Date().getTime() + "_" + file.getOriginalFilename();

        File dest = new File(path + "/" + filename);
        try {
            file.transferTo(dest);
            return R.ok().put("fileName", filename).put("pre","article/getImg?fileName=");
        } catch (IOException e) {
            return R.error();

        }

//        localhost:8080/article/getImg?fileName=1625230365217_image.png
//        http://localhost:8080/article/getImg?fileName=1625230365217_image.png
//        return R.ok();
    }

    @RequestMapping(value = "/getImg", method = RequestMethod.GET,produces = MediaType.IMAGE_JPEG_VALUE)
    public byte[] getImg(@RequestParam("fileName") String fileName)
    {
        File file = new File(path+"/"+fileName);
        byte[] bytes = new byte[0];
        try
        {
            FileInputStream fileInputStream = new FileInputStream(file);
            bytes = new byte[fileInputStream.available()];
            fileInputStream.read(bytes,0,fileInputStream.available());
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return bytes;
    }
}

