package com.tencent.service.appid;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;

import com.tencent.common.Configure;
import com.tencent.protocol.appid.sns_access_token_protocol.GetAuthAccessTokenReqData;
import com.tencent.service.BaseService;

/**
 * 网页授权获取/刷新AccessToken、openid服务类，调用限制次数无限制
 * 
 * @since 2016/12/16
 * @author mengzh
 */
public class GetAuthAccessTokenService extends BaseService {

    public GetAuthAccessTokenService(String certLocalPath, String certPassword)
        throws IllegalAccessException, InstantiationException, ClassNotFoundException, UnrecoverableKeyException, KeyManagementException, NoSuchAlgorithmException, KeyStoreException, IOException {
        super(Configure.JSAPI_GET_ACCESS_TOKEN, certLocalPath, certPassword);
    }

    /**
     * 请求网页授权获取/刷新AccessToken、openid服务
     * 
     * @param getAuthAccessTokenReqData
     *            这个数据对象里面包含了API要求提交的各种数据字段
     * @return API返回的JSON数据
     * @throws Exception
     */
    public String request(GetAuthAccessTokenReqData getAuthAccessTokenReqData)
        throws Exception {

        // --------------------------------------------------------------------
        // 发送HTTPS的Get请求到API地址
        // --------------------------------------------------------------------
        String url = null;
        if ("authorization_code".equals(getAuthAccessTokenReqData.getGrant_type())) {
            url = apiURL.replace("APPID", getAuthAccessTokenReqData.getAppid()).replace("APPSECRET", getAuthAccessTokenReqData.getSecret()).replace("CODE", getAuthAccessTokenReqData.getCode());
        } else if ("refresh_token".equals(getAuthAccessTokenReqData.getGrant_type())) {
            apiURL = Configure.JSAPI_REFRESH_ACCESS_TOKEN;
            url = apiURL.replace("APPID", getAuthAccessTokenReqData.getAppid()).replace("REFRESH_TOKEN", getAuthAccessTokenReqData.getRefresh_token());
        }
        String responseString = sendGet(url);
        
        return responseString;
    }

}
