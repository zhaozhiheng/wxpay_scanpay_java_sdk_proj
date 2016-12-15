package com.tencent.protocol.send_template_msg_protocol;

/**
 * 发送模版消息 API 结果包
 *
 * @since 2016/12/15
 * @author mengzh
 */
public class SendTemplateMsgResData {

    private String errcode;
    private String errmsg;
    private String msgid;

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

    public String getMsgid() {
        return msgid;
    }

    public void setMsgid(String msgid) {
        this.msgid = msgid;
    }

}
