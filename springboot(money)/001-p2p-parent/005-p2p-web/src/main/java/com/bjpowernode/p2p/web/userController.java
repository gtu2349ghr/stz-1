package com.bjpowernode.p2p.web;
import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSONObject;
import com.bjpowernode.p2p.model.*;
import com.bjpowernode.p2p.server.loan.BidInfoService;
import com.bjpowernode.p2p.server.loan.IncomeRecoredService;
import com.bjpowernode.p2p.server.loan.LoanInfoService;
import com.bjpowernode.p2p.server.loan.RechargerecordService;
import com.bjpowernode.p2p.server.user.FinaneCountService;
import com.bjpowernode.p2p.server.user.UserService;
import com.bjpowernode.springboot.cons.Constans;
import com.bjpowernode.springboot.cons.utils.ImageVerificationCode;
import com.bjpowernode.springboot.cons.utils.Result;
import lombok.Builder;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Controller
public class userController {
    @Autowired
    RedisTemplate redisTemplate;
    @Reference(interfaceClass = UserService.class,check = false,version = "1.0.0",timeout = 15000)
    UserService userService;
    @Reference(interfaceClass = FinaneCountService.class,check = false,version = "1.0.0",timeout = 15000)
    FinaneCountService finaneCountService;
    @Reference(interfaceClass = LoanInfoService.class,check = false,version = "1.0.0",timeout = 15000)
    LoanInfoService loanInfoService;
   @Reference(interfaceClass = RechargerecordService.class,version = "1.0.0",timeout = 15000)
    RechargerecordService rechargerecordService;
   @Reference(interfaceClass = BidInfoService.class,version="1.0.0",timeout = 15000)
   BidInfoService bidInfoService;
   @Reference(interfaceClass = IncomeRecoredService.class,version = "1.0.0",timeout = 15000)
   IncomeRecoredService incomeRecoredService;
   /**
     * 返回实名认证页面
     * @return
     */
    @RequestMapping("/user/realName")
    public  String realName(){
        return "realName";
    }
    @RequestMapping("/user/certion")
    @ResponseBody
    /**
     * 用户的实名认证
     */
    public Result certions(HttpServletRequest request,
                           @RequestParam(value = "messageCode" ) String messageCode,
                           @RequestParam(value = "idCard") String idCard,
                           @RequestParam(value = "realName") String realName
                           ) throws Exception {
                  //现在开始身份认证
        HashMap<String, Object> map = new HashMap<>();
        String url="https://way.jd.com/YOUYU365/jd_credit_two";
        map.put("appkey","cb4d78665c1313a4222d7add081df9f3");
        map.put("certNo",idCard);
        map.put("name",realName);
        //发送信息
//        String str = HttpClientUtils.doPost(url, map);
        String str = "{\n" +
                "    \"code\": \"10000\",\n" +
                "    \"charge\": false,\n" +
                "    \"remain\": 1305,\n" +
                "    \"msg\": \"查询成功\",\n" +
                "    \"result\": {\n" +
                "        \"code\": 000000,\n" +
                "            \"serialNo\": \"350721197702134399\",\n" +
                "            \"success\": \"true\",\n" +
                "            \"message\": \"一致\",\n" +
                "            \"comfirm\": \"jd_credit_two\",\n" +
                "        }\n" +
                "}";

        //然后解析json为json
        JSONObject jsonObject = JSONObject.parseObject(str);
      //拿到json对象后，得到它返回的值
        String code = jsonObject.getString("code");
        if(!StringUtils.equals(code,"10000")){
            return Result.erro("身份识别失败");
        }else {
            //成功之后继续解析
            String result = jsonObject.getString("result");
            //拿到result之后继续得到下一个对象
            JSONObject jsonObject1 = JSONObject.parseObject(result);
//            //得到下一个result
//            String result1 = jsonObject1.getString("result");
            //解析这个rsult
//            JSONObject jsonObject2 = JSONObject.parseObject(result1);
            String description = jsonObject1.getString("message");
            if(!StringUtils.equals(description,"一致")){

              return   Result.erro("身份信息不一致，请重新检查");
            }
            //认证成功之后将信息存到user里
            User  user =(User) request.getSession().getAttribute(Constans.SESSION_USER);
            user.setName(realName);
            user.setIdCard(idCard);
            //然后对表进行更新
            int results=userService.updateUser(user);
            if(results==1){
                Result.success("实名认证成功");
            }  return Result.success("身份信息一致，实名认证未成功");
        }

    }

