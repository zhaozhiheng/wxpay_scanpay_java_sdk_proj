package com.tencent.service;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;

import com.tencent.common.Configure;
import com.tencent.protocol.send_template_msg_protocol.SendTemplateMsgReqData;

/**
 * 发送模版消息服务类
 * 
 * @since 2016/12/15
 * @author mengzh
 */
public class SendTemplateMsgService extends BaseService {

    public SendTemplateMsgService(String certLocalPath, String certPassword)
        throws IllegalAccessException, InstantiationException, ClassNotFoundException, UnrecoverableKeyException, KeyManagementException, NoSuchAlgorithmException, KeyStoreException, IOException {
        super(Configure.SEND_TEMPLATE_MESSAGE, certLocalPath, certPassword);
    }

    /**
     * 请求发送模版消息服务
     * 
     * @param sendTemplateMsgReqData
     *            这个数据对象里面包含了API要求提交的各种数据字段
     * @return API返回的JSON数据
     * @throws Exception
     */
    public String request(SendTemplateMsgReqData sendTemplateMsgReqData, String accessToken)
        throws Exception {

        // --------------------------------------------------------------------
        // 发送HTTPS的Get请求到API地址
        // --------------------------------------------------------------------
        apiURL = apiURL.replace("ACCESS_TOKEN", accessToken);
        String responseString = sendPostJSON(sendTemplateMsgReqData);
        
        return responseString;
    }

}
