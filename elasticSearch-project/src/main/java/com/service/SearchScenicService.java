package com.service;

import com.pojo.ResultList;
import com.pojo.ScenicSpot;

import java.io.IOException;

public interface SearchScenicService {
    ResultList<ScenicSpot> searchScenic(String index) throws IOException;

}
