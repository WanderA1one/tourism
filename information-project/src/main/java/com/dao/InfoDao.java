package com.dao;

import com.pojo.Information;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface InfoDao extends JpaRepository<Information,Integer> {
    @Query(value = "select count(*) from Information",nativeQuery = true)
    Integer getTotalsOfInfos();

    List<Information> findByInfoState(Integer id);
}
