package com.service.impl;

import com.dao.IOrderDao;
import com.service.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IOrderServiceImpl implements IOrderService {
    @Autowired
    private IOrderDao iOrderDao;
    @Override
    public Integer getTotalOfOrders() {
        return iOrderDao.findTotalOfOrders();
    }
}
