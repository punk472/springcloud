package com.atguigu.cloud.controller;

import com.atguigu.cloud.entities.PayDTO;
import com.atguigu.cloud.resp.ResultData;
import jakarta.annotation.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/consumer")
public class OrderController {
//    private static final String PaymentSrvURL = "http://localhost:8001/pay";
    private static final String PaymentSrvURL = "http://cloud-payment-service/pay";

    @Resource
    private RestTemplate restTemplate;

    @GetMapping("/get/{id}")
    public ResultData<PayDTO> payDTOResultData(@PathVariable(value = "id") String id){
        return restTemplate.getForObject(PaymentSrvURL + "/get/" + id, ResultData.class);
    }

    @PostMapping("/add")
    public ResultData<String> add(@RequestBody PayDTO payDTO){
        return restTemplate.postForObject(PaymentSrvURL + "/add", payDTO, ResultData.class);
    }

    @PutMapping("/update")
    public ResultData<String> update(@RequestBody PayDTO payDTO){
        HttpEntity<PayDTO> httpEntity = new HttpEntity<>(payDTO);
        ResponseEntity<ResultData> exchange = restTemplate.exchange(PaymentSrvURL + "/update", HttpMethod.PUT, httpEntity, ResultData.class);
        return exchange.getBody();
    }

    @DeleteMapping("/del/{id}")
    public ResultData<String> del(@PathVariable(value = "id") String id){
        ResponseEntity<ResultData> exchange = restTemplate.exchange(PaymentSrvURL + "/del/" + id, HttpMethod.DELETE, null, ResultData.class);
        return exchange.getBody();
    }
}
