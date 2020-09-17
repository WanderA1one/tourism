package com.service;

import com.pojo.ResultList;

import java.util.List;

public interface ImgService {

    ResultList findAllImgs();

    List findByScenicSpotId(String ScenicSpotId);
}
