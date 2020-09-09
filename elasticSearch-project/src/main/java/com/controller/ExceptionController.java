package com.controller;

import com.pojo.ResultList;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;

@ControllerAdvice
@ResponseBody
public class ExceptionController {
    @ExceptionHandler(value = IOException.class)
    public ResultList ioEx(){
        ResultList resultList = new ResultList();
        resultList.setMessage("你家网不好");
        return resultList;
    }
}
