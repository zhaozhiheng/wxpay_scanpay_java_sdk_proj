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
	
	/**发送模版消息， 需要替换参数 ACCESS_TOKEN*/
    public static String SEND_TEMPLATE_MESSAGE = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=ACCESS_TOKEN";
	

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
