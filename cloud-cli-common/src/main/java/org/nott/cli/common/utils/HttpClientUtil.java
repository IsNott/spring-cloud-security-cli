package org.nott.cli.common.utils;


import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @author Nott
 * @date 2024-6-14
 */

@Slf4j
public class HttpClientUtil {

    private static final int DEFAULT_POOL_MAX_TOTAL = 200;
    private static final int DEFAULT_POOL_MAX_PER_ROUTE = 200;

    private static final int DEFAULT_CONNECT_TIMEOUT = 500;
    private static final int DEFAULT_CONNECT_REQUEST_TIMEOUT = 500;
    private static final int DEFAULT_SOCKET_TIMEOUT = 2000;

    private PoolingHttpClientConnectionManager gcm = null;

    private CloseableHttpClient httpClient = null;

    private IdleConnectionMonitorThread idleThread = null;

    // 连接池的最大连接数
    private final int maxTotal;
    // 连接池按route配置的最大连接数
    private final int maxPerRoute;

    // tcp connect的超时时间
    private final int connectTimeout;
    // 从连接池获取连接的超时时间
    private final int connectRequestTimeout;
    // tcp io的读写超时时间
    private final int socketTimeout;

    public HttpClientUtil() {
        this(DEFAULT_POOL_MAX_TOTAL, DEFAULT_POOL_MAX_PER_ROUTE, DEFAULT_CONNECT_TIMEOUT, DEFAULT_CONNECT_REQUEST_TIMEOUT, DEFAULT_SOCKET_TIMEOUT);
    }

    public HttpClientUtil(int maxTotal, int maxPerRoute, int connectTimeout, int connectRequestTimeout, int socketTimeout) {
        this.maxTotal = maxTotal;
        this.maxPerRoute = maxPerRoute;
        this.connectTimeout = connectTimeout;
        this.connectRequestTimeout = connectRequestTimeout;
        this.socketTimeout = socketTimeout;

        Registry<ConnectionSocketFactory> registry = RegistryBuilder.<ConnectionSocketFactory>create()
                .register("http", PlainConnectionSocketFactory.getSocketFactory())
                .register("https", SSLConnectionSocketFactory.getSocketFactory())
                .build();

        this.gcm = new PoolingHttpClientConnectionManager(registry);
        this.gcm.setMaxTotal(this.maxTotal);
        this.gcm.setDefaultMaxPerRoute(this.maxPerRoute);

        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(this.connectTimeout)                     // 设置连接超时
                .setSocketTimeout(this.socketTimeout)                       // 设置读取超时
                .setConnectionRequestTimeout(this.connectRequestTimeout)    // 设置从连接池获取连接实例的超时
                .build();

        HttpClientBuilder httpClientBuilder = HttpClients.custom();
        httpClient = httpClientBuilder
                .setConnectionManager(this.gcm)
                .setDefaultRequestConfig(requestConfig)
                .build();

        // 清除空闲连接线程池
        idleThread = new IdleConnectionMonitorThread(this.gcm);
        idleThread.start();
    }

    protected static HttpClientUtil build(){
        return new HttpClientUtil();
    }

    public static String doGet(String url) {
        return doGet(url, Collections.emptyMap(), Collections.emptyMap());
    }

    public static String doGet(String url, Map<String, Object> params) {
        return doGet(url, Collections.emptyMap(), params);
    }

    public static String doGetWithHeader(String url, Map<String, String> extraHeaders) {
        return doGet(url, extraHeaders, Collections.emptyMap());
    }

