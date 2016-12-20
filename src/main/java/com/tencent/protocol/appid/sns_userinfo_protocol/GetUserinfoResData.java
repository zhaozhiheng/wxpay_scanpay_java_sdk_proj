package com.tencent.protocol.appid.sns_userinfo_protocol;

import java.util.Arrays;

/**
 * 网页授权拉取用户信息 API结果包
 *
 * @since 2016/12/16
 * @author mengzh
 */
public class GetUserinfoResData {

    private String errcode;
    private String errmsg;

    private String openid;
    /** 用户昵称 */
    private String nickname;
    /** 用户的性别，值为1时是男性，值为2时是女性，值为0时是未知 */
    private String sex;
    private String province;
    private String city;
    private String country;
    private String headimgurl;
    /** 用户特权信息，json 数组，如微信沃卡用户为（chinaunicom） */
    private String[] privilege;
    private String unionid;

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

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getHeadimgurl() {
        return headimgurl;
    }

    public void setHeadimgurl(String headimgurl) {
        this.headimgurl = headimgurl;
    }

    public String[] getPrivilege() {
        return privilege;
    }

    public void setPrivilege(String[] privilege) {
        this.privilege = privilege;
    }

    public String getUnionid() {
        return unionid;
    }

    public void setUnionid(String unionid) {
        this.unionid = unionid;
    }

    @Override
    public String toString() {
        return "GetUserinfoResData [errcode=" + errcode + ", errmsg=" + errmsg + ", openid=" + openid + ", nickname=" + nickname + ", sex=" + sex + ", province=" + province + ", city=" + city + ", country=" + country + ", headimgurl=" + headimgurl + ", privilege=" + Arrays.toString(privilege) + ", unionid=" + unionid + "]";
    }

}
