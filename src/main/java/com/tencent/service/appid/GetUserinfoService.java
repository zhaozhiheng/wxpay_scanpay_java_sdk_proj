package com.tencent.service.appid;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;

import com.tencent.common.Configure;
import com.tencent.protocol.appid.sns_userinfo_protocol.GetUserinfoReqData;
import com.tencent.service.BaseService;

/**
 * 网页授权拉取用户信息 服务类
 * 
 * @since 2016/12/16
 * @author mengzh
 */
public class GetUserinfoService extends BaseService {

    public GetUserinfoService(String certLocalPath, String certPassword)
        throws IllegalAccessException, InstantiationException, ClassNotFoundException, UnrecoverableKeyException, KeyManagementException, NoSuchAlgorithmException, KeyStoreException, IOException {
        super(Configure.JSAPI_GET_USERINFO, certLocalPath, certPassword);
    }

    /**
     * 请求网页授权拉取用户信息服务
     * 
     * @param getAuthAccessTokenReqData
     *            这个数据对象里面包含了API要求提交的各种数据字段
     * @return API返回的JSON数据
     * @throws Exception
     */
    public String request(GetUserinfoReqData getUserinfoReqData)
        throws Exception {

        // --------------------------------------------------------------------
        // 发送HTTPS的Get请求到API地址
        // --------------------------------------------------------------------
        String responseString = sendGet(apiURL.replace("ACCESS_TOKEN", getUserinfoReqData.getAccess_token()).replace("OPENID ", getUserinfoReqData.getOpenid()));
        
        return responseString;
    }

}
