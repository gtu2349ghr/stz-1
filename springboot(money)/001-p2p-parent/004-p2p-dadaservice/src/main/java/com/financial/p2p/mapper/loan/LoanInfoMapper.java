package com.financial.p2p.mapper.loan;

import com.financial.p2p.model.LoanInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Mapper
public interface LoanInfoMapper {
    Double selectHistryAvgRate();
    int deleteByPrimaryKey(Integer id);

    int insert(LoanInfo record);

    int insertSelective(LoanInfo record);

    LoanInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(LoanInfo record);

    int updateByPrimaryKey(LoanInfo record);
      List<LoanInfo> selectByProductType_X(Map<String,Object> params);

    List<LoanInfo> selectByProductType_Y(Map<String, Object> params);

    List<LoanInfo> selectByProductType_S(Map<String, Object> params);
    

    List<LoanInfo> selectProductList(Map<String, Object> params);

    Integer selectPage(Map<String, Object> params);

    int updateLeftProductMoney(HashMap<String, Object> map);

    /**
     * 查询已满表产品的信息
     * @return
     */
    List<LoanInfo> seleceByLoanInfoByProductStas();
}