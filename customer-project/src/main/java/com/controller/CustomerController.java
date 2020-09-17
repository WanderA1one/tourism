package com.controller;

import com.pojo.Customer;
import com.pojo.CustomerReq;
import com.pojo.ResultList;
import com.service.ICustomerService;
import com.util.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;


@RestController
@CrossOrigin(origins = "http://localhost:8080",allowCredentials = "true")
@RequestMapping("/customer")
public class CustomerController {
    @Autowired
    TokenUtils tokenUtils;
    @Autowired
    RedisTemplate redisTemplate;
    @Autowired
    ICustomerService iCustomerService;
    @RequestMapping("/check")
    public Customer check(HttpServletRequest request){
        try {
            Customer customerByToken = tokenUtils.findCustomerByToken(request);
            return customerByToken;
        }catch (Exception e){
            return null;
        }
    }

    @RequestMapping("/customerLogin")
    public String customerLogin(@RequestBody Customer customer){
        return iCustomerService.customerLogin(customer);
    }

    @RequestMapping("/sendMail")
    public String sendMail(@RequestBody Map map){
        Object email = map.get("email");
        return iCustomerService.sendMail(email.toString());
    }

    @RequestMapping("/registry")
    public String registry(@RequestBody CustomerReq customerReq){
        return iCustomerService.registry(customerReq);
    }


    @RequestMapping(value = "/findAllCustomers/{page}/{size}", method = RequestMethod.GET)
    public ResultList findAllCustomers(@PathVariable("page") Integer page,@PathVariable("size") Integer size){
        return iCustomerService.findAllCustomers(page, size);
    }

    @RequestMapping("/deleteCustomer")
    public Integer deleteCustomerByCustomerId(@RequestBody Map map){
        String customerId = (String)map.get("customerId");
        return iCustomerService.deleteByCustomerId(customerId);
    }

    @RequestMapping("/saveCustomer")
    public Integer saveCustomer(@RequestBody Customer customer){
        if(iCustomerService.saveCustomer(customer) != null){
            return 1;
        }
        return 0;
    }
    @RequestMapping("/findByNameOrCardId")
    public ResultList findByNameOrCardId(@RequestBody Map map){
        String customerName = (String) map.get("customerName");
        String customerCardId = (String) map.get("customerCardId");
        return iCustomerService.findByNameOrCardId("%"+customerName+"%","%"+customerCardId+"%");
    }
    @RequestMapping("/addCustomer")
    public Integer addCustomer(@RequestBody Customer customer){
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        customer.setCustomerId(uuid);
        try {
            iCustomerService.addCustomer(customer);
            return 1;
        }catch(Exception e){
            e.printStackTrace();
            return 0;
        }
    }
    @RequestMapping("/deleteMore")
    public String deleteMoreCustomers(@RequestBody Map map){
        String str = (String) map.get("str");
        String[] s = str.trim().split(" ");
        List<String> list = new ArrayList<>();
        for(String customerId : s){
            list.add(customerId);
        }
        try {
            iCustomerService.deleteMoreCustomers(list);
            return "success";
        }catch(Exception e){
            e.printStackTrace();
            return "failed";
        }
    }
    @RequestMapping("/getTotalsOfCustomers")
    public Integer getAllTotals(){
        return iCustomerService.getTotals();
    }
}
