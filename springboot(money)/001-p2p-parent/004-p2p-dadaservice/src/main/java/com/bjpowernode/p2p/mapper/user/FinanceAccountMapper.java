package com.bjpowernode.p2p.mapper.user;

import com.bjpowernode.p2p.model.FinanceAccount;
import org.apache.ibatis.annotations.Mapper;

import java.util.HashMap;

@Mapper
public interface FinanceAccountMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(FinanceAccount record);

    int insertSelective(FinanceAccount record);

    FinanceAccount selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(FinanceAccount record);

    int updateByPrimaryKey(FinanceAccount record);

    Double selectByFinanceAccount(Integer uid);

    /**
     * 投资，更新账户余额
     * @param
     * @return
     */
    int updaeAccountMoney(Integer uid,Double bidMoney);

    int updateIncomeBackMoney(Integer uid, Double incomeBack);

    /**
     * 充值更新账户余额
     * @param uid
     * @param rechargeMoney
     * @return
     */
    int updateRecord(Integer uid, Double rechargeMoney);

    /**
     * 查询投资总金额
     * @return
     */
    Double selectSumMoney();
}