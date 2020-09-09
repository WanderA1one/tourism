package com.pojo;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Table(name = "img")
@Entity
public class Img {
    @Id
    @Column(name = "img_id")
    private String imgId;//图片编号
    @Column(name = "img_url")
    private String imgUrl;//图片路径
    @Column(name = "img_title")
    private String imgTitle;//图片标题
}
