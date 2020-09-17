package com.service.impl;

import com.dao.ScenicSpotDao;
import com.pojo.ResultList;
import com.pojo.ScenicSpot;
import com.service.ScenicSpotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScenicSpotServiceImpl implements ScenicSpotService {
    @Autowired
    private ScenicSpotDao scenicSpotDao;

    @Override
    public ResultList findAllScenic() {
        List<ScenicSpot> all = scenicSpotDao.findAll();

        ResultList resultList = new ResultList();

        resultList.setList(all);

        return resultList;
    }

    @Override
    public ResultList<ScenicSpot> findAllScenicSpots(Integer page, Integer size) {

        PageRequest pageRequest = new PageRequest(page - 1, size);
        Page<ScenicSpot> all = scenicSpotDao.findAll(pageRequest);
        List<ScenicSpot> content = all.getContent();
        long totalElements = all.getTotalElements();
        ResultList resultList = new ResultList();
        resultList.setList(content);
        resultList.setTotal(totalElements);
        return resultList;
    }

    @Override
    public ScenicSpot finyByScenicSpotId(String scenicSpotId) {
        return scenicSpotDao.findById(scenicSpotId).get();
    }
}
