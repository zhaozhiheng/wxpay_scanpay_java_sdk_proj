package com.tencent.protocol.close_order_protocol;

import com.tencent.common.RandomStringGenerator;
import com.tencent.common.Signature;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * 关闭订单API需要提交的数据
 *
 * @since 2016/12/13
 * @author mengzh
 */
public class CloseOrderReqData {

    //每个字段具体的意思请查看API文档
    private String appid = "";
    private String mch_id = "";
    private String sub_mch_id = "";
    private String out_trade_no = "";
    private String nonce_str = "";
    private String sign = "";
    //private String sdk_version = ""; 请求时不能穿此，否则关单失败

    /**
     * 请求关闭订单服务
     * @param outTradeNo 商户系统内部的订单号
     * @return API返回的XML数据
     * @throws Exception
     */
    public CloseOrderReqData(String outTradeNo,String keyPartner,String appId,String mchId,String subMchId){

        //--------------------------------------------------------------------
        //以下是测试数据，请商户按照自己的实际情况填写具体的值进去
        //--------------------------------------------------------------------

        //微信分配的公众号ID（开通公众号之后可以获取到）
        setAppid(appId);

        //微信支付分配的商户号ID（开通公众号的微信支付功能之后可以获取到）
        setMch_id(mchId);
        
        //微信支付分配的特约商户号ID
        setSub_mch_id(subMchId);

        //商户系统自己生成的唯一的订单号
        setOut_trade_no(outTradeNo);

        //随机字符串，不长于32 位
        setNonce_str(RandomStringGenerator.getRandomStringByLength(32));

        //根据API给的签名规则进行签名
        String sign = Signature.getSign(toMap(),keyPartner);
        setSign(sign);//把签名数据设置到Sign这个属性中

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

    public String getOut_trade_no() {
        return out_trade_no;
    }

    public void setOut_trade_no(String out_trade_no) {
        this.out_trade_no = out_trade_no;
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
    
	public String getSub_mch_id() {
		return sub_mch_id;
	}

	public void setSub_mch_id(String sub_mch_id) {
		this.sub_mch_id = sub_mch_id;
	}
	
    public Map<String,Object> toMap(){
        Map<String,Object> map = new HashMap<String, Object>();
        Field[] fields = this.getClass().getDeclaredFields();
        for (Field field : fields) {
            Object obj;
            try {
                obj = field.get(this);
                if(obj!=null){
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
}
