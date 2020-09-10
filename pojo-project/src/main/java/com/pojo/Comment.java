package com.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
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
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date commentTime;//评论时间
    @Column(name = "scenic_spot_id")
    private String scenicSpotId;//景点编号
}
