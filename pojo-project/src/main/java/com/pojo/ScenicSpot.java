package com.pojo;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Data
@Entity
@Table(name = "scenic_spot")
public class ScenicSpot {
    @Id
    @Column(name = "scenic_spot_id")
    private String scenicSpotId;//景点编号
    @Column(name = "scenic_spot_name")
    private String scenicSpotName;//景点名称
    @Column(name = "scenic_spot_addr")
    private String scenicSpotAddr;//景点地址
    @Column(name = "scenic_spot_opentime")
    private String scenicSpotOpentime;//景点开放时间
    @Column(name = "scenic_spot_closetime")
    private String scenicSpotCloseTime;//景点关闭时间
    @Column(name = "scenic_spot_score")
    private Double scenicSpotScore;//景点评分
    @Column(name = "scenic_spot_desc")
    private String scenicSpotDesc;//景点描述
    @Column(name = "scenic_spot_phone")
    private String scenicSpotPhone;//景点电话
    @Column(name="scenic_sqot_price")
    private String scenicSqotPrice;//景点价格
    @Column(name="scenic_sqot_photo")
    private String scenicSqotPhoto;//景点图片
    @Column(name = "scenic_sqot_x")
    private String scenicSqotX;//景点X轴
    @Column(name = "scenic_sqot_y")
    private String scenicSqotY;//景点Y轴

}
