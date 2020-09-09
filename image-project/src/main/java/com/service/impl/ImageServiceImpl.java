package com.service.impl;

import com.dao.ImageDao;
import com.pojo.Img;
import com.pojo.ResultList;
import com.service.ImgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ImageServiceImpl implements ImgService {
    @Autowired
    private ImageDao imageDao;
    @Override
    public ResultList findAllImgs() {
        ResultList resultList = new ResultList();
        List<Img> all = imageDao.findAll();
        Long total = imageDao.findTotal();
        resultList.setTotal(total);
        resultList.setList(all);
        return resultList;
    }
}