    /**
     * 查询y余额
     * @param request
     * @return
     */
    @RequestMapping("/user/finaneCount")
    @ResponseBody
    public  Double finaneCount(HttpServletRequest request){
        User user =(User) request.getSession().getAttribute(Constans.SESSION_USER);
        //这个是根据uid来查询而user的id等于账户的uid，在用户注册的时候关联导入的，注册领888红包
        Double financeAccount=finaneCountService.querryCount(user.getId());
        return  financeAccount;
    }
    /**
     * 用户的退出,重定向到首页，删除session用户的信息
     */
    @RequestMapping("/loan/logout")
    public  String loginOut(HttpServletRequest request){
            request.getSession().removeAttribute(Constans.SESSION_USER);
        return "redirect:/index";//根路径表示上下文跟p2p
    }


        /**
         * 生成验证码的接口
         *
         * @param response Response对象
         * @param request  Request对象
         * @throws Exception
         */
        @RequestMapping("getVerifiCode")
        @ResponseBody
        public void getVerifiCode(HttpServletRequest request, HttpServletResponse response) throws IOException {
        /*
             1.生成验证码
             2.把验证码上的文本存在session中
             3.把验证码图片发送给客户端
             */
            ImageVerificationCode ivc = new ImageVerificationCode();     //用我们的验证码类，生成验证码类对象
            BufferedImage image = ivc.getImage();  //获取验证码
            request.getSession().setAttribute("text", ivc.getText()); //将验证码的文本存在session中
            ivc.output(image, response.getOutputStream());//将验证码图片响应给客户端
        }


        @RequestMapping("/Login_authentication")
        @ResponseBody
        public Result Login_authentication(HttpServletRequest request,HttpServletResponse response,@RequestParam(value = "message") String message) throws IOException, ServletException {
            request.setCharacterEncoding("utf-8");
            String session_vcode=(String) request.getSession().getAttribute("text");    //从session中获取真正的验证码
            System.out.println(session_vcode);
            if(!StringUtils.equals(message,session_vcode)){
             return   Result.erro("验证码不正确");
            }else{
                return Result.success("验证成功");
            }
        }
        @RequestMapping("/loan/page/login")
    public String loginPage(HttpServletResponse response,HttpServletRequest request,Model model) throws IOException {
//进入登录页面，先判断是否有redis,如果没有则登录，有则直接跳转首页
//            Object attribute = request.getSession().getAttribute(Constans.SESSION_USER);
//            Object o = redisTemplate.opsForValue().get(Constans.SESSION_USER);

//            if(attribute==null){
//                return "login";
//            }else{
//                return "redirect:/index";
//            }
            Integer user = userService.querryCountUser();
            Double money = finaneCountService.selectSumMoney();
            long l = money.longValue();
           String money1= String.valueOf(l);
            int length = money1.length();
             int moneyNew=length-4;
            String substring = money1.substring(0, moneyNew);
            model.addAttribute("user",user);
           model.addAttribute("money",substring);
            return "login";
        }

