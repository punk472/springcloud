package com.atguigu.cloud.exp;

import com.atguigu.cloud.resp.ResultData;
import com.atguigu.cloud.resp.ReturnCodeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.text.SimpleDateFormat;
import java.util.Date;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    private SimpleDateFormat simpleDateFormat =new SimpleDateFormat("HH:mm:ss");
    @ExceptionHandler(value = Exception.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResultData<String> exceptionHandler(Exception e) {
        System.out.println("进入全局异常处理器"+e.getMessage());
        log.error(e.getMessage());
        e.printStackTrace();
        System.out.println("异常时间："+simpleDateFormat.format(new Date()));
//        throw new RuntimeException(e);
        return ResultData.fail(ReturnCodeEnum.RC500.getCode(), e.getMessage());
    }
}
