package com.tencent;

import com.tencent.business.CloseOrderBusiness;
import com.tencent.business.DownloadBillBusiness;
import com.tencent.business.OrderQueryBusiness;
import com.tencent.business.RefundBusiness;
import com.tencent.business.RefundQueryBusiness;
import com.tencent.business.ReverseBusiness;
import com.tencent.business.ScanPayBusiness;
import com.tencent.business.UnifiedOrderBusiness;
import com.tencent.protocol.appid.base_access_token_protocol.GetBaseAccessTokenReqData;
import com.tencent.protocol.close_order_protocol.CloseOrderReqData;
import com.tencent.protocol.downloadbill_protocol.DownloadBillReqData;
import com.tencent.protocol.pay_protocol.ScanPayReqData;
import com.tencent.protocol.pay_query_protocol.ScanPayQueryReqData;
import com.tencent.protocol.refund_protocol.RefundReqData;
import com.tencent.protocol.refund_query_protocol.RefundQueryReqData;
import com.tencent.protocol.reverse_protocol.ReverseReqData;
import com.tencent.protocol.appid.send_template_msg_protocol.SendTemplateMsgReqData;
import com.tencent.protocol.appid.sns_access_token_protocol.GetAuthAccessTokenReqData;
import com.tencent.protocol.appid.sns_userinfo_protocol.GetUserinfoReqData;
import com.tencent.protocol.unified_order_protocol.UnifiedOrderReqData;
import com.tencent.service.DownloadBillService;
import com.tencent.service.RefundQueryService;
import com.tencent.service.RefundService;
import com.tencent.service.ReverseService;
import com.tencent.service.ScanPayQueryService;
import com.tencent.service.ScanPayService;
import com.tencent.service.UnifiedOrderService;
import com.tencent.service.appid.GetAuthAccessTokenService;
import com.tencent.service.appid.GetBaseAccessTokenService;
import com.tencent.service.appid.GetUserinfoService;
import com.tencent.service.appid.SendTemplateMsgService;

/**
 * SDK总入口
 */
public class WXPay {

    /**
     * 初始化SDK依赖的几个关键配置
     * @param key 签名算法需要用到的秘钥
     * @param appID 公众账号ID
     * @param mchID 商户ID
     * @param sdbMchID 子商户ID，受理模式必填
     * @param certLocalPath HTTP证书在服务器中的路径，用来加载证书用
     * @param certPassword HTTP证书的密码，默认等于MCHID
      
    public static void initSDKConfiguration(String key,String appID,String mchID,String sdbMchID,String certLocalPath,String certPassword){
        Configure.setKey(key);
        Configure.setAppID(appID);
        Configure.setMchID(mchID);
        Configure.setSubMchID(sdbMchID);
        Configure.setCertLocalPath(certLocalPath);
        Configure.setCertPassword(certPassword);
    }*/ 

    /**
     * 直接执行被扫支付业务逻辑（包含最佳实践流程）
     * @param scanPayReqData 这个数据对象里面包含了API要求提交的各种数据字段
     * @param resultListener 商户需要自己监听被扫支付业务逻辑可能触发的各种分支事件，并做好合理的响应处理
     * @param certLocalPath 服务商证书路径
     * @param certPassword 服务商证书密码 
     * @param keyPartner
     * @throws Exception
     */
    public static void doScanPayBusiness(ScanPayReqData scanPayReqData, ScanPayBusiness.ResultListener resultListener,String certLocalPath,String certPassword,String keyPartner) throws Exception {
        new ScanPayBusiness(certLocalPath,certPassword,keyPartner).run(scanPayReqData, resultListener,certLocalPath,certPassword);
    }

    /**
     * 调用退款业务逻辑
     * @param refundReqData 这个数据对象里面包含了API要求提交的各种数据字段
     * @param resultListener 业务逻辑可能走到的结果分支，需要商户处理
     * @param certLocalPath 服务商证书路径
     * @param certPassword 服务商证书密码 
     * @param keyPartner
     * @throws Exception
     */
    public static void doRefundBusiness(RefundReqData refundReqData, RefundBusiness.ResultListener resultListener,String certLocalPath,String certPassword,String keyPartner) throws Exception {
        new RefundBusiness(certLocalPath,certPassword,keyPartner).run(refundReqData, resultListener,certLocalPath,certPassword);
    }

    /**
     * 运行退款查询的业务逻辑
     * @param refundQueryReqData 这个数据对象里面包含了API要求提交的各种数据字段
     * @param resultListener 商户需要自己监听退款查询业务逻辑可能触发的各种分支事件，并做好合理的响应处理
     * @param certLocalPath 服务商证书路径
     * @param certPassword 服务商证书密码 
     * @param keyPartner
     * @throws Exception
     */
    public static void doRefundQueryBusiness(RefundQueryReqData refundQueryReqData,RefundQueryBusiness.ResultListener resultListener,String certLocalPath,String certPassword,String keyPartner) throws Exception {
        new RefundQueryBusiness(certLocalPath,certPassword,keyPartner).run(refundQueryReqData,resultListener,certLocalPath,certPassword);
    }

