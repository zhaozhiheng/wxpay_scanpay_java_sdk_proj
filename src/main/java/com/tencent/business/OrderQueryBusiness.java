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
import com.tencent.protocol.pay_query_protocol.ScanPayQueryReqData;
import com.tencent.protocol.pay_query_protocol.ScanPayQueryResData;
import com.tencent.service.ScanPayQueryService;

public class OrderQueryBusiness {
    //打log用
    private static Log log = new Log(LoggerFactory.getLogger(OrderQueryBusiness.class));

    private ScanPayQueryService scanPayQueryService;
    
    private String key = null;
	 
    public OrderQueryBusiness(String certLocalPath,String certPassword,String keyPartner) throws IllegalAccessException, ClassNotFoundException, InstantiationException, UnrecoverableKeyException, KeyManagementException, NoSuchAlgorithmException, KeyStoreException, IOException {
    	key = keyPartner;
    	scanPayQueryService = new ScanPayQueryService(certLocalPath,certPassword);
    }

    public interface ResultListener{
        //API返回ReturnCode不合法，支付请求逻辑错误，请仔细检测传过去的每一个参数是否合法，或是看API能否被正常访问
        void onFailByReturnCodeError(ScanPayQueryResData scanPayQueryResData);

        //API返回ReturnCode为FAIL，支付API系统返回失败，请检测Post给API的数据是否规范合法
        void onFailByReturnCodeFail(ScanPayQueryResData scanPayQueryResData);

        //支付请求API返回的数据签名验证失败，有可能数据被篡改了
        void onFailBySignInvalid(ScanPayQueryResData scanPayQueryResData);

        //订单查询失败
        void onOrderQueryFail(ScanPayQueryResData scanPayQueryResData);

        //订单查询成功
        void onOrderQuerySuccess(ScanPayQueryResData scanPayQueryResData);

    }
    
    /**
     * 直接执行订单查询业务逻辑 
     *
     * @param scanPayReqData 这个数据对象里面包含了API要求提交的各种数据字段
     * @param resultListener 商户需要自己监听订单查询业务逻辑可能触发的各种分支事件，并做好合理的响应处理
     * @throws Exception
     */
	public void run(ScanPayQueryReqData scanPayQueryReqData, ResultListener resultListener, String certLocalPath,String certPassword) throws Exception {

        //--------------------------------------------------------------------
        //构造请求“订单查询”所需要提交的数据
        //--------------------------------------------------------------------
 

        long costTimeStart = System.currentTimeMillis();

        log.i("支付API返回的数据如下：");
        orderQuery(scanPayQueryReqData,resultListener);

        long costTimeEnd = System.currentTimeMillis();
        long totalTimeCost = costTimeEnd - costTimeStart;
        log.i("api请求总耗时：" + totalTimeCost + "ms");
 
    }
	/**
	 * 订单查询
	 * @param scanPayQueryReqData
	 * @param resultListener
	 * @return
	 * @throws Exception
	 */
      
    private boolean orderQuery(ScanPayQueryReqData orderQueryReqData, ResultListener resultListener)
        throws Exception {

        String responseString = scanPayQueryService.request(orderQueryReqData);

        log.i("订单查询API返回的数据如下：");
        log.i(responseString);
        // 将从API返回的XML数据映射到Java对象
        ScanPayQueryResData orderQueryResData = (ScanPayQueryResData) Util.getObjectFromXML(responseString, ScanPayQueryResData.class);

        if (orderQueryResData == null) {
            log.i("订单查询请求逻辑错误，请仔细检测传过去的每一个参数是否合法");
            resultListener.onFailByReturnCodeError(orderQueryResData);
            return false;
        }
        // 回传商户系统订单ID
        orderQueryResData.setOut_trade_no(orderQueryReqData.getOut_trade_no());

        if (orderQueryResData.getReturn_code().equals("FAIL")) {
            // 注意：一般这里返回FAIL是出现系统级参数错误，请检测Post给API的数据是否规范合法
            log.i("订单查询API系统返回失败，失败信息为：" + orderQueryResData.getReturn_msg());
            resultListener.onFailByReturnCodeFail(orderQueryResData);
            return false;
        } else {
            if (!Signature.checkIsSignValidFromResponseString(responseString, key)) {
                log.e("【订单查询失败】订单查询请求API返回的数据签名验证失败，有可能数据被篡改了");
                resultListener.onFailBySignInvalid(orderQueryResData);
                return false;
            }

            if (orderQueryResData.getResult_code().equals("FAIL")) {
                log.i("订单查询出错，错误码：" + orderQueryResData.getErr_code() + "     错误信息：" + orderQueryResData.getErr_code_des());
                resultListener.onOrderQueryFail(orderQueryResData);
                return false;
            } else {
                // 查询成功，打印交易状态
                log.i("订单查询成功");
                resultListener.onOrderQuerySuccess(orderQueryResData);
                return true;
            }
        }
    }

}
