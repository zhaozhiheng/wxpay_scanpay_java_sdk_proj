package com.tencent.business;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;

import org.slf4j.LoggerFactory;

import com.tencent.common.Log;
import com.tencent.common.Signature;
import com.tencent.common.Util;
import com.tencent.protocol.unified_order_protocol.UnifiedOrderReqData;
import com.tencent.protocol.unified_order_protocol.UnifiedOrderResData;
import com.tencent.service.UnifiedOrderService;

public class UnifiedOrderBusiness {
    //打log用
    private static Log log = new Log(LoggerFactory.getLogger(ReverseBusiness.class));

    private UnifiedOrderService unifiedOrderService;
    
    private String key = null;
	 
    public UnifiedOrderBusiness(String certLocalPath,String certPassword,String keyPartner) throws IllegalAccessException, ClassNotFoundException, InstantiationException, UnrecoverableKeyException, KeyManagementException, NoSuchAlgorithmException, KeyStoreException, IOException {
    	key = keyPartner;
    	unifiedOrderService = new UnifiedOrderService(certLocalPath,certPassword);
    }

    public interface ResultListener {

        //API返回ReturnCode不合法，支付请求逻辑错误，请仔细检测传过去的每一个参数是否合法，或是看API能否被正常访问
        void onFailByReturnCodeError(UnifiedOrderResData unifiedOrderResData);

        //API返回ReturnCode为FAIL，支付API系统返回失败，请检测Post给API的数据是否规范合法
        void onFailByReturnCodeFail(UnifiedOrderResData unifiedOrderResData);

        //撤销请求API返回的数据签名验证失败，有可能数据被篡改了
        void onFailBySignInvalid(UnifiedOrderResData unifiedOrderResData);

        //支付失败
        void onFail(UnifiedOrderResData unifiedOrderResData);

        //支付成功
        void onSuccess(UnifiedOrderResData unifiedOrderResData);

    }
    
    /**
     * 直接执行统一下单业务逻辑 
     *
     * @param scanPayReqData 这个数据对象里面包含了API要求提交的各种数据字段
     * @param resultListener 商户需要自己监听被扫支付业务逻辑可能触发的各种分支事件，并做好合理的响应处理
     * @throws Exception
     */
	public void run(UnifiedOrderReqData unifiedOrderReqData, ResultListener resultListener, String certLocalPath,String certPassword) throws Exception {

        //--------------------------------------------------------------------
        //构造请求“被扫支付API”所需要提交的数据
        //--------------------------------------------------------------------
 

        long costTimeStart = System.currentTimeMillis();

        log.i("支付API返回的数据如下：");
        unifiedOrder(unifiedOrderReqData,resultListener);

        long costTimeEnd = System.currentTimeMillis();
        long totalTimeCost = costTimeEnd - costTimeStart;
        log.i("api请求总耗时：" + totalTimeCost + "ms");

 
 
    }
	/**
	 * 统一下单
	 * @param unifiedOrderReqData
	 * @param resultListener
	 * @return
	 * @throws Exception
	 */
      
    private boolean unifiedOrder(UnifiedOrderReqData unifiedOrderReqData,ResultListener resultListener) throws Exception {

        String responseString;
 
        responseString = unifiedOrderService.request(unifiedOrderReqData);

        log.i("统一下单API返回的数据如下：");
        log.i(responseString);
        //将从API返回的XML数据映射到Java对象
        UnifiedOrderResData unifiedOrderResData = (UnifiedOrderResData) Util.getObjectFromXML(responseString, UnifiedOrderResData.class);
        
        if (unifiedOrderResData == null) {
            log.i("统一下单请求逻辑错误，请仔细检测传过去的每一个参数是否合法");
            resultListener.onFailByReturnCodeError(unifiedOrderResData);
            return false;
        }
        // 回传商户系统订单ID
        unifiedOrderResData.setOut_trade_no(unifiedOrderReqData.getOut_trade_no());
        
        if (unifiedOrderResData.getReturn_code().equals("FAIL")) {
            //注意：一般这里返回FAIL是出现系统级参数错误，请检测Post给API的数据是否规范合法
            log.i("统一下单API系统返回失败，失败信息为：" + unifiedOrderResData.getReturn_msg());
            resultListener.onFailByReturnCodeFail(unifiedOrderResData);
            return false;
        } else {

            if (!Signature.checkIsSignValidFromResponseString(responseString,key)) {
                log.e("【支付失败】支付请求API返回的数据签名验证失败，有可能数据被篡改了");
                resultListener.onFailBySignInvalid(unifiedOrderResData);
                return false;
            }

            if (unifiedOrderResData.getResult_code().equals("FAIL")) {
                log.i("统一下单出错，错误码：" + unifiedOrderResData.getErr_code() + "     错误信息：" + unifiedOrderResData.getErr_code_des());
                resultListener.onFail(unifiedOrderResData);
                return false;            
            } else {
                //查询成功，打印交易状态
                log.i("统一下单成功");
                resultListener.onSuccess(unifiedOrderResData);
                return true;
            }
        }
    }

}
