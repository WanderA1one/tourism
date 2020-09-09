package com.pojo;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Data
@Entity
@Table(name = "orders")
public class Orders {
    @Id
    @Column(name = "order_id")
    private String orderId;
    @Column(name = "order_time")
    private Date orderTime;
    @Column(name = "order_state")
    private Integer orderState;
}
