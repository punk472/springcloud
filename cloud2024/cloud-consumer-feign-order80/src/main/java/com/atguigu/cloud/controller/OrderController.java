package com.atguigu.cloud.controller;

import com.atguigu.cloud.entities.PayDTO;
import com.atguigu.cloud.feign.PayFeignApi;
import com.atguigu.cloud.resp.ResultData;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;

@RequestMapping("/consumer")
@RestController
public class OrderController {

    private SimpleDateFormat simpleDateFormat =new SimpleDateFormat("HH:mm:ss");


    @Resource
    private PayFeignApi payFeignApi;
    @PostMapping("/add")
    public ResultData<String> add(@RequestBody PayDTO payDTO) {
        return payFeignApi.addPay(payDTO);
    }
    @DeleteMapping("/del/{id}")
    public ResultData<Integer> del(@PathVariable("id") int id) {
        return payFeignApi.delPay(id);
    }

    @PutMapping("/update")
    public ResultData<String> update(@RequestBody PayDTO payDTO) {
        return payFeignApi.updatePay(payDTO);
    }

    @GetMapping("/get/{id}")
    public ResultData<PayDTO> get(@PathVariable("id") int id) {
        System.out.println("开始请求时间："+simpleDateFormat.format(new Date()));
        ResultData<PayDTO> pay = payFeignApi.getPay(id);
        System.out.println("结束请求时间："+simpleDateFormat.format(new Date()));
        return pay;

    }

}
