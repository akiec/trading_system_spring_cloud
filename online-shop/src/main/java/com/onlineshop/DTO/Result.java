package com.onlineshop.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor

//通用返回类，封装信息，对象进行返回
public class Result {
    private Boolean success;
    private String errormessage;
    private Object data;
    private Long total;



    public static Result success() {return new Result(true,null,null,null);}
    public static Result success(Object data) {return new Result(true,null,data,null);}
    public static Result success(List<?> data,Long total) {return new Result(true,null,data,total);}
    public static Result error(String message) {return new Result(false,message,null,null);}
}
