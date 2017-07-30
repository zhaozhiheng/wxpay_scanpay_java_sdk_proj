package com.tencent.business;

import static java.lang.Thread.sleep;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;

import org.slf4j.LoggerFactory;

import com.tencent.common.Log;
import com.tencent.common.Signature;
import com.tencent.common.Util;
import com.tencent.protocol.reverse_protocol.ReverseReqData;
import com.tencent.protocol.reverse_protocol.ReverseResData;
import com.tencent.service.ReverseService;

/**
 * User: zhaozh
 * Date: 2016/11/20
 * Time: 17:05
 */
public class ReverseBusiness {
    
    public ReverseBusiness(String certLocalPath,String certPassword,String keyPartner) 
        throws IllegalAccessException, ClassNotFoundException, InstantiationException, UnrecoverableKeyException, KeyManagementException, NoSuchAlgorithmException, KeyStoreException, IOException {
    	key = keyPartner;
        reverseService = new ReverseService(certLocalPath,certPassword);
    }

    public interface ResultListener {

        //API返回ReturnCode不合法，撤销请求逻辑错误，请仔细检测传过去的每一个参数是否合法，或是看API能否被正常访问
        void onFailByReturnCodeError(ReverseResData reverseResData);

        //API返回ReturnCode为FAIL，撤销API系统返回失败，请检测Post给API的数据是否规范合法
        void onFailByReturnCodeFail(ReverseResData reverseResData);

        //撤销请求API返回的数据签名验证失败，有可能数据被篡改了
        void onFailByReverseSignInvalid(ReverseResData reverseResData);

        //撤销失败
        void onFail(ReverseResData reverseResData, String outTradeNo);

        //撤销成功
        void onSuccess(ReverseResData reverseResData, String outTradeNo);

    }

    //打log用
    private static Log log = new Log(LoggerFactory.getLogger(ReverseBusiness.class));

    //每次调用撤销API的等待时间
    private int waitingTimeBeforeReverseServiceInvoked = 5000;

    private ReverseService reverseService;
    
    private String key = null;
    
    //是否需要再调一次撤销，这个值由撤销API回包的recall字段决定
    private boolean needRecallReverse = false;

    /**
     * 直接执行撤销业务逻辑
     */
	public void run(ReverseReqData reverseReqData, ResultListener resultListener, String certLocalPath,String certPassword) throws Exception {
        //--------------------------------------------------------------------
        //构造请求“撤销API”所需要提交的数据
        //--------------------------------------------------------------------

        String outTradeNo = reverseReqData.getOut_trade_no();
        String appId = reverseReqData.getAppid();  
        String mchId = reverseReqData.getMch_id();
        String subMchId = reverseReqData.getSub_mch_id();

        long costTimeStart = System.currentTimeMillis();

        log.i("支付API返回的数据如下：");
        doReverseLoop(outTradeNo,resultListener,appId,mchId,subMchId);

        long costTimeEnd = System.currentTimeMillis();
        long totalTimeCost = costTimeEnd - costTimeStart;
        log.i("api请求总耗时：" + totalTimeCost + "ms");
    }

    /**
     * 进行一次撤销操作
     */
    private boolean doOneReverse(String outTradeNo,ResultListener resultListener,String appId,String mchId,String subMchId) throws Exception {
        String reverseResponseString;

        ReverseReqData reverseReqData = new ReverseReqData("",outTradeNo,this.key,appId,mchId,subMchId);
        reverseResponseString = reverseService.request(reverseReqData);

        log.i("撤销API返回的数据如下：");
        log.i(reverseResponseString);
        //将从API返回的XML数据映射到Java对象
        ReverseResData reverseResData = (ReverseResData) Util.getObjectFromXML(reverseResponseString, ReverseResData.class);
        if (reverseResData == null) {
            log.i("支付订单撤销请求逻辑错误，请仔细检测传过去的每一个参数是否合法");
            sleep(waitingTimeBeforeReverseServiceInvoked);//等待一定时间再进行撤销，避免状态还没来得及被更新
            return false;
        }
        if (reverseResData.getReturn_code().equals("FAIL")) {
            //注意：一般这里返回FAIL是出现系统级参数错误，请检测Post给API的数据是否规范合法
            log.i("支付订单撤销API系统返回失败，失败信息为：" + reverseResData.getReturn_msg());
            sleep(waitingTimeBeforeReverseServiceInvoked);//等待一定时间再进行撤销，避免状态还没来得及被更新
            return false;
        } else {
            if (!Signature.checkIsSignValidFromResponseString(reverseResponseString,key)) {
                log.e("【支付失败】支付请求API返回的数据签名验证失败，有可能数据被篡改了");
                resultListener.onFailByReverseSignInvalid(reverseResData);
                needRecallReverse = false;//数据被窜改了，不需要再重试
                sleep(waitingTimeBeforeReverseServiceInvoked);//等待一定时间再进行撤销，避免状态还没来得及被更新
                return false;
            }

            if (reverseResData.getResult_code().equals("FAIL")) {
                log.i("撤销出错，错误码：" + reverseResData.getErr_code() + "     错误信息：" + reverseResData.getErr_code_des());
                if (reverseResData.getRecall().equals("Y")) {
                    //表示需要重试
                    needRecallReverse = true;
                    sleep(waitingTimeBeforeReverseServiceInvoked);//等待一定时间再进行撤销，避免状态还没来得及被更新
                    return false;
                } else {
                    //表示不需要重试，也可以当作是撤销成功
                    needRecallReverse = false;
                    resultListener.onFail(reverseResData, outTradeNo);
                    return true;
                }
            } else {
                //查询成功，打印交易状态
                log.i("支付订单撤销成功");
                resultListener.onSuccess(reverseResData, outTradeNo);
                return true;
            }
        }
    }

    /**
     * 由于有的时候是因为服务延时，所以需要商户每隔一段时间（建议5秒）后再进行查询操作，是否需要继续循环调用撤销API由撤销API回包里面的recall字段决定。
     *
     * @param outTradeNo    商户系统内部的订单号,32个字符内可包含字母, [确保在商户系统唯一]
     * @param resultListener 商户需要自己监听被扫支付业务逻辑可能触发的各种分支事件，并做好合理的响应处理
     * @throws InterruptedException
     */
    private void doReverseLoop(String outTradeNo,ResultListener resultListener,String appId,String mchId,String subMchId) throws Exception {
        //初始化这个标记
        needRecallReverse = true;
        //进行循环撤销，直到撤销成功，或是API返回recall字段为"Y"
        while (needRecallReverse) {
            if (doOneReverse(outTradeNo,resultListener,appId,mchId,subMchId)) {
                return;
            }
        }
    }

    /**
     * 设置循环多次调用撤销API的时间间隔
     *
     * @param duration 时间间隔，默认为5秒
     */
    public void setWaitingTimeBeforeReverseServiceInvoked(int duration) {
        waitingTimeBeforeReverseServiceInvoked = duration;
    }
 
    public void setReverseService(ReverseService service) {
        reverseService = service;
    }

}
