package com.dao;

import com.pojo.Notice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoticeDao extends JpaRepository<Notice,String> {
}
