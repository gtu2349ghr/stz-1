package com.bjpowernode.Controller;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
@Controller
public class aaaController {
    @RequestMapping("/")
    public String aaa(){
        return "toRecharge";
    }

    @RequestMapping("/return")
    public String return1(){
        return "toRecharge";
    }
}
