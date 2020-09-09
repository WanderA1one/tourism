package com.pojo;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "information")
public class Information {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "info_id")
    private Integer infoId;//消息序号
    @Column(name = "info_details")
    private String infoDetails;//消息内容
    @Column(name = "info_time")
    private Date infoTime;//发布消息时间
    @Column(name = "info_state")
    private Integer infoState;//状态（已读和未读）
}
