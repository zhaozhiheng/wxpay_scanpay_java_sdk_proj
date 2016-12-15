package com.tencent.protocol.send_template_msg_protocol;

import java.util.Map;

/**
 * 发送模版消息 API需要提交的数据
 *
 * @since 2016/12/15
 * @author mengzh
 */
public class SendTemplateMsgReqData {

    /** 接收者openid */
    private String touser;
    /** 模板ID */
    private String template_id;
    /** 模板跳转链接，URL置空，则在发送后，点击模板消息会进入一个空白页面（ios），或无法点击（android） */
    private String url;
    /** 标题颜色 */
    private String titleColor;
    /** 详细内容 */
    private Map<String, TemplateData> data;

    public SendTemplateMsgReqData(String touser, String titleColor, String template_id, String url, Map<String, TemplateData> data) {
        this.touser = touser;
        this.template_id = template_id;
        if (titleColor != null && !"".equals(titleColor)) {
            this.titleColor = titleColor;
        }
        this.url = url;
        this.data = data;
    }

    public String getTemplate_id() {
        return template_id;
    }

    public void setTemplate_id(String template_id) {
        this.template_id = template_id;
    }

    public String getTouser() {
        return touser;
    }

    public void setTouser(String touser) {
        this.touser = touser;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitleColor() {
        return titleColor;
    }

    public void setTitleColor(String titleColor) {
        this.titleColor = titleColor;
    }

    public Map<String, TemplateData> getData() {
        return data;
    }

    public void setData(Map<String, TemplateData> data) {
        this.data = data;
    }

}
