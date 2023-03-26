package com.financial.p2p.web;

import com.alibaba.dubbo.config.annotation.Reference;
import com.financial.p2p.model.LoanInfo;
import com.financial.p2p.server.loan.BidInfoService;
import com.financial.p2p.server.loan.LoanInfoService;
import com.financial.p2p.server.user.UserService;
import com.financial.springboot.cons.Constans;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.List;

@Controller
public class IndexController {
    @Reference(interfaceClass = LoanInfoService.class,version = "1.0.0",check =false,timeout = 15000)
    LoanInfoService loanInfoService;
    @Reference(interfaceClass = UserService.class,version = "1.0.0",check = false,timeout = 15000)
    private  UserService userService;
    @Reference(interfaceClass = BidInfoService.class,version = "1.0.0",check = false,timeout = 15000)
    private BidInfoService bidInfoService;
    @RequestMapping("/index")
    public  String toindex(Model model){
        /**
         * 查询历史年话收益率
         */
        Double  HistroyAvgRate= loanInfoService.queryHistryAvgRate();
    model.addAttribute(Constans.HISTRY_AVG_RATE,HistroyAvgRate);
        /**
         * 查询平台总用户量
         */
        Integer countUser = userService.querryCountUser();
        model.addAttribute(Constans.COUNT_USER,countUser);
        /**
         * 查询累计成交金额
         */
        Double querryAllMoney = bidInfoService.querryAllMoney();
        model.addAttribute(Constans.BID_INFO_ALLMONEY,querryAllMoney);

        /**
         * 查询list列表根据类型查询产品列表
         * 查询新手宝的信息
         */
        HashMap<String, Object> params = new HashMap<>();
        params.put("productType",0);
        params.put("currentPage",0);
        params.put("pageSize",1);
        List<LoanInfo> loaninfox= loanInfoService.querryProduceType_X(params);
         model.addAttribute(Constans.LOAN_INFO_X,loaninfox);
        /**
         * 查询list列表根据类型查询产品列表
         * 查询优选类产品的的信息
         */
        params.put("productType",1);
        params.put("pageSize",4);
        List<LoanInfo> loaninfoy= loanInfoService.querryProduceType_Y(params);
        model.addAttribute(Constans.LOAN_INFO_Y,loaninfoy);
        /**
         * 查询散标类产品列表
         */
        params.put("productType",2);
        params.put("pageSize",8);
        List<LoanInfo> loaninfos= loanInfoService.querryProduceType_S(params);
        model.addAttribute(Constans.LOAN_INFO_S,loaninfos);
        return "index";

    }
}
