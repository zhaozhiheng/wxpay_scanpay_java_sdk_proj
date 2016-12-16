package com.tencent.service;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;

import com.tencent.common.Configure;
import com.tencent.protocol.close_order_protocol.CloseOrderReqData;

/**
 * 关闭订单服务类
 * 
 * @since 2016/12/13
 * @author mengzh
 */
public class CloseOrderService extends BaseService {

    public CloseOrderService(String certLocalPath, String certPassword)
        throws IllegalAccessException, InstantiationException, ClassNotFoundException, UnrecoverableKeyException, KeyManagementException, NoSuchAlgorithmException, KeyStoreException, IOException {
        super(Configure.ORDER_CLOSE_API, certLocalPath, certPassword);
    }

    /**
     * 请求关闭订单服务
     * 
     * @param closeOrderReqData
     *            这个数据对象里面包含了API要求提交的各种数据字段
     * @return API返回的XML数据
     * @throws Exception
     */
    public String request(CloseOrderReqData closeOrderReqData)
        throws Exception {

        // --------------------------------------------------------------------
        // 发送HTTPS的Post请求到API地址
        // --------------------------------------------------------------------
        String responseString = sendPost(closeOrderReqData);

        return responseString;
    }

}