    /**
     * 验证登录
     * @return
     */
    @RequestMapping("/loan/login")
    @ResponseBody
    public  Result loginConfirm(HttpServletRequest request,
            HttpServletResponse response,
            @RequestParam(value = "phone") String phone,
            @RequestParam(value = "passWord") String passWord){
                    //根据用户名和密码验证
         User user=  userService.QuerryUser(phone,passWord);
         if (user==null){
             //没查到
             return Result.erro("用户名或者密码不正确");
         }else {
             //将数据加入到redis
            redisTemplate.opsForValue().set(Constans.SESSION_USER,user,3,TimeUnit.DAYS);
             //然后将数据添加到sessio中
             User sessionUser =(User) redisTemplate.opsForValue().get(Constans.SESSION_USER);
             System.out.println(sessionUser);

             HttpSession session = request.getSession();
             session.setAttribute(Constans.SESSION_USER,user);
//             HttpSession session = request.getSession();
             session.setMaxInactiveInterval(72 *3600);
//             //存sessionId的cookie
             Cookie cookieSId = new Cookie("JSESSIONID",session.getId());
             cookieSId.setMaxAge(72 *3600);
             cookieSId.setPath("/");
             response.addCookie(cookieSId);

             //设置时间
             return Result.success("登录成功");

         }

        }

    /**
     * 进入小金库
     * @return
     */
    @RequestMapping("/loan/myCenter")
    public  String  counter(HttpServletRequest request, Model model){
        User user = (User) request.getSession().getAttribute(Constans.SESSION_USER);
            Double finaneCount = finaneCountService.querryCount(user.getId());
            model.addAttribute("finaneCount",finaneCount);
        LoanInfo loanInfo = loanInfoService.querryLoanInfoById(user.getId());
        //然后查询充值记录
        //查询5条。按照时间查询，然后根据uid
        int rows=5;
        int uid=user.getId();
       List<RechargeRecord> rechargeRecord= rechargerecordService.selectAllRecord(rows,uid);
        System.out.println(rechargeRecord+"这是查到的记录");
        model.addAttribute("rechargeList",rechargeRecord);
        //然后查投资记录表
       List<BidInfo> bidInList=bidInfoService.selectByLoanId(user.getId(),rows);
        System.out.println(bidInList);
        model.addAttribute("bidInList",bidInList);
        //查询5条收益记录
        List<IncomeRecord> incomeRecords=incomeRecoredService.selectAllByLoanInfo(rows,user.getId());
        System.out.println(incomeRecords);
        model.addAttribute("incomeList",incomeRecords);
        return "myCenter";
    }
@RequestMapping("/loan/myRecharge")
    public  String myRecharge(@RequestParam(value = "current",defaultValue = "1") int current,
                              Model model
                              ){
              //从前端拿到当前页
        //然后进行封装到impl层
    User   user =(User) redisTemplate.opsForValue().get(Constans.SESSION_USER);
    ProductPage<RechargeRecord> page = new ProductPage<>();
        page.setCount(5);
        page.setCurrentPage((current-1)*page.getCount());
       ProductPage<RechargeRecord>rechargeModel= rechargerecordService.selectRechargeLimit(page,user.getId());
       model.addAttribute("pageList",rechargeModel.getProductList());
       model.addAttribute("totalPage",rechargeModel.getTotalPage());
       model.addAttribute("Allcount",rechargeModel.getAllPage());
       model.addAttribute("currentPage",current);
    return "myRecharge";
}

    /**
     * 查看全部投资记录
     * @return
     */
    @RequestMapping("/loan/myInvest")
    public String toInvest(@RequestParam(value = "current",defaultValue = "1") int current,
                           Model model){
        User   user =(User) redisTemplate.opsForValue().get(Constans.SESSION_USER);
        ProductPage<BidInfo> page = new ProductPage<>();
        page.setCount(5);
        page.setCurrentPage((current-1)*page.getCount());
        ProductPage<BidInfo>rechargeModel= bidInfoService.selectbidInfoLimit(page,user.getId());
        model.addAttribute("pageList",rechargeModel.getProductList());
        model.addAttribute("totalPage",rechargeModel.getTotalPage());
        model.addAttribute("Allcount",rechargeModel.getAllPage());
        model.addAttribute("currentPage",current);
        return "myInvest";
}

}



