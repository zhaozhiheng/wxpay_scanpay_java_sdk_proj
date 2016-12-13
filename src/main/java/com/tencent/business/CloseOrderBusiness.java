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
import com.tencent.protocol.close_order_protocol.CloseOrderReqData;
import com.tencent.protocol.close_order_protocol.CloseOrderResData;
import com.tencent.service.CloseOrderService;

public class CloseOrderBusiness {
    //打log用
    private static Log log = new Log(LoggerFactory.getLogger(CloseOrderBusiness.class));

    private CloseOrderService closeOrderService;
    
    private String key = null;
	 
    public CloseOrderBusiness(String certLocalPath,String certPassword,String keyPartner) throws IllegalAccessException, ClassNotFoundException, InstantiationException, UnrecoverableKeyException, KeyManagementException, NoSuchAlgorithmException, KeyStoreException, IOException {
    	key = keyPartner;
    	closeOrderService = new CloseOrderService(certLocalPath,certPassword);
    }

    public interface ResultListener{
        //API返回ReturnCode不合法，关闭订单请求逻辑错误，请仔细检测传过去的每一个参数是否合法，或是看API能否被正常访问
        void onFailByReturnCodeError(CloseOrderResData closeOrderResData);

        //API返回ReturnCode为FAIL，关闭订单API系统返回失败，请检测Post给API的数据是否规范合法
        void onFailByReturnCodeFail(CloseOrderResData closeOrderResData);

        //关闭订单请求API返回的数据签名验证失败，有可能数据被篡改了
        void onFailBySignInvalid(CloseOrderResData closeOrderResData);

        //关闭订单失败
        void onCloseOrderFail(CloseOrderResData closeOrderResData);

        //关闭订单成功
        void onCloseOrderSuccess(CloseOrderResData closeOrderResData);

    }
    
    /**
     * 直接执行关闭订单业务逻辑 
     *
     * @param closeOrderReqData 这个数据对象里面包含了API要求提交的各种数据字段
     * @param resultListener 商户需要自己监听关闭订单业务逻辑可能触发的各种分支事件，并做好合理的响应处理
     * @throws Exception
     */
	public void run(CloseOrderReqData closeOrderReqData, ResultListener resultListener, String certLocalPath,String certPassword) throws Exception {

        //--------------------------------------------------------------------
        //构造请求“关闭订单API”所需要提交的数据
        //--------------------------------------------------------------------
 
        long costTimeStart = System.currentTimeMillis();

        log.i("关闭订单API返回的数据如下：");
        closeOrder(closeOrderReqData,resultListener);

        long costTimeEnd = System.currentTimeMillis();
        long totalTimeCost = costTimeEnd - costTimeStart;
        log.i("api请求总耗时：" + totalTimeCost + "ms");
 
    }
	/**
	 * 关闭订单
	 * @param closeOrderReqData
	 * @param resultListener
	 * @return
	 * @throws Exception
	 */
      
    private boolean closeOrder(CloseOrderReqData closeOrderReqData, ResultListener resultListener)
        throws Exception {

        String responseString = closeOrderService.request(closeOrderReqData);

        log.i("关闭订单API返回的数据如下：");
        log.i(responseString);
        // 将从API返回的XML数据映射到Java对象
        CloseOrderResData closeOrderResData = (CloseOrderResData) Util.getObjectFromXML(responseString, CloseOrderResData.class);

        if (closeOrderResData == null) {
            log.i("关闭订单请求逻辑错误，请仔细检测传过去的每一个参数是否合法");
            resultListener.onFailByReturnCodeError(closeOrderResData);
            return false;
        }
        // 回传商户系统订单ID
        closeOrderResData.setOut_trade_no(closeOrderReqData.getOut_trade_no());

        if (closeOrderResData.getReturn_code().equals("FAIL")) {
            // 注意：一般这里返回FAIL是出现系统级参数错误，请检测Post给API的数据是否规范合法
            log.i("关闭订单API系统返回失败，失败信息为：" + closeOrderResData.getReturn_msg());
            resultListener.onFailByReturnCodeFail(closeOrderResData);
            return false;
        } else {
            if (!Signature.checkIsSignValidFromResponseString(responseString, key)) {
                log.e("【关闭订单失败】关闭订单请求API返回的数据签名验证失败，有可能数据被篡改了");
                resultListener.onFailBySignInvalid(closeOrderResData);
                return false;
            }
            if (closeOrderResData.getResult_code().equals("FAIL")) {// 微信官方给的说明没有此参数，实际是有点的
            //if (closeOrderResData.getErr_code() != null && !"".equals(closeOrderResData.getErr_code())) {
                log.i("关闭订单出错，错误码：" + closeOrderResData.getErr_code() + "     错误信息：" + closeOrderResData.getErr_code_des());
                resultListener.onCloseOrderFail(closeOrderResData);
                return false;
            } else {
                // 查询成功，打印交易状态
                log.i("关闭订单成功");
                resultListener.onCloseOrderSuccess(closeOrderResData);
                return true;
            }
        }
    }

}
