package com.tencent.service;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;

import com.tencent.common.HttpsRequest;

/**
 * User: rizenguo
 * Date: 2014/12/10
 * Time: 15:44
 * 服务的基类
 */
public class BaseService{

    //API的地址
    protected String apiURL;

    //发请求的HTTPS请求器
    private IServiceRequest serviceRequest;

    public BaseService(String api,String certLocalPath,String certPassword) throws ClassNotFoundException, IllegalAccessException, InstantiationException, UnrecoverableKeyException, KeyManagementException, NoSuchAlgorithmException, KeyStoreException, IOException {
        apiURL = api;
        serviceRequest =  new HttpsRequest(certLocalPath,certPassword);
    }

    protected String sendPost(Object xmlObj) throws UnrecoverableKeyException, IOException, NoSuchAlgorithmException, KeyStoreException, KeyManagementException {
        return serviceRequest.sendPost(apiURL,xmlObj);
    }

    protected String sendPostJSON(Object jsonObj) throws UnrecoverableKeyException, IOException, NoSuchAlgorithmException, KeyStoreException, KeyManagementException {
        return serviceRequest.sendPostJSON(apiURL, jsonObj);
    }
    
    protected String sendGet(String url) throws UnrecoverableKeyException, IOException, NoSuchAlgorithmException, KeyStoreException, KeyManagementException {
        return serviceRequest.sendGet(url == null || "".equals(url) ? apiURL : url);
    }

    /**
     * 供商户想自定义自己的HTTP请求器用
     * @param request 实现了IserviceRequest接口的HttpsRequest
     */
    public void setServiceRequest(IServiceRequest request){
        serviceRequest = request;
    }
}
