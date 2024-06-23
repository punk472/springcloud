package com.atguigu.cloud.feign;

import com.atguigu.cloud.entities.PayDTO;
import com.atguigu.cloud.resp.ResultData;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(value = "cloud-payment-service")
public interface PayFeignApi {
    @PostMapping("/pay/add")
    ResultData<String> addPay(@RequestBody PayDTO payDTO);

    @DeleteMapping("/pay/del/{id}")
    ResultData<Integer> delPay(@PathVariable("id") int id);

    @PutMapping("/pay/update")
    ResultData<String> updatePay(@RequestBody PayDTO payDTO);

    @GetMapping("/pay/get/{id}")
    ResultData<PayDTO> getPay(@PathVariable("id") int id);

    @GetMapping("/circuit/get/{id}")
    ResultData<PayDTO> getCircuitPay(@PathVariable("id") int id);

    @GetMapping("/bulkhead/get/{id}")
    ResultData<PayDTO> getBulkHeadPay(@PathVariable("id") int id);
}
