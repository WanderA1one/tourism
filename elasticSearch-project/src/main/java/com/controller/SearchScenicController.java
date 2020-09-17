package com.controller;

import com.pojo.ResultList;
import com.service.SearchScenicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/es-scenic")
//
@CrossOrigin(origins = "http://localhost:8060",allowCredentials = "true")
public class SearchScenicController {

    @Autowired
    private SearchScenicService searchScenicService;

    @RequestMapping("/searchScenic/{index}")
    public ResultList searchScenic(@PathVariable("index")String index) throws IOException {
        return searchScenicService.searchScenic(index);
    }

//    @RequestMapping("/searchScenic/{index}/{page}/{size}")
//    public ResultList searchScenic(@PathVariable("index")String index, @PathVariable("page")Integer page, @PathVariable("size")Integer size) throws IOException {
//        return searchScenicService.searchScenic(index,page,size);
//    }

}
