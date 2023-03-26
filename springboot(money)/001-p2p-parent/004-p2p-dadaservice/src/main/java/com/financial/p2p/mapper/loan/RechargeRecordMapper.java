package com.financial.p2p.mapper.loan;

import com.financial.p2p.model.RechargeRecord;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface RechargeRecordMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(RechargeRecord record);

    int insertSelective(RechargeRecord record);

    RechargeRecord selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(RechargeRecord record);

    int updateByPrimaryKey(RechargeRecord record);

    RechargeRecord selectByOrderId(String orderId);

    List<RechargeRecord> cheackOrderin();

    List<RechargeRecord> selectByuid(int rows,int uid);

    /**
     * 分页查询充值记录
     * @param
     * @return
     */
    List<RechargeRecord> selectByPage(Integer count,Integer currentPage,int uid);

    int selectBycount(int uid);
}