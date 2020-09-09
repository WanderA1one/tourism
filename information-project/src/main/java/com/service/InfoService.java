package com.service;

import com.pojo.Information;

import java.util.List;

public interface InfoService {

    Integer getTotals();

    List<Information> findAllInfo();

    List<Information> getByInfoState(Integer id);
}
