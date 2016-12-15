package com.tencent.service;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;

import com.tencent.common.Configure;
import com.tencent.protocol.base_access_token_protocol.GetBaseAccessTokenReqData;

/**
 * 获取/刷新基础支持的AccessToken服务类目前调用限制，2000/天
 * 
 * @since 2016/12/15
 * @author mengzh
 */
public class GetBaseAccessTokenService extends BaseService {

    public GetBaseAccessTokenService(String certLocalPath, String certPassword)
        throws IllegalAccessException, InstantiationException, ClassNotFoundException, UnrecoverableKeyException, KeyManagementException, NoSuchAlgorithmException, KeyStoreException, IOException {
        super(Configure.GET_BASE_ACCESS_TOKEN, certLocalPath, certPassword);
    }

    /**
     * 请求获取/刷新基础支持的AccessToken服务
     * 
     * @param closeOrderReqData
     *            这个数据对象里面包含了API要求提交的各种数据字段
     * @return API返回的JSON数据
     * @throws Exception
     */
    public String request(GetBaseAccessTokenReqData getBaseAccessTokenReqData)
        throws Exception {

        // --------------------------------------------------------------------
        // 发送HTTPS的Get请求到API地址
        // --------------------------------------------------------------------
        String responseString = sendGet(apiURL.replace("APPID", getBaseAccessTokenReqData.getAppid()).replace("APPSECRET", getBaseAccessTokenReqData.getSecret()));
        
        return responseString;
    }

}
