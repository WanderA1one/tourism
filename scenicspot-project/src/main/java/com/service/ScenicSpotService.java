package com.service;

import com.pojo.ResultList;
import com.pojo.ScenicSpot;

public interface ScenicSpotService {
    ResultList findAllScenic();
    ResultList<ScenicSpot> findAllScenicSpots(Integer page,Integer size);

    ScenicSpot finyByScenicSpotId(String scenicSpotId);
}
