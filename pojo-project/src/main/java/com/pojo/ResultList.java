package com.pojo;

import lombok.Data;

import java.util.List;
@Data
public class ResultList<T> {

    private List<T> list;
    private Long total;
    private String message;
}
