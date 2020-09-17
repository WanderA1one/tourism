package com.controller;

import com.pojo.ResultList;
import com.service.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@CrossOrigin(origins ="http://localhost:8080",allowCredentials = "true")
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private IOrderService iOrderService;

    @RequestMapping("/findTotals")
    public Integer findTotals(){
        return iOrderService.getTotalOfOrders();
    }
    @RequestMapping("/findAll")
    public ResultList findAll(){
        return iOrderService.findAll();
    }
    @RequestMapping("/findCustomerOrder")
    public ResultList findCustomerOrder(HttpServletRequest request){
        return iOrderService.findCustomerOrder(request);
    }
}
