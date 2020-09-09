package com.dao;

import com.pojo.Customer;
import com.pojo.ResultList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ICustomerDao extends JpaRepository<Customer,String> {

    ResultList findCustomersByCustomerNameOrCustomerCardIdLike(String customerName,String customerCardId);
    @Modifying
    @Transactional
    @Query("delete from Customer c where c.customerId in (:ids)")
    void deleteMoreCustomers(@Param("ids") List<String> ids);
    @Query(value = "select count(*) from Customer",nativeQuery = true)
    Integer getCountOfCustomers();
}
