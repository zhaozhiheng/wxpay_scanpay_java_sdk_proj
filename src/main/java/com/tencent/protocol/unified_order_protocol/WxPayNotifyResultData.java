package com.tencent.protocol.unified_order_protocol;

/**
 * 微信支付结果通知 系统处理结果返回包
 * 
 * @author 孟郑宏
 */
public class WxPayNotifyResultData {

    // 协议层
    private String return_code = "";
    private String return_msg = "";

    public WxPayNotifyResultData(String return_code) {
        this.return_code = return_code;
    }

    public String getReturn_code() {
        return return_code;
    }

    public void setReturn_code(String return_code) {
        this.return_code = return_code;
    }

    public String getReturn_msg() {
        return return_msg;
    }

    public void setReturn_msg(String return_msg) {
        this.return_msg = return_msg;
    }

}
