package com.pojo;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;
@Data
@Entity
@Table(name = "comment")
public class Comment {
    @Id
    @Column(name = "comment_id")
    private String commentId;//评论编号
    @Column(name = "comment_details")
    private String commentDetails;//评论内容
    @Column(name = "comment_time")
    private Date commentTime;//评论时间
}
