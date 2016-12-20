package com.tencent.common;

/**
 * User: rizenguo
 * Date: 2014/10/29
 * Time: 14:40
 * 这里放置各种配置数据
 */
public class Configure {

	//sdk的版本号
	private static final String sdkVersion = "java sdk 1.0.1";
	
	//是否上报API测速，默认为上报
	private static boolean reportFlag = true;
	
	//是否使用异步线程的方式来上报API测速，默认为异步模式
	private static boolean useThreadToDoReport = true;

	//机器IP
	private static String ip = "";

	//以下是几个API的路径：
	//1）被扫支付API
	public static String PAY_API = "https://api.mch.weixin.qq.com/pay/micropay";

	//2）被扫支付查询API
	public static String PAY_QUERY_API = "https://api.mch.weixin.qq.com/pay/orderquery";

	//3）退款API
	public static String REFUND_API = "https://api.mch.weixin.qq.com/secapi/pay/refund";

	//4）退款查询API
	public static String REFUND_QUERY_API = "https://api.mch.weixin.qq.com/pay/refundquery";

	//5）撤销API
	public static String REVERSE_API = "https://api.mch.weixin.qq.com/secapi/pay/reverse";

	//6）下载对账单API
	public static String DOWNLOAD_BILL_API = "https://api.mch.weixin.qq.com/pay/downloadbill";

	//7) 统计上报API
	public static String REPORT_API = "https://api.mch.weixin.qq.com/payitil/report";
	
	//8) 统一下单API
	public static String UNIFIED_API = "https://api.mch.weixin.qq.com/pay/unifiedorder";
	
	//9) 关闭订单API
	public static String ORDER_CLOSE_API = "https://api.mch.weixin.qq.com/pay/closeorder";
	
	/**------------------------------公众号接口URL----------------------------------*/
	/**获取/刷新基础支持的AccessToken， 需要替换参数 APPID、APPSECRET*/
	public static String GET_BASE_ACCESS_TOKEN = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";
	
    /**
     * 通过code换取网页授权access_token，get方式请求，需要替换参数 APPID、SECRET、CODE（用户统一授权后得到）、grant_type=authorization_code；
     */
    public static final String JSAPI_GET_ACCESS_TOKEN = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=APPSECRET&code=CODE&grant_type=authorization_code";
    
    /**
     * 刷新access_token，get方式请求，需要替换参数 APPID、REFRESH_TOKEN（通过code换取网页授权access_token得到的）
     */
    public static final String JSAPI_REFRESH_ACCESS_TOKEN = "https://api.weixin.qq.com/sns/oauth2/refresh_token?appid=APPID&grant_type=refresh_token&refresh_token=REFRESH_TOKEN";
    
    /**
     * 检验授权凭证（access_token）是否有效，需要替换 ACCESS_TOKEN、OPENID
     */
    public static final String JSAPI_CHECK_ACCESS_TOKEN = "https://api.weixin.qq.com/sns/auth?access_token=ACCESS_TOKEN&openid=OPENID";
    
    /**拉取用户信息(需scope为 snsapi_userinfo)，需要替换参数 ACCESS_TOKEN、OPENID */
    public static final String JSAPI_GET_USERINFO = "https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";
 
    /**设置行业可在MP中完成，每月可修改行业1次，账号仅可使用所属行业中相关的模板，为方便第三方开发者，提供通过接口调用的方式来修改账号所属行业*/
    public static final String TEMPLATE_SET_INDUSTRY = "https://api.weixin.qq.com/cgi-bin/template/api_set_industry?access_token=ACCESS_TOKEN";
    
    /**获得模板ID*/
    public static final String TEMPLATE_GET_ID = "https://api.weixin.qq.com/cgi-bin/template/api_add_template?access_token=ACCESS_TOKEN";
    
    /**发送模板消息*/
    public static final String TEMPLATE_SEND_MESSAGE = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=ACCESS_TOKEN";
    
    
	public static boolean isUseThreadToDoReport() {
		return useThreadToDoReport;
	}

	public static void setUseThreadToDoReport(boolean useThreadToDoReport) {
		Configure.useThreadToDoReport = useThreadToDoReport;
	}

	public static String HttpsRequestClassName = "com.tencent.common.HttpsRequest";



	public static void setIp(String ip) {
		Configure.ip = ip;
	}

	public static String getIP(){
		return ip;
	}

	public static void setHttpsRequestClassName(String name){
		HttpsRequestClassName = name;
	}

	public static String getSdkVersion(){
		return sdkVersion;
	}

	public static boolean isReportFlag() {
		return reportFlag;
	}

	public static void setReportFlag(boolean reportFlag) {
		Configure.reportFlag = reportFlag;
	}

}
