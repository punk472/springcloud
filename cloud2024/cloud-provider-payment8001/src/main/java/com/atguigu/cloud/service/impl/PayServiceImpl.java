package com.atguigu.cloud.service.impl;

import com.atguigu.cloud.service.PayService;
import com.atguigu.cloud.entities.Pay;
import com.atguigu.cloud.mapper.PayMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PayServiceImpl implements PayService {

    @Resource
    private PayMapper payMapper;

    @Override
    public int add(Pay pay) {
        return payMapper.insert(pay);
    }

    @Override
    public int delete(int id) {
        return payMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int update(Pay pay) {
        return payMapper.updateByPrimaryKeySelective(pay);
    }

    @Override
    public Pay getPayById(int id) {
        return payMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<Pay> getAllPay() {
        return payMapper.selectAll();
    }
}
