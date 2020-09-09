package com.pojo;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "notice")
public class Notice {
    @Id
    @Column(name = "notice_id")
    private String noticeId;//通知公告编号
    @Column(name = "notice_title")
    private String noticeTitle;//通知公告标题
    @Column(name = "notice_details")
    private String noticeDetails;//通知公告内容
    @Column(name = "notice_release")
    private String noticeRelease;//日期
}
