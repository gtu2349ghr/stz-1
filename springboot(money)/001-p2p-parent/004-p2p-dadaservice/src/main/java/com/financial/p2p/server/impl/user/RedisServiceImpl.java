package com.financial.p2p.server.impl.user;

import com.alibaba.dubbo.config.annotation.Service;
import com.financial.p2p.server.user.RedisService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;
@Component
@Service(interfaceClass = RedisService.class,version = "1.0.0",timeout = 15000)
public class RedisServiceImpl implements RedisService {
    @Resource
    RedisTemplate<Object,Object> redisTemplate;
    @Override
    public void put(String phone, String ranDomNumer) {
        //设置时长三十分钟
        System.out.println(ranDomNumer);
             redisTemplate.opsForValue().set(phone,ranDomNumer,60, TimeUnit.SECONDS);
    }

    @Override
    public String get(String phone) {
        //根据手机号获取redis中的验证码
        String o = (String) redisTemplate.opsForValue().get(phone);
        return o;
    }
}
