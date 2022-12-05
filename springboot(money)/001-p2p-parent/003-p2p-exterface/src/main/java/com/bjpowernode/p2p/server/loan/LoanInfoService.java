package com.bjpowernode.p2p.server.loan;

import com.bjpowernode.p2p.model.LoanInfo;
import com.bjpowernode.p2p.model.ProductPage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface LoanInfoService {

    Double queryHistryAvgRate();

    List<LoanInfo> querryProduceType_X(Map<String,Object> params);

    List<LoanInfo> querryProduceType_Y(Map<String, Object> params);

    List<LoanInfo> querryProduceType_S(Map<String, Object> params);

    ProductPage<LoanInfo> querryProductPage(Map<String,Object> params);

    LoanInfo querryLoanInfoById(Integer id);

    com.bjpowernode.springboot.cons.utils.Result invest( HashMap<String, Object> map);

    /**
     * 查询已满表产品的信息
     * @return
     */
    List<LoanInfo> querryLoanInfoByproductStas();
}
