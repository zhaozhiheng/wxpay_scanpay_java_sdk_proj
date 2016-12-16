package com.tencent.protocol.base_access_token_protocol;

/**
 * 获取/刷新基础支持的AccessToken API 结果包
 *
 * @since 2016/12/15
 * @author mengzh
 */
public class GetBaseAccessTokenResData {

    private String errcode;
    private String errmsg;
    /** 目前512个字符 */
    private String access_token;
    /** 凭证有效时间，单位：秒，目前7200秒，2个小时 */
    private long expires_in;

    public String getErrcode() {
        return errcode;
    }

    public void setErrcode(String errcode) {
        this.errcode = errcode;
    }

    public String getErrmsg() {
        return errmsg;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public long getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(long expires_in) {
        this.expires_in = expires_in;
    }

}
