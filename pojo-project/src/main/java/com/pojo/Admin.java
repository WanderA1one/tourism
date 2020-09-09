package com.pojo;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "admin")
public class Admin {
    @Id
    @Column(name = "admin_id")
    private Integer adminId;//管理员编号
    @Column(name = "admin_name")
    private String adminName;//管理员姓名
    @Column(name = "admin_sex")
    private Integer adminSex;//管理员性别
    @Column(name = "admin_phone")
    private String adminPhone;//管理员电话
    @Column(name = "admin_addr")
    private String adminAddr;//管理员地址
    @Column(name = "admin_password")
    private String adminPassword;//登陆密码
}
