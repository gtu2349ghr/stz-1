package com.financial.p2p.server.user;

public interface RedisService {
    void put(String phone, String ranDomNumer);

    String get(String phone);
}
