package com.atguigu.cloud.config;

import feign.Logger;
import feign.Retryer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenfeignConfig {

    @Bean
    public Retryer retryer(){
        return  Retryer.NEVER_RETRY;
//        return new Retryer.Default(5000,1,3);
    }
    @Bean
    public Logger.Level loggerLevel(){
        return  Logger.Level.FULL;
    }
}
