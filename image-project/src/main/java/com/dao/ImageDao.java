package com.dao;

import com.pojo.Img;
import com.pojo.ResultList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ImageDao extends JpaRepository<Img,String> {

    @Query(value = "select count(*) from Img",nativeQuery = true)
    Long findTotal();

    List findByScenicSpotId(String scenicSpotId);
}
