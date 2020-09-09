package com.controller;

import com.pojo.Admin;
import com.service.IAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    IAdminService iAdminService;
    @RequestMapping("/adminLogin")
    public String adminLogin(@RequestBody Admin admin){
        return iAdminService.adminLogin(admin);
    }
}
