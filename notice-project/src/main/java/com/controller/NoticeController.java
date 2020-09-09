package com.controller;

import com.pojo.Notice;
import com.service.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.UUID;

@RestController
@CrossOrigin
@RequestMapping("/notice")
public class NoticeController {
    @Autowired
    private NoticeService noticeService;

    @RequestMapping("/addNotice")
    public Integer addNotice(@RequestBody Map map){
        String noticeTitle = (String) map.get("noticeTitle");
        String content = (String) map.get("noticeDetails");
        Notice notice = new Notice();
        String uuid = UUID.randomUUID().toString().replaceAll("-","");
        notice.setNoticeId(uuid);
        notice.setNoticeTitle(noticeTitle);
        notice.setNoticeDetails(content);
        try{
            noticeService.saveNotice(notice);
            return 1;
        }catch(Exception e){
            e.printStackTrace();
            return 0;
        }
    }
}
