package com.controller;

import com.pojo.ResultList;
import com.service.ScenicSpotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequestMapping("/scenicSpot")
public class ScenicSpotController {
    @Autowired
    private ScenicSpotService scenicSpotService;

    @RequestMapping("/findAll/{page}/{size}")
    public ResultList getAllScenicSpotByPage(@PathVariable("page") Integer page,@PathVariable("size") Integer size){
        return scenicSpotService.findAllScenicSpots(page, size);
    }
}
