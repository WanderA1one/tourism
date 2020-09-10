package com.dao;

import com.pojo.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRespository extends JpaRepository<Comment,String> {
    List findByScenicSpotId(String scenicSpotId);
}