    public static String doGet(String url, Map<String,String> headers,Map<String,Object> params){
        // *) 构建GET请求头
        String apiUrl = getUrlWithParams(url, params);
        HttpGet httpGet = new HttpGet(apiUrl);

        // *) 设置header信息
        if ( headers != null && headers.size() > 0 ) {
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                httpGet.addHeader(entry.getKey(), entry.getValue());
            }
        }
        CloseableHttpResponse response = null;
        try {
            log.info("HttpGet Url ========> : [{}]",httpGet.getURI());
            response = HttpClientUtil.build().httpClient.execute(httpGet);
            if (response == null || response.getStatusLine() == null) {
                return null;
            }
            int statusCode = response.getStatusLine().getStatusCode();
            log.info("HttpGet Response code ========> : [{}]",statusCode);
            if ( statusCode == HttpStatus.SC_OK ) {
                HttpEntity entityRes = response.getEntity();
                if (entityRes != null) {
                    String respStr = EntityUtils.toString(entityRes, "UTF-8");
                    log.info("HttpGet Response String ========> : [{}]",respStr);
                    return respStr;
                }
            }
            return null;
        } catch (IOException e) {
        } finally {
            if ( response != null ) {
                try {
                    response.close();
                } catch (IOException e) {
                }
            }
        }
        return null;
    }

    public String doPost(String apiUrl, Map<String, Object> params) {
        return this.doPost(apiUrl, Collections.EMPTY_MAP, params);
    }

    public static String doPost(String apiUrl, Map<String, String> headers, Map<String, Object> params) {

        HttpPost httpPost = new HttpPost(apiUrl);

        // *) 配置请求headers
        if ( headers != null && headers.size() > 0 ) {
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                httpPost.addHeader(entry.getKey(), entry.getValue());
            }
        }

        // *) 配置请求参数
        if ( params != null && params.size() > 0 ) {
            HttpEntity entityReq = getUrlEncodedFormEntity(params);
            httpPost.setEntity(entityReq);
        }


        CloseableHttpResponse response = null;
        try {
            log.info("HttpPost Url ========> : [{}]",httpPost.getURI());
            response = HttpClientUtil.build().httpClient.execute(httpPost);
            if (response == null || response.getStatusLine() == null) {
                return null;
            }

            int statusCode = response.getStatusLine().getStatusCode();
            log.info("HttpPost Response code ========> : [{}]",statusCode);
            if ( statusCode == HttpStatus.SC_OK ) {
                HttpEntity entityRes = response.getEntity();
                if ( entityRes != null ) {
                    String respStr = EntityUtils.toString(entityRes, "UTF-8");
                    log.info("HttpPost Response String ========> : [{}]",respStr);
                    return respStr;
                }
            }
            return null;
        } catch (IOException e) {
        } finally {
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                }
            }
        }
        return null;

    }

    public static JSONObject doPost(String url, JSONObject json){
        HttpPost post = new HttpPost(url);
        JSONObject response = null;
        try {
            StringEntity s = new StringEntity(json.toString());
            s.setContentEncoding("UTF-8");
            //发送json数据需要设置contentType
            s.setContentType("application/json");
            post.setEntity(s);
            log.info("HttpPostJson Url ========> : [{}]",post.getURI());
            HttpResponse res = HttpClientUtil.build().httpClient.execute(post);
            int statusCode = res.getStatusLine().getStatusCode();
            log.info("HttpPostJson Response code ========> : [{}]",statusCode);
            if(statusCode == HttpStatus.SC_OK){
                HttpEntity entity = res.getEntity();
                // 返回json格式：
                String result = EntityUtils.toString(entity);
                response = JSONObject.parseObject(result);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        log.info("HttpPostJson Response String ========> : [{}]",response.toJSONString());
        return response;
    }

    private static HttpEntity getUrlEncodedFormEntity(Map<String, Object> params) {
        List<NameValuePair> pairList = new ArrayList<NameValuePair>(params.size());
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            NameValuePair pair = new BasicNameValuePair(entry.getKey(), entry
                    .getValue().toString());
            pairList.add(pair);
        }
        return new UrlEncodedFormEntity(pairList, Charset.forName("UTF-8"));
    }

    private static String getUrlWithParams(String url, Map<String, Object> params) {
        Iterator<String> iterator = params.keySet().iterator();
        StringBuilder builder = new StringBuilder(url);
        try {
            while (iterator.hasNext()){
                boolean withOtherParam = url.contains("?");
                String paramName = iterator.next();
                String param = params.get(paramName).toString();
                String linkChar = withOtherParam ? "&" : "?";
                builder.append(linkChar);
                builder.append(paramName);
                builder.append("=");
                builder.append(java.net.URLEncoder.encode(param,"UTF-8"));
            }
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        return builder.toString();
    }

    private class IdleConnectionMonitorThread extends Thread {

        private final HttpClientConnectionManager connMgr;
        private volatile boolean exitFlag = false;

        public IdleConnectionMonitorThread(HttpClientConnectionManager connMgr) {
            this.connMgr = connMgr;
            setDaemon(true);
        }

        @Override
        public void run() {
            while (!this.exitFlag) {
                synchronized (this) {
                    try {
                        this.wait(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                // 关闭失效的连接
                connMgr.closeExpiredConnections();
                // 可选的, 关闭30秒内不活动的连接
                connMgr.closeIdleConnections(30, TimeUnit.SECONDS);
            }
        }

        public void shutdown() {
            this.exitFlag = true;
            synchronized (this) {
                notify();
            }
        }

    }
}
