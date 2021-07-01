package com.example.demo.controller;


import com.example.demo.util.R;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

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
public class UserController {


    @RequestMapping("/tt")
    public R tt(){
        return R.ok();
    }
}

