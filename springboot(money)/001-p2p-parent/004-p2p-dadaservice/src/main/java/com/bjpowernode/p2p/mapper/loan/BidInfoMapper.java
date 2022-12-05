package com.bjpowernode.p2p.mapper.loan;

import com.bjpowernode.p2p.model.BidInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BidInfoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(BidInfo record);

    int insertSelective(BidInfo record);

    BidInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(BidInfo record);

    int updateByPrimaryKey(BidInfo record);

    Double selectAllMoney();


    List<BidInfo> selectBidInfoList(Integer id);

    /**
     * 根据产品id查询投资信息表
     * @param
     * @return
     */
    List<BidInfo> selectBidInfoById(Integer id);

    /**
     * 查询5条投资记录
     * @param uid
     * @param rows
     * @return
     */
    List<BidInfo> querryByUid(Integer uid, int rows);

    /**
     * 查询全部记录
     * @param count
     * @param currentPage
     * @param uid
     * @return
     */
    List<BidInfo> selectByPage(Integer count, Integer currentPage, Integer uid);

    /**
     * 分页查询总条数
     * @param uid
     * @return
     */
    int selectBycount(Integer uid);
}