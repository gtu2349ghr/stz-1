package com.bjpowernode.p2p.server.user;

import com.bjpowernode.p2p.model.User;

public interface UserService {
    Integer querryCountUser();

    User querryPhone(String phone);


    User register(String phone, String password) throws Exception;

    int updateUser(User user);

    User QuerryUser(String phone ,String passWord);
}
