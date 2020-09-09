package com.dao;

import com.pojo.Img;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ImageDao extends JpaRepository<Img,String> {

    @Query(value = "select count(*) from Img",nativeQuery = true)
    Long findTotal();
}
