package com.tencent.common;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.SocketTimeoutException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.zip.GZIPInputStream;

import javax.net.ssl.SSLContext;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.GzipDecompressingEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.ConnectionPoolTimeoutException;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.tencent.service.IServiceRequest;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.io.xml.XmlFriendlyNameCoder;

/**
 * User: rizenguo
 * Date: 2014/10/29
 * Time: 14:36
 */
public class HttpsRequest implements IServiceRequest{

    public interface ResultListener {

        public void onConnectionPoolTimeoutError();

    }

    private static Log log = new Log(LoggerFactory.getLogger(HttpsRequest.class));

    //表示请求器是否已经做了初始化工作
    private boolean hasInit = false;

    //连接超时时间，默认10秒
    private int socketTimeout = 10000;

    //传输超时时间，默认30秒
    private int connectTimeout = 30000;

    //请求器的配置
    private RequestConfig requestConfig;

    //HTTP请求器
    private CloseableHttpClient httpClient;
    
    public HttpsRequest(String certLocalPath,String certPassword) throws UnrecoverableKeyException, KeyManagementException, NoSuchAlgorithmException, KeyStoreException, IOException {
        init(certLocalPath,certPassword);
    }

//    public HttpsRequest() throws UnrecoverableKeyException, KeyManagementException, NoSuchAlgorithmException, KeyStoreException, IOException {
//        init();
//    }
    
    // 为适应多服务商修改，牺牲了程序的灵活性 
    private void init(String certLocalPath,String certPassword) throws IOException, KeyStoreException, UnrecoverableKeyException, NoSuchAlgorithmException, KeyManagementException {

        KeyStore keyStore = KeyStore.getInstance("PKCS12");
        FileInputStream instream = new FileInputStream(new File(certLocalPath));//加载本地的证书进行https加密传输
        try {
            keyStore.load(instream, certPassword.toCharArray());//设置证书密码
        } catch (CertificateException e) {
        	log.e("SSL证书异常:"+certLocalPath);
        } catch (NoSuchAlgorithmException e) {
        	log.e("SSL证书不可用:"+certLocalPath);
        } finally {
            instream.close();
        }

        // Trust own CA and all self-signed certs
        SSLContext sslcontext = SSLContexts.custom()
                .loadKeyMaterial(keyStore, certPassword.toCharArray())
                .build();
        // Allow TLSv1 protocol only
        SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
                sslcontext,
                new String[]{"TLSv1"},
                null,
                SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);

        httpClient = HttpClients.custom()
                .setSSLSocketFactory(sslsf)
                .build();

        //根据默认超时限制初始化requestConfig
        requestConfig = RequestConfig.custom().setSocketTimeout(socketTimeout).setConnectTimeout(connectTimeout).build();

        hasInit = true;
    }

