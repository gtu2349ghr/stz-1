package com.bjpowernode.Vo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.HashMap;
import java.util.Map;

@Data
@Accessors(chain = true)
public class Result {
    private  String code;//响应码
    private  String mag;//响应消息
    private Map<String,Object> data =new HashMap<String,Object>();//响应数据
    //然后给他成功和失败的方法
    public  static  Result ok(){
        Result result = new Result();
        result.setCode("0");
        result.setMag("成功");
        return result;
    }
    public  static  Result erro(){
        Result result = new Result();
        result.setCode("-1");
        result.setMag("失败");
        return result;
    }
    //重写ok，返回数据
    public  Result data(String key, Object value){
       this.data.put(key,value);
        return this;
    }
}
