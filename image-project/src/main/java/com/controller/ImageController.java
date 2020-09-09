package com.controller;

import com.pojo.ResultList;
import com.service.ImgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequestMapping("/img")
public class ImageController {
    @Autowired
    private ImgService imgService;
    @RequestMapping("/findAll")
    public ResultList findAllImgs(){
        return imgService.findAllImgs();
    }
}