//    private void init() throws IOException, KeyStoreException, UnrecoverableKeyException, NoSuchAlgorithmException, KeyManagementException {
//
//        KeyStore keyStore = KeyStore.getInstance("PKCS12");
//        FileInputStream instream = new FileInputStream(new File(Configure.getCertLocalPath()));//加载本地的证书进行https加密传输
//        try {
//            keyStore.load(instream, Configure.getCertPassword().toCharArray());//设置证书密码
//        } catch (CertificateException e) {
//            e.printStackTrace();
//        } catch (NoSuchAlgorithmException e) {
//            e.printStackTrace();
//        } finally {
//            instream.close();
//        }
//
//        // Trust own CA and all self-signed certs
//        SSLContext sslcontext = SSLContexts.custom()
//                .loadKeyMaterial(keyStore, Configure.getCertPassword().toCharArray())
//                .build();
//        // Allow TLSv1 protocol only
//        SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
//                sslcontext,
//                new String[]{"TLSv1"},
//                null,
//                SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
//
//        httpClient = HttpClients.custom()
//                .setSSLSocketFactory(sslsf)
//                .build();
//
//        //根据默认超时限制初始化requestConfig
//        requestConfig = RequestConfig.custom().setSocketTimeout(socketTimeout).setConnectTimeout(connectTimeout).build();
//
//        hasInit = true;
//    }

    /**
     * 通过Https往API post xml数据
     *
     * @param url    API地址
     * @param xmlObj 要提交的XML数据对象
     * @return API回包的实际数据
     * @throws IOException
     * @throws KeyStoreException
     * @throws UnrecoverableKeyException
     * @throws NoSuchAlgorithmException
     * @throws KeyManagementException
     */
    public String sendPost(String url, Object xmlObj) throws IOException, KeyStoreException, UnrecoverableKeyException, NoSuchAlgorithmException, KeyManagementException {

        String result = null;
        
        if (!hasInit) {
//          init();
      	    log.e("请调用新版HttpsRequest初始化函数");
      	    return result;
        }

        HttpPost httpPost = new HttpPost(url);

        //解决XStream对出现双下划线的bug
        XStream xStreamForRequestPostData = new XStream(new DomDriver("UTF-8", new XmlFriendlyNameCoder("-_", "_")));

        //将要提交给API的数据对象转换成XML格式数据Post给API
        String postDataXML = xStreamForRequestPostData.toXML(xmlObj);

        Util.log("API，POST过去的数据是：");
        Util.log(postDataXML);

        //得指明使用UTF-8编码，否则到API服务器XML的中文不能被成功识别
        StringEntity postEntity = new StringEntity(postDataXML, "UTF-8");
        httpPost.addHeader("Content-Type", "text/xml");
        httpPost.setEntity(postEntity);

        //设置请求器的配置
        httpPost.setConfig(requestConfig);

        Util.log("executing request" + httpPost.getRequestLine());

        try {
            HttpResponse response = httpClient.execute(httpPost);

            HttpEntity entity = response.getEntity();
            Header header = entity.getContentType();
            if(header != null && header.getValue().indexOf("gzip")!=-1){
            	InputStream in = entity.getContent();
            	result = unZip(in, "UTF-8");
//            	result = EntityUtils.toString(new GzipDecompressingEntity(entity), "UTF-8");
            } else {
            	result = EntityUtils.toString(entity, "UTF-8");
            }

            

        } catch (ConnectionPoolTimeoutException e) {
            log.e("http post throw ConnectionPoolTimeoutException(wait time out)");
            throw e;
        } catch (ConnectTimeoutException e) {
            log.e("http post throw ConnectTimeoutException");
            throw e;
        } catch (SocketTimeoutException e) {
            log.e("http post throw SocketTimeoutException");
            throw e;
        } catch (Exception e) {
            log.e("http post throw Exception：" + e.getMessage());
            throw e;
        } finally {
            httpPost.abort();
        }
        Util.log("http post json result : " + result);
        return result;
    }
    
    @Override
    public String sendPostJSON(String apiURL, Object jsonObj)
        throws UnrecoverableKeyException, KeyManagementException, NoSuchAlgorithmException, KeyStoreException, IOException {
        String result = null;
        
        if (!hasInit) {
            //          init();
            log.e("请调用新版HttpsRequest初始化函数");
            return result;
        }

        HttpPost httpPost = new HttpPost(apiURL);

        //将要提交给API的数据对象转换成JSON格式数据Post给API
        String postDataJSON = JSON.toJSONString(jsonObj);

        Util.log("API，POST过去的数据是：");
        Util.log(postDataJSON);

        //得指明使用UTF-8编码，否则到API服务器JSON的中文不能被成功识别
        StringEntity postEntity = new StringEntity(postDataJSON, "UTF-8");
        httpPost.addHeader("Content-Type", "application/json");
        httpPost.setEntity(postEntity);

        //设置请求器的配置
        httpPost.setConfig(requestConfig);

        Util.log("executing request" + httpPost.getRequestLine());

        try {
            HttpResponse response = httpClient.execute(httpPost);

            HttpEntity entity = response.getEntity();

            result = EntityUtils.toString(entity, "UTF-8");

        } catch (ConnectionPoolTimeoutException e) {
            log.e("http post throw ConnectionPoolTimeoutException(wait time out)");
            throw e;
        } catch (ConnectTimeoutException e) {
            log.e("http post throw ConnectTimeoutException");
            throw e;
        } catch (SocketTimeoutException e) {
            log.e("http post throw SocketTimeoutException");
            throw e;
        } catch (Exception e) {
            log.e("http post throw Exception：" + e.getMessage());
            throw e;
        } finally {
            httpPost.abort();
        }
        Util.log("http post json result : " + result);
        return result;
    }
    
    @Override
    public String sendGet(String api_url)
        throws UnrecoverableKeyException, KeyManagementException, NoSuchAlgorithmException, KeyStoreException, IOException {
        String result = null;
        
        HttpGet httpGet = new HttpGet(api_url);
        
        //设置请求器的配置
        httpGet.setConfig(requestConfig);
        Util.log("executing request" + httpGet.getRequestLine());
        try {
            HttpResponse response = httpClient.execute(httpGet);
            
            result = EntityUtils.toString(response.getEntity(), "UTF-8");
        } catch (ConnectionPoolTimeoutException e) {
            log.e("http get throw ConnectionPoolTimeoutException(wait time out)");
            throw e;
        } catch (ConnectTimeoutException e) {
            log.e("http get throw ConnectTimeoutException");
            throw e;
        } catch (SocketTimeoutException e) {
            log.e("http get throw SocketTimeoutException");
            throw e;
        } catch (Exception e) {
            log.e("http get throw Exception：" + e.getMessage());
            throw e;
        } finally {
            httpGet.abort();
        }
        Util.log("http get result : " + result);
        return result;
    }

    /**
     * 设置连接超时时间
     *
     * @param socketTimeout 连接时长，默认10秒
     */
    public void setSocketTimeout(int socketTimeout) {
        this.socketTimeout = socketTimeout;
        resetRequestConfig();
    }

    /**
     * 设置传输超时时间
     *
     * @param connectTimeout 传输时长，默认30秒
     */
    public void setConnectTimeout(int connectTimeout) {
        this.connectTimeout = connectTimeout;
        resetRequestConfig();
    }

    private void resetRequestConfig(){
        requestConfig = RequestConfig.custom().setSocketTimeout(socketTimeout).setConnectTimeout(connectTimeout).build();
    }

    /**
     * 允许商户自己做更高级更复杂的请求器配置
     *
     * @param requestConfig 设置HttpsRequest的请求器配置
     */
    public void setRequestConfig(RequestConfig requestConfig) {
        this.requestConfig = requestConfig;
    }
    /**
     * 解压服务器返回的gzip流
     * @param in 抓取返回的InputStream流
     * @param charSet 编码
     * @return String格式
     * @throws IOException
     */
    private String unZip(InputStream in, String charSet) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        GZIPInputStream gis = null;
        try {
            gis = new GZIPInputStream(in);
            byte[] _byte = new byte[1024];
            int len = 0;
            while ((len = gis.read(_byte)) != -1) {
                baos.write(_byte, 0, len);
            }
            String unzipString = new String(baos.toByteArray(), charSet);
            return unzipString;
        } finally {
            if (gis != null) {
                gis.close();
            }
            if(baos != null){
                baos.close();
            }
        }
    }
    
}