    /**
     * 请求对账单下载服务
     * @param downloadBillReqData 这个数据对象里面包含了API要求提交的各种数据字段
     * @param resultListener 商户需要自己监听账单下载业务逻辑可能触发的各种分支事件，并做好合理的响应处理
     * @param certLocalPath 服务商证书路径
     * @param certPassword 服务商证书密码 
     * @param keyPartner
     * @return API返回的XML数据
     * @throws Exception
     */
    public static void doDownloadBillBusiness(DownloadBillReqData downloadBillReqData,DownloadBillBusiness.ResultListener resultListener,String certLocalPath,String certPassword,String keyPartner) throws Exception {
        new DownloadBillBusiness(certLocalPath,certPassword,keyPartner).run(downloadBillReqData,resultListener,certLocalPath,certPassword);
    }
    
    /**
     * 直接执行被撤销订单
     * @param scanPayReqData 这个数据对象里面包含了API要求提交的各种数据字段
     * @param resultListener 商户需要自己监听撤销订单业务逻辑可能触发的各种分支事件，并做好合理的响应处理
     * @param certLocalPath 服务商证书路径
     * @param certPassword 服务商证书密码 
     * @param keyPartner
     * @throws Exception
     */
    public static void doReverseBusiness(ReverseReqData reverseReqData, ReverseBusiness.ResultListener resultListener,String certLocalPath,String certPassword,String keyPartner) throws Exception {
        new ReverseBusiness(certLocalPath,certPassword,keyPartner).run(reverseReqData, resultListener,certLocalPath,certPassword);
    }

	
	

    /**
     * 请求支付服务
     * @param scanPayReqData 这个数据对象里面包含了API要求提交的各种数据字段
     * @param certLocalPath 服务商证书路径
     * @param certPassword 服务商证书密码 
     * @return API返回的数据
     * @throws Exception
     */
    public static String requestScanPayService(ScanPayReqData scanPayReqData,String certLocalPath,String certPassword) throws Exception{
        return new ScanPayService(certLocalPath,certPassword).request(scanPayReqData);
    }

    /**
     * 请求支付查询服务
     * @param scanPayQueryReqData 这个数据对象里面包含了API要求提交的各种数据字段
     * @param certLocalPath 服务商证书路径
     * @param certPassword 服务商证书密码 
     * @return API返回的XML数据
     * @throws Exception
     */
	public static String requestScanPayQueryService(ScanPayQueryReqData scanPayQueryReqData,String certLocalPath,String certPassword,String keyPartner) throws Exception{
		return new ScanPayQueryService(certLocalPath,certPassword).request(scanPayQueryReqData);
	}

    /**
     * 请求退款服务
     * @param refundReqData 这个数据对象里面包含了API要求提交的各种数据字段
     * @param certLocalPath 服务商证书路径
     * @param certPassword 服务商证书密码 
     * @return API返回的XML数据
     * @throws Exception
     */
    public static String requestRefundService(RefundReqData refundReqData,String certLocalPath,String certPassword) throws Exception{
        return new RefundService(certLocalPath,certPassword).request(refundReqData);        
    }

    /**
     * 请求退款查询服务
     * @param refundQueryReqData 这个数据对象里面包含了API要求提交的各种数据字段
     * @param certLocalPath 服务商证书路径
     * @param certPassword 服务商证书密码 
     * @return API返回的XML数据
     * @throws Exception
     */
	public static String requestRefundQueryService(RefundQueryReqData refundQueryReqData,String certLocalPath,String certPassword) throws Exception{
		return new RefundQueryService(certLocalPath,certPassword).request(refundQueryReqData);		
	}

    /**
     * 请求撤销服务
     * @param reverseReqData 这个数据对象里面包含了API要求提交的各种数据字段
     * @param certLocalPath 服务商证书路径
     * @param certPassword 服务商证书密码 
     * @return API返回的XML数据
     * @throws Exception
     */
	public static String requestReverseService(ReverseReqData reverseReqData,String certLocalPath,String certPassword) throws Exception{
		return new ReverseService(certLocalPath,certPassword).request(reverseReqData);
	}

    /**
     * 请求对账单下载服务
     * @param downloadBillReqData 这个数据对象里面包含了API要求提交的各种数据字段
     * @param certLocalPath 服务商证书路径
     * @param certPassword 服务商证书密码
     * @return API返回的XML数据
     * @throws Exception
     */
    public static String requestDownloadBillService(DownloadBillReqData downloadBillReqData,String certLocalPath,String certPassword) throws Exception{
        return new DownloadBillService(certLocalPath,certPassword).request(downloadBillReqData);
    }

    /**
     * 请求统一下单服务
     * @param unifiedOrderReqData 这个数据对象里面包含了API要求提交的各种数据字段
     * @param certLocalPath 服务商证书路径
     * @param certPassword 服务商证书密码
     * @return API返回的数据
     * @throws Exception
     */
    public static String requestUnifiedOrderService(UnifiedOrderReqData unifiedOrderReqData,String certLocalPath,String certPassword) throws Exception{
        return new UnifiedOrderService(certLocalPath,certPassword).request(unifiedOrderReqData);
    }
    
