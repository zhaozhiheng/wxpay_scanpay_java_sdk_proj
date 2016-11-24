package com.tencent.protocol.unified_order_protocol;

import java.lang.reflect.Field;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.tencent.common.RandomStringGenerator;
import com.tencent.common.Signature;

/**
 * 公众号支付 JS API 请求参数类
 * 
 * @author 孟郑宏
 */
public class JSPayReqData {

    private String appId;
    private String timeStamp;
    private String nonceStr;
    private String dataPackage; // 比如：prepay_id=u802345jgfjsdfgsdg888"
    private String signType;
    private String paySign;

    public JSPayReqData(String appId, String prepayId, String keyPartner) {
        this.appId = appId;
        this.dataPackage = "prepay_id="  + prepayId;
        setTimeStamp(new Date().getTime() / 1000 + "");// 毫秒级转换成秒级，10位
        setNonceStr(RandomStringGenerator.getRandomStringByLength(32));
        setSignType("MD5");
        //根据API给的签名规则进行签名
        String sign = Signature.getSign(toMap(),keyPartner);
        setPaySign(sign);//把签名数据设置到Sign这个属性中
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getNonceStr() {
        return nonceStr;
    }

    public void setNonceStr(String nonceStr) {
        this.nonceStr = nonceStr;
    }

    public String getDataPackage() {
        return dataPackage;
    }

    public void setDataPackage(String dataPackage) {
        this.dataPackage = dataPackage;
    }

    public String getSignType() {
        return signType;
    }

    public void setSignType(String signType) {
        this.signType = signType;
    }

    public String getPaySign() {
        return paySign;
    }

    public void setPaySign(String paySign) {
        this.paySign = paySign;
    }
    
    public Map<String,Object> toMap(){
        Map<String,Object> map = new HashMap<String, Object>();
        Field[] fields = this.getClass().getDeclaredFields();
        for (Field field : fields) {
            Object obj;
            try {
                obj = field.get(this);
                if(obj!=null){
                	if (field.getName().equals("dataPackage")) {
                		
                		map.put("package", obj);// package是java 关键字，需要转换，否则报签名错误
                	} else {
                		map.put(field.getName(), obj);
                	}
                }
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return map;
    }

}
