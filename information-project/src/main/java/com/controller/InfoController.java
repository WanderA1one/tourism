package com.controller;

import com.pojo.Information;
import com.pojo.ResultList;
import com.service.InfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/info")
public class InfoController {
    @Autowired
    private InfoService infoService;

    @RequestMapping("/findAll")
    public List<Information> getAllInfo(){
        return infoService.findAllInfo();
    }

    @RequestMapping("/getTotalsOfInfo")
    public Integer getTotalsOfInfo(){
        return infoService.getTotals();
    }

    @RequestMapping("/findByInfoState/{id}")
    public List<Information> getAllByInfoState(@PathVariable("id") Integer id){
        return infoService.getByInfoState(id);
    }
}

