package com.example.demo.controller;


import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.example.demo.entity.*;
import com.example.demo.entity.vo.Code;
import com.example.demo.util.R;
import com.example.demo.util.VerifyCodeUtils;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.tomcat.util.http.parser.Authorization;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import com.example.demo.controller.BaseController;
import org.thymeleaf.context.Context;

import javax.imageio.ImageIO;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.websocket.server.PathParam;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
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

        redisUtils.set(uuid,code.getCode(),60);

        BufferedImage image = VerifyCodeUtils.createImage(code.getCode());
        ServletOutputStream outputStream = null;
        try {
            outputStream = response.getOutputStream();
            ImageIO.write(image,"png",outputStream);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @RequestMapping(value = "/register",method = RequestMethod.POST)
    public R register(@RequestParam Map<String,Object> map) throws InvocationTargetException, IllegalAccessException, InstantiationException {

        User user = new User();
        User one = zUserService.getOne(Wrappers.<User>lambdaQuery().eq(User::getName, map.get("name")));
        if(one!=null){
            return R.error("用户名已存在");
        }
        one= zUserService.getOne(Wrappers.<User>lambdaQuery().eq(User::getEmail, map.get("email")));
        if(one!=null){
            return R.error("邮箱已被注册");

        }
//        BeanUtils.populate(user,map);
        user.setName(map.get("name").toString());
        user.setEmail(map.get("email").toString());
        user.setPassword(map.get("password").toString());



        String s = UUID.randomUUID().toString();
        redisUtils.set(s,user,60*60*24*2);

        return sendEmail(user.getName(),s);

    }

    @Transactional(rollbackFor = Exception.class)
    @RequestMapping("/active")
    public R active(@RequestParam("uuid") String uuid){

        User user = redisUtils.get(uuid, User.class);

        if(user!=null){
            redisUtils.delete(uuid);
            zUserService.save(user);
            UserRole userRole = new UserRole();
            userRole.setUid(user.getId());
            userRole.setRid(1);
            zUserRoleService.save(userRole);
            return R.ok("激活成功");

        }
        return R.error("已失效");

    }


    public R sendEmail(String name,String uuid)  {

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = null;
        try {
            helper = new MimeMessageHelper(mimeMessage, true);
            helper.setSubject("这是一封测试邮件");
            helper.setFrom("2496971960@qq.com");
            helper.setTo("1599484837@qq.com");
//        helper.setCc("37xxxxx37@qq.com");
//        helper.setBcc("14xxxxx098@qq.com");
            helper.setSentDate(new Date());
            // 这里引入的是Template的Context
            Context context = new Context();
            // 设置模板中的变量
            context.setVariable("username", name);
            context.setVariable("url","http://localhost:9528/active?uuid="+uuid);
            // 第一个参数为模板的名称
            String process = templateEngine.process("createUser.html", context);
            // 第二个参数true表示这是一个html文本
            helper.setText(process,true);
            javaMailSender.send(mimeMessage);

        } catch (MessagingException e) {
            e.printStackTrace();
            return R.error("发送邮件失败");
        }

        return R.ok();


    }

}

