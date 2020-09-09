package com.service.impl;

import com.dao.InfoDao;
import com.pojo.Information;
import com.service.InfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class InfoServiceImpl implements InfoService {
    @Autowired
    private InfoDao infoDao;
    @Override
    public Integer getTotals() {
        return infoDao.getTotalsOfInfos();
    }

    @Override
    public List<Information> findAllInfo() {

//        PageRequest pageRequest = new PageRequest(page - 1, size);
//        Page<Information> all = infoDao.findAll(pageRequest);
//        long totalElements = all.getTotalElements();
//        List<Information> content = all.getContent();
//        ResultList resultList = new ResultList();
//        resultList.setList(content);
//        resultList.setTotal(totalElements);
        return infoDao.findAll();
    }

    @Override
    public List<Information> getByInfoState(Integer id) {
        return infoDao.findByInfoState(id);
    }

}
