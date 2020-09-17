package com.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.Column;
import java.util.Date;

@Data
public class CustomerReq {
    private String customerName;//消费者姓名
    private Integer customerSex;//消费者性别
    private Integer customerAge;//消费者年龄
    private String customerCardId;//身份证号码
    private String customerPhone;//消费者电话
    private String customerPassword;//登录密码
    private String customerImg;//头像
    private String customerEmail;//邮箱
    private String customerCode;
}