    /**
     * 直接执行统一下单业务逻辑 
     * @param unifiedOrderReqData 这个数据对象里面包含了API要求提交的各种数据字段
     * @param resultListener 商户需要自己监听统一下单业务逻辑可能触发的各种分支事件，并做好合理的响应处理
     * @param certLocalPath 服务商证书路径
     * @param certPassword 服务商证书密码 
     * @param keyPartner
     * @throws Exception
     */
    public static void doUnifiedOrderBusiness(UnifiedOrderReqData unifiedOrderReqData, UnifiedOrderBusiness.ResultListener resultListener,String certLocalPath,String certPassword,String keyPartner) throws Exception {
        new UnifiedOrderBusiness(certLocalPath,certPassword,keyPartner).run(unifiedOrderReqData, resultListener,certLocalPath,certPassword);
    }
    
    /**
     * 直接执行订单查询业务逻辑，请求参数和扫码查询请求包通用
     * @param orderQueryReqData 这个数据对象里面包含了API要求提交的各种数据字段
     * @param resultListener 商户需要自己监听关闭订单业务逻辑可能触发的各种分支事件，并做好合理的响应处理
     * @param certLocalPath 服务商证书路径
     * @param certPassword 服务商证书密码 
     * @param keyPartner
     * @throws Exception
     */
    public static void doOrderQueryBusiness(ScanPayQueryReqData orderQueryReqData, OrderQueryBusiness.ResultListener resultListener,String certLocalPath,String certPassword,String keyPartner) throws Exception {
        new OrderQueryBusiness(certLocalPath,certPassword,keyPartner).run(orderQueryReqData, resultListener,certLocalPath,certPassword);
    }
    
    /**
     * 直接执行统一下单业务逻辑 
     * @param unifiedOrderReqData 这个数据对象里面包含了API要求提交的各种数据字段
     * @param resultListener 商户需要自己监听统一下单业务逻辑可能触发的各种分支事件，并做好合理的响应处理
     * @param certLocalPath 服务商证书路径
     * @param certPassword 服务商证书密码 
     * @param keyPartner
     * @throws Exception
     */
    public static void doCloseOrderBusiness(CloseOrderReqData closeOrderReqData, CloseOrderBusiness.ResultListener resultListener,String certLocalPath,String certPassword,String keyPartner) throws Exception {
        new CloseOrderBusiness(certLocalPath,certPassword,keyPartner).run(closeOrderReqData, resultListener,certLocalPath,certPassword);
    }
    
    /**
     * 请求获取/刷新基础支持的AccessToken
     * @param getBaseAccessTokenReqData 这个数据对象里面包含了API要求提交的各种数据字段
     * @param certLocalPath 服务商证书路径
     * @param certPassword 服务商证书密码 
     * @return API返回的JSON数据
     * @throws Exception
     */
    public static String requestGetBaseAccessTokenService(GetBaseAccessTokenReqData getBaseAccessTokenReqData,String certLocalPath,String certPassword) throws Exception{
        return new GetBaseAccessTokenService(certLocalPath,certPassword).request(getBaseAccessTokenReqData);
    }
    
    /**
     * 发送模版消息
     * @param getBaseAccessTokenReqData 这个数据对象里面包含了API要求提交的各种数据字段
     * @param certLocalPath 服务商证书路径
     * @param certPassword 服务商证书密码 
     * @return API返回的JSON数据
     * @throws Exception
     */
    public static String requestSendTemplateMsgService(SendTemplateMsgReqData sendTemplateMsgReqData, String accessToken, String certLocalPath,String certPassword) throws Exception{
        return new SendTemplateMsgService(certLocalPath,certPassword).request(sendTemplateMsgReqData, accessToken);
    }
    
    /**
     * 请求网页授权获取/刷新  AccessToken、openid
     * @param getAuthAccessTokenReqData 这个数据对象里面包含了API要求提交的各种数据字段
     * @param certLocalPath 服务商证书路径
     * @param certPassword 服务商证书密码 
     * @return API返回的JSON数据
     * @throws Exception
     */
    public static String requestGetAuthAccessTokenService(GetAuthAccessTokenReqData getAuthAccessTokenReqData,String certLocalPath,String certPassword) throws Exception{
        return new GetAuthAccessTokenService(certLocalPath,certPassword).request(getAuthAccessTokenReqData);
    }
    
    /**
     * 请求网页授权拉取用户信息
     * @param getUserinfoReqData 这个数据对象里面包含了API要求提交的各种数据字段
     * @param certLocalPath 服务商证书路径
     * @param certPassword 服务商证书密码 
     * @return API返回的JSON数据
     * @throws Exception
     */
    public static String requestGetUserinfoService(GetUserinfoReqData getUserinfoReqData,String certLocalPath,String certPassword) throws Exception{
        return new GetUserinfoService(certLocalPath,certPassword).request(getUserinfoReqData);
    }
    
}
