package com.demo111.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class indexController {
    @RequestMapping("index")
    public String index(){
        return "index";
    }
    @RequestMapping("/")
    public String local(){
        return "index";
    }
    @RequestMapping("Wxppp")
    public String Wxppp(){
        return "Wx";
    }
    @RequestMapping("Zfb")
    public String Zfb(){
        return "Zfb";
    }
}
