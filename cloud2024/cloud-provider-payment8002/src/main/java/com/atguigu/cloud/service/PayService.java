package com.atguigu.cloud.service;

import com.atguigu.cloud.entities.Pay;

import java.util.List;

public interface PayService {
    int add(Pay pay);
    int delete (int id);
    int update (Pay pay);
    Pay getPayById (int id);
    List<Pay> getAllPay();
}
