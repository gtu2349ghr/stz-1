package com.bjpowernode.p2p.mapper.loan;

import com.bjpowernode.p2p.model.IncomeRecord;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface IncomeRecordMapper {
    int deleteByPrimaryKey(Integer id);

    /**
     * 生成收益
     * @param record
     * @return
     */
    int insert(IncomeRecord record);

    int insertSelective(IncomeRecord record);

    IncomeRecord selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(IncomeRecord record);

    int updateByPrimaryKey(IncomeRecord record);

    /**
     * 查询收益表，返还收益
     * @return
     */
    List<IncomeRecord> selectIncomeBackMoney();

    /**
     * 查询5条收益记录
     * @param rows
     * @param uid
     * @return
     */
    List<IncomeRecord> selectIncomByLoanInfo(int rows, Integer uid);
}