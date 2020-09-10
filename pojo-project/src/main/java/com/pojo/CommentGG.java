package com.pojo;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Data

public class CommentGG {

    private String commentId;//评论编号

    private String commentDetails;//评论内容

    private String commentTime;//评论时间

    private String scenicSpotId;//景点编号
}
