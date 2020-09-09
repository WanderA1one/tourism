package com.pojo;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "role")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private Integer roleId;//角色编号
    @Column(name = "role_name")
    private String roleName;//角色名称
    @Column(name = "role_desc")
    private String roleDesc;//角色描述
}
