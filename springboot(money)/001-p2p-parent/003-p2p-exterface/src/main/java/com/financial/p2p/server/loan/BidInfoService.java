package com.financial.p2p.server.loan;

import com.financial.p2p.model.BidInfo;
import com.financial.p2p.model.BidTop;
import com.financial.p2p.model.ProductPage;

import java.util.List;

public interface BidInfoService {
    Double querryAllMoney();

    List<BidInfo> querryBidInfoList(Integer id);

    List<BidTop> investTop();

    /**
     * 根据产品id查询投资信息表
     * @param
     * @return
     */
    List<BidInfo> querryBidInfoById(Integer id);

    /**
     * 查询5条投资记录
     * @param uid
     * @return
     */
    List<BidInfo> selectByLoanId(Integer uid,int rows);

    /**
     * 查询全部投资记录
     * @param page
     * @param
     * @return
     */
    ProductPage<BidInfo> selectbidInfoLimit(ProductPage<BidInfo> page, Integer uid);
}
