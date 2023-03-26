package com.financial.springboot.cons.utils;

import java.util.HashMap;

public class Result extends HashMap<String,Object>{
    public static Result success(String msg){
        Result map = new Result();
        map.put("code",1);
        map.put("message",msg);
        return map;
    }
    public static Result success(String msg,String messageCode){
        Result map = new Result();
        map.put("code",1);
        map.put("message",msg);
        map.put("data",messageCode);
        return map;
    }
    public  static   Result erro(String msg){
        Result map = new Result();
        map.put("code",2);
        map.put("message",msg);
        return map;
    }
}
