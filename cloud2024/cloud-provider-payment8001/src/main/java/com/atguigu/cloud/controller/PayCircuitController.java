package com.atguigu.cloud.controller;

import com.atguigu.cloud.entities.Pay;
import com.atguigu.cloud.entities.PayDTO;
import com.atguigu.cloud.resp.ResultData;
import com.atguigu.cloud.service.PayService;
import jakarta.annotation.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

@RestController
public class PayCircuitController {
    @Resource
    private PayService payService;

    @GetMapping("/circuit/get/{id}")
    public ResultData<PayDTO> getPayById(@PathVariable("id") int id) {
        if (id<=0){
            throw new RuntimeException("id不能为负数");
        }
        if(id == 9999){
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        Pay payById = payService.getPayById(id);
        PayDTO payDTO = new PayDTO();
        BeanUtils.copyProperties(payById,payDTO);
        return ResultData.success(payDTO);
    }


    @GetMapping("/bulkhead/get/{id}")
    public ResultData<PayDTO> getPayById4Bulkhead(@PathVariable("id") int id) {
        if (id<=0){
            throw new RuntimeException("id不能为负数");
        }
        if(id >= 9999){
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        Pay payById = payService.getPayById(id);
        PayDTO payDTO = new PayDTO();
        BeanUtils.copyProperties(payById,payDTO);
        return ResultData.success(payDTO);
    }
}
