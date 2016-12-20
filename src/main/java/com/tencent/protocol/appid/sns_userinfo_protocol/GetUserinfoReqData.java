package com.tencent.protocol.appid.sns_userinfo_protocol;

/**
 * 网页授权拉取用户信息 API需要提交的数据
 *
 * @since 2016/12/16
 * @author mengzh
 */
public class GetUserinfoReqData {

    /** 必填，用户的唯一标识 */
    private String openid = "";

    /** 必填， 网页授权接口调用凭证,注意：此access_token与基础支持的access_token不同 */
    private String access_token = "";
    // private String lang = "zh_CN"// 默认简体，zh_TW 繁体，en 英语

    public String getOpenid() {
        return openid;
    }

    public GetUserinfoReqData(String openid, String access_token) {
        super();
        this.openid = openid;
        this.access_token = access_token;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

}
