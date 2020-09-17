package com.controller;

import com.pojo.Img;
import com.pojo.ResultList;
import com.service.ImgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:8080", allowCredentials = "true")
@RequestMapping("/img")
public class ImageController {
    @Autowired
    private ImgService imgService;

    @RequestMapping("/findAll")
    public ResultList findAllImgs() {
        return imgService.findAllImgs();
    }

    @RequestMapping("/findByScenicSpotId")
    public String[] findByScenicSpotId(@RequestBody Map map) {
        List list = imgService.findByScenicSpotId((String) map.get("ScenicSpotId"));
        String[] s = new String[list.size()];
            for (int i = 0; i < s.length; i++) {
                Img a = (Img)list.get(i);
                s[i] =a.getImgUrl();
            }
        return s;
    }
}
