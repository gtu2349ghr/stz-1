package com.bjpowernode.p2p.web;

import com.alibaba.dubbo.config.annotation.Reference;
import com.bjpowernode.p2p.model.*;
import com.bjpowernode.p2p.server.loan.BidInfoService;
import com.bjpowernode.p2p.server.loan.LoanInfoService;
import com.bjpowernode.p2p.server.user.FinaneCountService;
import com.bjpowernode.springboot.cons.Constans;
import com.bjpowernode.springboot.cons.utils.Result;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Controller
public class loaninfoController {
    @Reference(interfaceClass = LoanInfoService.class,version = "1.0.0",check =false,timeout = 15000)
    LoanInfoService loanInfoService;
    @Reference(interfaceClass = BidInfoService.class,version="1.0.0",check = false,timeout = 15000)
   private  BidInfoService bidInfoService;
    @Reference(interfaceClass = FinaneCountService.class,version="1.0.0",check = false,timeout = 15000)
    private  FinaneCountService finaneCountService;
    @RequestMapping("loan/loan")
    public String loaninfo(Model model , @RequestParam(value = "ptype" ,required = false) Integer ptype,
        @RequestParam(value = "current",defaultValue = "1") Integer current)
    {
        //第一个查询的是产品列表（因为要查询两条信息，一条是产品列表，所以用一个类封装
        HashMap<String, Object> parms = new HashMap<>();
        if(ObjectUtils.allNotNull(ptype)){
            parms.put("ptype",ptype);
        }
        parms.put("currentPage",(current-1)*9);
        ProductPage<LoanInfo> obs = loanInfoService.querryProductPage(parms);
        obs.setCurrentPage(current);
          model.addAttribute("currentPage",current);//当前页
          model.addAttribute("loanInfoList",obs.getProductList());//产品列表
          model.addAttribute("TotalPage",obs.getAllPage());//总页数
        model.addAttribute("TotalSize",obs.getCount());
         if(ObjectUtils.allNotNull(ptype)){
             model.addAttribute("ptype");
         }
        //第二个查询的是总条数
        //用户投资排行榜
        List<BidTop> bidTops = bidInfoService.investTop();
         model.addAttribute(Constans.INVEST_TOP,bidTops);
        return "loan";
    }
    @RequestMapping("/loan/loanInfo")
    public  String toloaninfo(Model model,
                              HttpServletRequest request,
                              @RequestParam(value = "id") Integer id){
         //根据产品id查询产品信息
        LoanInfo loanInfo=loanInfoService.querryLoanInfoById(id);
        model.addAttribute("loanInfo",loanInfo);
        //查询投资列表
               List<BidInfo> bidInfo= bidInfoService.querryBidInfoList(id);
        model.addAttribute("bidInfo",bidInfo);
        //然后是登录状态的话就拿到账户余额
        User user = (User) request.getSession().getAttribute(Constans.SESSION_USER);
         if(ObjectUtils.allNotNull(user)){
             Double finaneCount = finaneCountService.querryCount(user.getId());
              model.addAttribute("finaneCount",finaneCount);
         }
        return "loanInfo";
    }

    /**
     * 投资
     * @param request
     * @param loanId
     * @param bidMoney
     * @return
     */
    @RequestMapping("/loaninfo/invest")
     @ResponseBody
    public Result invest(HttpServletRequest request,
                         @RequestParam("loanId") Integer loanId,
                         @RequestParam("bidMoney") Double bidMoney,
                         @RequestParam("phone") String  phone

    ){
        User user  =(User) request.getSession().getAttribute(Constans.SESSION_USER);
        HashMap<String, Object> map = new HashMap<>();
        map.put("loanId",loanId);
        map.put("bidMoney",bidMoney);
        map.put("phone",phone);
        //然后传一个用户的id
        map.put("uid",user.getId());
       Result result= loanInfoService.invest(map);
        return result;
    }
}
