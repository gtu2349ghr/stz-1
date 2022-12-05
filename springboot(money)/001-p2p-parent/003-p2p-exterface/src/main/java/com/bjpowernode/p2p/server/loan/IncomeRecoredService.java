package com.bjpowernode.p2p.server.loan;

import com.bjpowernode.p2p.model.IncomeRecord;

import java.util.List;

public interface IncomeRecoredService {

    /**
     * 生成收益计划
     * @return
     */
    int generateIncomes() ;

    /**
     * 返还收益计划
     */
    int incomeRecoredBack() throws Exception;

    /**
     * 查询5条收益记录
     * @param rows
     * @param
     * @return
     */
    List<IncomeRecord> selectAllByLoanInfo(int rows, Integer uid);
}
