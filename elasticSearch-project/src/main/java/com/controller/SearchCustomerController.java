package com.controller;

import com.pojo.ResultList;
import com.service.SearchCustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@CrossOrigin
@RequestMapping("/es-customer")
public class SearchCustomerController {

    @Autowired
    private SearchCustomerService searchService;

    @RequestMapping("/searchCustomer/{index}/{page}/{size}")
    public ResultList searchCustomer(@PathVariable("index")String index,@PathVariable("page")Integer page,@PathVariable("size")Integer size) throws IOException {
        return searchService.searchCustomer(index,page,size);
    }
}
