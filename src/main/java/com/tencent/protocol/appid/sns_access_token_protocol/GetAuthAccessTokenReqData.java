package com.tencent.protocol.appid.sns_access_token_protocol;

/**
 * 网页授权获取/刷新  AccessToken、openid API需要提交的数据
 *
 * @since 2016/12/16
 * @author mengzh
 */
public class GetAuthAccessTokenReqData {

    /**必填，获取access_token填写authorization_code，刷新时填refresh_token*/
    private String grant_type = "";
    /**必填，第三方用户唯一凭证*/
    private String appid = "";

    
    /**获取时必填，第三方用户唯一凭证密钥，即appsecret*/
    private String secret = "";
    /**获取时必填，微信用户同意授权后返回*/
    private String code = "";
    
    /**刷新时必填， 填写通过access_token获取到的refresh_token参数*/
    private String refresh_token = "";

    public GetAuthAccessTokenReqData(String grant_type, String appid, String secret, String code, String refresh_token) {
        this.grant_type = grant_type;
        this.appid = appid;
        this.secret = secret;
        this.code = code;
        this.refresh_token = refresh_token;
    }

    public String getGrant_type() {
        return grant_type;
    }

    public void setGrant_type(String grant_type) {
        this.grant_type = grant_type;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getCode() {
        return code;
    }
    
    public void setCode(String code) {
        this.code = code;
    }
    
    public String getRefresh_token() {
        return refresh_token;
    }

    public void setRefresh_token(String refresh_token) {
        this.refresh_token = refresh_token;
    }

}
