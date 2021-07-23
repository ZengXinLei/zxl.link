package com.example.demo.filter;

import com.example.demo.util.R;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @className: com.example.demo.filter.ZxlExceptionHandler
 * @description: TODO
 * @author: zxl
 * @create: 2021-07-02 19:08
 */
@RestControllerAdvice
public class ZxlExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public R ErrorHandle(Exception e){
        e.printStackTrace();


        if(e instanceof org.springframework.security.access.AccessDeniedException)
            return R.error("不允许访问");

        if(e instanceof org.springframework.web.multipart.MaxUploadSizeExceededException)
            return R.error(e.getCause().getMessage());
        return R.error();
    }
}
