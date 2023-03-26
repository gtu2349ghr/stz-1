package com.financial.p2p.server.impl.loan;
import com.alibaba.dubbo.config.annotation.Service;
import com.financial.p2p.mapper.loan.BidInfoMapper;
import com.financial.p2p.model.BidInfo;
import com.financial.p2p.model.BidTop;
import com.financial.p2p.model.ProductPage;
import com.financial.p2p.server.loan.BidInfoService;
import com.financial.springboot.cons.Constans;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Component
@Service(interfaceClass = BidInfoService.class,version = "1.0.0",timeout = 15000)
public class BidInfoServiceImpl implements BidInfoService {
  @Resource
      BidInfoMapper bidInfoMapper;
@Resource
     RedisTemplate redisTemplate;
    @Override
    public Double querryAllMoney() {
        Double bidInfoAllMoney = (Double) redisTemplate.opsForValue().get(Constans.BID_INFO_ALLMONEY);
        if(!ObjectUtils.allNotNull(bidInfoAllMoney)){
            //没有查到进入线程锁
            synchronized (this){
                //当新的线程进入之后再查一边有了就不查了
                bidInfoAllMoney = (Double)redisTemplate.opsForValue().get(Constans.BID_INFO_ALLMONEY);
                if(!ObjectUtils.allNotNull(bidInfoAllMoney)){
                    //没有就查收数据库然后给redis
                    bidInfoAllMoney = bidInfoMapper.selectAllMoney();
                    redisTemplate.opsForValue().set(Constans.BID_INFO_ALLMONEY,bidInfoAllMoney,  7, TimeUnit.DAYS);
                }
            }
        }
        return bidInfoAllMoney;
    }

    @Override
    public List<BidInfo> querryBidInfoList(Integer id) {

        return  bidInfoMapper.selectBidInfoList(id);
    }

    /**
     * 用户投资排行榜
     * @return
     */
    @Override
    public List<BidTop> investTop() {
//        Set sets = redisTemplate.opsForZSet().reverseRangeWithScores(Constans.INVEST_TOP, 0, 5);
        ArrayList<BidTop> bidTops = new ArrayList<>();
        Set<ZSetOperations.TypedTuple<Object>> sets = redisTemplate.opsForZSet().reverseRangeWithScores(Constans.INVEST_TOP, 0, 5);
        //然后遍历set集合
        Iterator<ZSetOperations.TypedTuple<Object>> iterator = sets.iterator();
        while (iterator.hasNext()){
            //有下一个值就拿到
            BidTop bidTop = new BidTop();
            ZSetOperations.TypedTuple<Object> next = iterator.next();
            String phone = (String)next.getValue();
            Double score = next.getScore();
            bidTop.setPhone(phone);
            bidTop.setBidMoney(score);
            bidTops.add(bidTop);
        }
        return bidTops;
    }

    /**
     * 根据产品的id查询投资信息表
     * @param
     * @return
     */
    @Override
    public List<BidInfo> querryBidInfoById(Integer id) {
        return bidInfoMapper.selectBidInfoById(id);
    }

    /**
     * 查询5条投资记录
     * @param uid
     * @return
     */
    @Override
    public List<BidInfo> selectByLoanId(Integer uid,int rows) {
        return bidInfoMapper.querryByUid(uid,rows);
    }

    @Override
    public ProductPage<BidInfo> selectbidInfoLimit(ProductPage<BidInfo> page, Integer uid) {
        //进入mapper
        Integer count = page.getCount();
        Integer currentPage = page.getCurrentPage();
        List<BidInfo> list=  bidInfoMapper.selectByPage(count,currentPage,uid);
        //拿到list
        page.setProductList(list);
        //然后查总数量
        int AllPage= bidInfoMapper.selectBycount(uid);
        //根据总数量计算总页数
        int totalPage=AllPage/(page.getCount());
        int mod=AllPage%(page.getCount());
        if(mod>0){
            totalPage=totalPage+1;
        }
        //然后写入
        page.setAllPage(AllPage);//写入总数量
        page.setTotalPage(totalPage);//写入总页数
        return page;
    }
}
