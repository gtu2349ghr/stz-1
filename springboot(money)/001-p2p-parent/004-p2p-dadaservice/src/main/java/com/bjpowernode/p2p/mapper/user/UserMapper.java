package com.bjpowernode.p2p.mapper.user;

import com.bjpowernode.p2p.model.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    Integer selectCountUser();

    User selectPhone(String phone);

    User selectUser(String phone, String passWord);
}