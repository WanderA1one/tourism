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
@Table(name="customer")
public class Customer {
    @Id
    @Column(name = "customer_id")
    private String customerId;//消费者编号
    @Column(name = "customer_name")
    private String customerName;//消费者姓名
    @Column(name = "customer_sex")
    private Integer customerSex;//消费者性别
    @Column(name = "customer_age")
    private Integer customerAge;//消费者年龄
    @Column(name = "customer_card_id")
    private String customerCardId;//身份证号码
    @Column(name = "customer_phone")
    private String customerPhone;//消费者电话
    @Column(name = "customer_role")
    private Integer customerRole;//消费者角色(会员，非会员)
    @Column(name = "customer_password")
    private String customerPassword;//登录密码
    @Column(name = "customer_time")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date customerTime;//注册时间
    @Column(name = "customerImg")
    private String customerImg;//头像
    @Column(name = "customer_email")
    private String customerEmail;
}
