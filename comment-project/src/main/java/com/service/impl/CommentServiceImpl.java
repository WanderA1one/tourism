package com.service.impl;

import com.dao.CommentRespository;
import com.pojo.Comment;
import com.pojo.CommentGG;
import com.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    CommentRespository commentRespository;
    @Override
    public List findyByScenicSpotId(String scenicSpotId) {
        List list = commentRespository.findByScenicSpotId(scenicSpotId);
        List<CommentGG> listGG = new ArrayList<CommentGG>();
        CommentGG cc = null;
        for (Object x:list
             ) {
            Comment c = (Comment)x;
            String format = new SimpleDateFormat("yyyy-MM-dd").format(c.getCommentTime());
            cc = new CommentGG();
            cc.setCommentDetails(c.getCommentDetails());
            cc.setCommentId(c.getCommentId());
            cc.setCommentTime(format);
            cc.setScenicSpotId(c.getScenicSpotId());
            listGG.add(cc);
        }
        Collections.reverse(listGG);//倒序
        return listGG;
    }

    @Override
    public void saveComment(String text, String scenicSpotId) {
        Comment comment = new Comment();
        comment.setCommentId(UUID.randomUUID().toString());
        comment.setCommentDetails(text);
        comment.setScenicSpotId(scenicSpotId);
        commentRespository.save(comment);
    }
}
