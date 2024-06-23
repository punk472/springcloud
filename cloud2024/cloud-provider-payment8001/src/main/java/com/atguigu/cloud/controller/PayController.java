package com.atguigu.cloud.controller;

import com.atguigu.cloud.entities.Pay;
import com.atguigu.cloud.entities.PayDTO;
import com.atguigu.cloud.resp.ResultData;
import com.atguigu.cloud.service.PayService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/pay")
@Slf4j
@RefreshScope
@Tag(name = "支付微服务模块1",description = "支付CRUD")
public class PayController {
    @Resource
    private PayService payService;
    @Value("${server.port}")
    private String port;
    @Value("${person.name}")
    private String info;

    @PostMapping("/add")
    @Operation(summary = "新增支付",description = "新增支付，json串做传参")
    public ResultData<String> addPay(@RequestBody Pay pay) {
        int add = payService.add(pay);
        return ResultData.success("插入条数："+add);
    }

    @DeleteMapping(value = "/del/{id}")
    public ResultData<Integer> delPay(@PathVariable("id") int id) {
        return ResultData.success(payService.delete(id));
    }

    @PutMapping(value = "update")
    public ResultData<String> update(@RequestBody PayDTO payDTO){
        Pay pay = new Pay();
        BeanUtils.copyProperties(payDTO,pay);
        int update = payService.update(pay);
        return ResultData.success("修改条数："+update);
    }

    @GetMapping("get/{id}")
    public ResultData<PayDTO> getPayById(@PathVariable("id") int id) throws InterruptedException {
//        TimeUnit.SECONDS.sleep(10);
        Pay payById = payService.getPayById(id);
        PayDTO payDTO = new PayDTO();
        BeanUtils.copyProperties(payById,payDTO);
        return ResultData.success(payDTO);
    }

    @GetMapping("testException")
    public ResultData<String> testException(){
        int i =1/0;
        return ResultData.success("ok");
    }



    @GetMapping("/getPort")
    public void getPort(){
        System.out.println(port);
        System.out.println(info);
    }

}
