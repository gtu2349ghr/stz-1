package com.financial.Controller;

import com.financial.Vo.Result;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class aaaController {
    @RequestMapping("/test1")
    public Result aaa(){
        return Result.ok();
    }
}
