package com.financial.p2p.server.loan;

import java.util.Map;

public interface KuaiqianService {
    /**
     * 封装充值信息提交块钱
     * @param money
     * @param name
     * @param phone
     * @return
     */
    Map<String, String> makeKuaiqianfrom(String money, String name, String phone,String orderid);
}
