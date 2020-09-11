package com.pojo;

import lombok.Data;
import java.util.Date;

@Data
public class OrderRedis {
    private String orderId;
    private String orderOccurrenceTime;
    private String orderBookingTime;
    private Integer orderState;
    private String scenicSpotName;
    private String scenicSpotAddr;
    private String scenicSpotPhone;
    private String scenicSqotPhoto;
    private Double totalPrice;
}
