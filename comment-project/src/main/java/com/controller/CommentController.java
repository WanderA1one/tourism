package com.controller;

import com.pojo.ResultList;
import com.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.xml.transform.Result;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:8080",allowCredentials = "true")
@RequestMapping("/comment")
public class CommentController {
    @Autowired
    CommentService commentService;
    @RequestMapping("/findByScenicSpotId/{scenicSpotId}")
    public ResultList findyByScenicSpotId(@PathVariable("scenicSpotId") String scenicSpotId){
        ResultList resultList = new ResultList();
        List list = commentService.findyByScenicSpotId(scenicSpotId);
        resultList.setList(list);
        return resultList;
    }

    @RequestMapping("/saveComment")
    public void saveComment(@RequestBody Map map){
        String text = (String) map.get("text");
        String scenicSpotId = (String)map.get("scenicSpotId");
        commentService.saveComment(text,scenicSpotId);
    }
}
