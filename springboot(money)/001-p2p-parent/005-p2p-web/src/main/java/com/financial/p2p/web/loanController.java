package com.financial.p2p.web;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSONObject;
import com.financial.p2p.model.User;
import com.financial.p2p.server.user.RedisService;
import com.financial.p2p.server.user.UserService;

import com.financial.springboot.cons.Constans;
import com.financial.springboot.cons.utils.Result;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Node;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Controller
public class loanController {
    @Reference(interfaceClass = UserService.class,check = false,version="1.0.0",timeout = 15000)
    UserService userService;
    @Reference(interfaceClass = RedisService.class,check = false,version="1.0.0",timeout=15000)
    RedisService redisService;
    @Autowired
    RedisTemplate redisTemplate;
    @RequestMapping("/loan/page/register")
    public  String loan(){

        return "register";
    }

    @RequestMapping("/enroll/checkPhone")
    @ResponseBody
    public Map<String, Object> checkPhone( String phone){
        User user = userService.querryPhone(phone);
        if(ObjectUtils.allNotNull(user)){
            return Result.erro("手机号已存在");
        }else {
           return Result.success("验证成功");
       }
    }

    /**
     * 用户的注册
     * @param request
     * @param phone
     * @param password
     * @param messageCode
     * @return
     */
    @RequestMapping("/enroll/register")
    @ResponseBody
    public  Map<String,Object> enroll(HttpServletRequest request,
            @RequestParam(value = "phone") String phone,
                                      @RequestParam(value = "password") String password,
                                      @RequestParam(value = "messageCode") String messageCode

    ){
        //返回一个user，然后通过此登陆的user可以传到前端
        try {
                String s = redisService.get(phone);
                if(StringUtils.equals(messageCode,s)){
                    User user=userService.register(phone,password);
                    //成功之后设置session中相当于免登录
                    request.getSession().setAttribute(Constans.SESSION_USER,user);
                    //加入redis
                    redisTemplate.opsForValue().set(Constans.SESSION_USER,user);
                    return Result.success("注册成功");
                }
        } catch (Exception e) {
            return Result.erro("注册失败");
        }
        return Result.erro("验证码错误");
    }

    /**
     * 发送验证码
     * @param phone
     * @return
     * @throws Exception
     */
    @RequestMapping("/user/Verifaction/")
    @ResponseBody
    public  Map<String,Object> verifaction(String phone) throws Exception {
        //这个时返回前端的map
        HashMap<String, Object> params = new HashMap<>();
        params.put("appkey", "cb4d78665c1313a4222d7add081df9f3");
        params.put("mobile", phone);
        String RanDomNumer = gerRandom(6);
        String content="【凯信通】您的验证码是："+ RanDomNumer;
        params.put("content",content);
        //然后生成一个随机验证码方法
        String url = "https://way.jd.com/kaixintong/kaixintong";
//        String jsonStr = HttpClientUtils.doPost("https://way.jd.com/kaixintong/kaixintong",params);
        String jsonStr ="{\n" +
                "    \"code\": \"10000\",\n" +
                "    \"charge\": false,\n" +
                "    \"remain\": 0,\n" +
                "    \"msg\": \"查询成功\",\n" +
                "    \"result\": \"<?xml version=\\\"1.0\\\" encoding=\\\"utf-8\\\" ?><returnsms>\\n <returnstatus>Success</returnstatus>\\n <message>ok</message>\\n <remainpoint>-1600642</remainpoint>\\n <taskID>120359706</taskID>\\n <successCounts>1</successCounts></returnsms>\"\n" +
                "}";

        JSONObject jsonObject = JSONObject.parseObject(jsonStr);
        //先解析code，等于10000则连接成功，然后解析下面的值
        String code = jsonObject.getString("code");
        if (!StringUtils.equals(code,"10000")) {
           return Result.erro("发送失败");
        }
            //然后解析xml
            String resultxml = jsonObject.getString("result");
            Document document1 = DocumentHelper.parseText(resultxml);
            Node retuannode = document1.selectSingleNode("//returnstatus");
            String text = retuannode.getText();
            if (!StringUtils.equals(text,"Success")) {

                return Result.erro("验证码发送失败");
            }
            //发送成功的时候加入到redis中
            redisService.put(phone,RanDomNumer);
        return Result.success("验证码发送成功",RanDomNumer);
    }
    private  String gerRandom(int n){
        String[] array = {"0","1","2","3","4","5","6","7","8","9"};

        StringBuffer stringBuffer = new StringBuffer();

        for (int i = 0; i < n; i++) {
            int index = (int) Math.round(Math.random()*9);
            stringBuffer.append(array[index]);
        }
        return stringBuffer.toString();
    }


}
