package com.service.impl;

import com.dao.NoticeDao;
import com.pojo.Notice;
import com.service.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NoticeServiceImpl implements NoticeService {
    @Autowired
    private NoticeDao noticeDao;
    @Override
    public Notice saveNotice(Notice notice) {
        return noticeDao.saveAndFlush(notice);
    }
}
