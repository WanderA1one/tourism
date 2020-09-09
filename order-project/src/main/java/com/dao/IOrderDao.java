package com.dao;

import com.pojo.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IOrderDao extends JpaRepository<Orders,String> {
    @Query(value = "select count(*) from Orders",nativeQuery = true)
    Integer findTotalOfOrders();
}
