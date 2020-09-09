package com.dao;

import com.pojo.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IAdminDao extends JpaRepository<Admin,Integer> {

    Admin findAdminByAdminPhone(String phone);
}
