package com.example.demo.controller;


import com.example.demo.util.R;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.example.demo.controller.BaseController;

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


    @RequestMapping(value = "/t",method = RequestMethod.POST)
    public R t(){
        return R.ok("t");
    }
}

