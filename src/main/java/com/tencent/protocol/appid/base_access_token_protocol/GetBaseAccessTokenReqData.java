package com.tencent.protocol.appid.base_access_token_protocol;

/**
 * 获取/刷新基础支持的AccessToken API需要提交的数据
 *
 * @since 2016/12/15
 * @author mengzh
 */
public class GetBaseAccessTokenReqData {

    /**必填，获取access_token填写client_credential*/
    private String grant_type = "client_credential";
    /**必填，第三方用户唯一凭证*/
    private String appid = "";
    /**必填，第三方用户唯一凭证密钥，即appsecret*/
    private String secret = "";

    public GetBaseAccessTokenReqData(String appid, String secret) {
        this.appid = appid;
        this.secret = secret;
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

}
