package com.tencent.protocol.send_template_msg_protocol;

/**
 * 模版数据
 * 
 * @author 孟郑宏
 */
public class TemplateData {

    private String value;
    private String color;
    
    public TemplateData(String value) {
        super();
        this.value = value;
    }

    public TemplateData(String value, String color) {
        this.value = value;
        this.color = color;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @Override
    public String toString() {
        return "TemplateData [value=" + value + ", color=" + color + "]";
    }
    
}
