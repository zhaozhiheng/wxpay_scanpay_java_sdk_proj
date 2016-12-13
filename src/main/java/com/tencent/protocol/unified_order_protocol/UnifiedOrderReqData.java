package com.tencent.protocol.unified_order_protocol;

import com.tencent.common.RandomStringGenerator;
import com.tencent.common.Signature;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * 统一下单API需要提交的数据
 *
 * @since 2016/12/13
 * @author mengzh
 */
public class UnifiedOrderReqData {

    // 每个字段具体的意思请查看API文档
    private String appid = "";
    private String mch_id = "";
    private String sub_appid = "";
    private String sub_mch_id = "";
    private String device_info = "";
    private String nonce_str = "";
    private String sign = "";
    private String sign_type = "MD5";
    private String body = "";
    private String detail = "";
    private String attach = "";
    private String out_trade_no = "";
    private String fee_type = "CNY";
    private int total_fee = 0;
    private String spbill_create_ip = "";
    private String time_start = "";
    private String time_expire = "";
    private String goods_tag = "";
    private String notify_url = "";
    private String trade_type = "JSAPI";
    private String product_id = "";
    private String limit_pay = "";
    private String openid = "";
    private String sub_openid = "";

    /**
     * @param appid
     *            公众账号ID
     * @param mch_id
     *            商户号
     * @param sub_appid
     *            子商户公众账号ID
     * @param sub_mch_id
     *            子商户号
     * @param device_info
     *            设备号
     * @param nonce_str
     *            随机字符串
     * @param sign
     *            签名
     * @param sign_type
     *            签名类型
     * @param body
     *            商品描述
     * @param detail
     *            商品详情
     * @param attach
     *            透传数据
     * @param out_trade_no
     *            商户订单号
     * @param fee_type
     *            货币类型
     * @param total_fee
     *            总金额，单位为“分”，只能整数
     * @param spbill_create_ip
     *            订单生成的机器IP
     * @param time_start
     *            订单生成时间， 格式为yyyyMMddHHmmss，如2009年12 月25 日9 点10 分10 秒表示为20091225091010。时区为GMT+8 beijing。该时间取自商户服务器
     * @param time_expire
     *            订单失效时间
     * @param goods_tag
     *            商品标记
     * @param notify_url
     *            通知地址
     * @param trade_type
     *            交易类型
     * @param product_id
     *            商品ID
     * @param limit_pay
     *            指定支付方式
     * @param openid
     *            用户标识
     * @param sub_openid
     *            用户子标识
     * @param keyPartner
     *            秘钥
     */
    public UnifiedOrderReqData(String appid, String mch_id, String sub_appid, String sub_mch_id, String device_info, String nonce_str, String sign, String sign_type, String body, String detail, String attach, String out_trade_no, String fee_type, int total_fee, String spbill_create_ip, String time_start, String time_expire, String goods_tag, String notify_url, String trade_type, String product_id, String limit_pay, String openid, String sub_openid, String keyPartner) {
        this.appid = appid;
        this.mch_id = mch_id;
        this.sub_appid = sub_appid;
        this.sub_mch_id = sub_mch_id;
        this.device_info = device_info;
        this.nonce_str = RandomStringGenerator.getRandomStringByLength(32);
        this.sign_type = sign_type;
        this.body = body;
        this.detail = detail;
        this.attach = attach;
        this.out_trade_no = out_trade_no;
        this.fee_type = fee_type;
        // 订单总金额，单位为“分”，只能整数
        this.total_fee = total_fee;
        this.spbill_create_ip = spbill_create_ip;
        this.time_start = time_start;
        this.time_expire = time_expire;
        this.goods_tag = goods_tag;
        this.notify_url = notify_url;
        this.trade_type = trade_type;
        this.product_id = product_id;
        this.limit_pay = limit_pay;
        this.openid = openid;
        this.sub_openid = sub_openid;
        // 根据API给的签名规则进行签名
        this.sign = Signature.getSign(toMap(), keyPartner);
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getMch_id() {
        return mch_id;
    }

    public void setMch_id(String mch_id) {
        this.mch_id = mch_id;
    }

    public String getDevice_info() {
        return device_info;
    }

    public void setDevice_info(String device_info) {
        this.device_info = device_info;
    }

    public String getNonce_str() {
        return nonce_str;
    }

    public void setNonce_str(String nonce_str) {
        this.nonce_str = nonce_str;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getAttach() {
        return attach;
    }

    public void setAttach(String attach) {
        this.attach = attach;
    }

    public String getOut_trade_no() {
        return out_trade_no;
    }

    public void setOut_trade_no(String out_trade_no) {
        this.out_trade_no = out_trade_no;
    }

    public int getTotal_fee() {
        return total_fee;
    }

    public void setTotal_fee(int total_fee) {
        this.total_fee = total_fee;
    }

    public String getSpbill_create_ip() {
        return spbill_create_ip;
    }

    public void setSpbill_create_ip(String spbill_create_ip) {
        this.spbill_create_ip = spbill_create_ip;
    }

    public String getTime_start() {
        return time_start;
    }

    public void setTime_start(String time_start) {
        this.time_start = time_start;
    }

    public String getTime_expire() {
        return time_expire;
    }

    public void setTime_expire(String time_expire) {
        this.time_expire = time_expire;
    }

    public String getGoods_tag() {
        return goods_tag;
    }

    public void setGoods_tag(String goods_tag) {
        this.goods_tag = goods_tag;
    }

    public String getSub_mch_id() {
        return sub_mch_id;
    }

    public void setSub_mch_id(String sub_mch_id) {
        this.sub_mch_id = sub_mch_id;
    }

    public String getSub_appid() {
        return sub_appid;
    }

    public void setSub_appid(String sub_appid) {
        this.sub_appid = sub_appid;
    }

    public String getSign_type() {
        return sign_type;
    }

    public void setSign_type(String sign_type) {
        this.sign_type = sign_type;
    }

    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<String, Object>();
        Field[] fields = this.getClass().getDeclaredFields();
        for (Field field : fields) {
            Object obj;
            try {
                obj = field.get(this);
                if (obj != null) {
                    map.put(field.getName(), obj);
                }
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return map;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getFee_type() {
        return fee_type;
    }

    public void setFee_type(String fee_type) {
        this.fee_type = fee_type;
    }

    public String getNotify_url() {
        return notify_url;
    }

    public void setNotify_url(String notify_url) {
        this.notify_url = notify_url;
    }

    public String getTrade_type() {
        return trade_type;
    }

    public void setTrade_type(String trade_type) {
        this.trade_type = trade_type;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getLimit_pay() {
        return limit_pay;
    }

    public void setLimit_pay(String limit_pay) {
        this.limit_pay = limit_pay;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getSub_openid() {
        return sub_openid;
    }

    public void setSub_openid(String sub_openid) {
        this.sub_openid = sub_openid;
    }

}
