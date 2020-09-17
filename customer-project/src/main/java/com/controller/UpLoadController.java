package com.controller;

import com.util.QiniuUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@CrossOrigin(origins = "http://localhost:8080",allowCredentials = "true")
public class UpLoadController {
    @Autowired
    QiniuUtils qiniuUtils;

    @RequestMapping(value = "/upload",method = RequestMethod.POST)
    public String upload(@RequestParam("file")MultipartFile multipartFile){

        String originalFilename = multipartFile.getOriginalFilename();
        System.out.println(originalFilename);
        return qiniuUtils.upload(multipartFile);
    }
}
