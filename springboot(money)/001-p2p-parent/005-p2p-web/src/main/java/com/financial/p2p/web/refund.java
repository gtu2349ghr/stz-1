package com.financial.p2p.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class refund {
    @RequestMapping("refund")
    public String refundO(){
        return "refuned";
    }

}
