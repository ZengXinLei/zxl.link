package com.example.demo.controller;


import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.example.demo.entity.Permission;
import com.example.demo.entity.PermissionRole;
import com.example.demo.entity.Role;
import com.example.demo.entity.User;
import com.example.demo.entity.vo.Code;
import com.example.demo.util.R;
import com.example.demo.util.VerifyCodeUtils;
import org.apache.tomcat.util.http.parser.Authorization;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import com.example.demo.controller.BaseController;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.websocket.server.PathParam;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zxl
 * @since 2021-07-02
 */
@RestController
@RequestMapping("/user")
public class UserController extends BaseController {


//    @PreAuthorize("hasRole('ROLE_USER')")
    @RequestMapping(value = "/baseInfo",method = RequestMethod.POST)
    public R baseInfo(){

        return R.ok().put("baseInfo",getUser());
    }


    @RequestMapping("/getCode/{uuid}")
    public void getCode(HttpServletResponse response, @PathVariable("uuid") String uuid){
        Code code = new Code();
        code.setCode(VerifyCodeUtils.getSecurityCode());
        code.setUuid(uuid);

        redisUtils.set(uuid,code.getCode());
        BufferedImage image = VerifyCodeUtils.createImage(code.getCode());
        ServletOutputStream outputStream = null;
        try {
            outputStream = response.getOutputStream();
            ImageIO.write(image,"png",outputStream);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

