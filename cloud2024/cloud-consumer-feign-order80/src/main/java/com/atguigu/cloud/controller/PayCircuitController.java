package com.atguigu.cloud.controller;

import com.atguigu.cloud.entities.PayDTO;
import com.atguigu.cloud.feign.PayFeignApi;
import com.atguigu.cloud.resp.ResultData;
import com.atguigu.cloud.resp.ReturnCodeEnum;
import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

@RestController
public class PayCircuitController {

    private SimpleDateFormat simpleDateFormat =new SimpleDateFormat("HH:mm:ss");


    @Resource
    private PayFeignApi payFeignApi;

    @GetMapping("/circuit/get/{id}")
    @CircuitBreaker(name = "cloud-payment-service1",fallbackMethod = "myCircuitBreakFallback")
    public ResultData get4Circuit(@PathVariable("id") int id) {
        System.out.println("开始请求时间："+simpleDateFormat.format(new Date()));
        ResultData<PayDTO> pay = payFeignApi.getCircuitPay(id);
        System.out.println("结束请求时间："+simpleDateFormat.format(new Date()));
        return pay;
    }

    @GetMapping("/bulkhead/get/{id}")
    @Bulkhead(name = "cloud-payment-service2",fallbackMethod = "myBulkHeadFallback",type = Bulkhead.Type.SEMAPHORE)
    public ResultData get4BulkHead(@PathVariable("id") int id) {
        System.out.println("开始请求时间："+simpleDateFormat.format(new Date()));
        ResultData<PayDTO> pay = payFeignApi.getBulkHeadPay(id);
        System.out.println("结束请求时间："+simpleDateFormat.format(new Date()));
        return pay;
    }

    @GetMapping("fixedThreadPoolBulkhead/{id}")
    @Bulkhead(name = "cloud-payment-service3",fallbackMethod = "myFixedThreadPoolBulkHeadFallback",type = Bulkhead.Type.THREADPOOL)
    public CompletableFuture<String> myFixedThreadPoolBulkhead(@PathVariable(value = "id")String id) {
        return CompletableFuture.supplyAsync(new Supplier<String>() {
            @Override
            public String get() {
                System.out.println(Thread.currentThread().getName()+"开始id:"+id);
                try {
                    TimeUnit.SECONDS.sleep(5);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.println(Thread.currentThread().getName()+"结束id  "+id);
                return "finish!";
            }
        });
    }

    @GetMapping("/rateLimiter/{id}")
    @RateLimiter(name = "cloud-payment-service4",fallbackMethod = "myRateLimiterFallback")
    public ResultData rateLimiter(@PathVariable(value = "id")String id) throws InterruptedException {
        System.out.println("开始执行");
        TimeUnit.SECONDS.sleep(5);
        System.out.println("结束执行");
        return ResultData.success("ok!");
    }

    public ResultData myCircuitBreakFallback(Throwable e){
        e.printStackTrace();
//        return "系统繁忙, 请稍后重试...;";
        return ResultData.success("降级...系统繁忙, 请稍后重试...");
    }
    public ResultData myBulkHeadFallback(Throwable e){
        e.printStackTrace();
//        return "系统繁忙, 请稍后重试...;";
        return ResultData.success("bulkHead...系统繁忙, 请稍后重试...");
    }

    public CompletableFuture<String> myFixedThreadPoolBulkHeadFallback(Throwable e){
        e.printStackTrace();
//        return "系统繁忙, 请稍后重试...;";
        return CompletableFuture.supplyAsync(new Supplier<String>() {
            @Override
            public String get() {
                return "fixedThreadPoolBulkhead系统繁忙, 请稍后重试...";
            }
        });
    }

    public ResultData myRateLimiterFallback(Throwable throwable){

        return ResultData.fail("500","您被限流了");
    }


}
