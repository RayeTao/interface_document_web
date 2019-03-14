package com.egova.api.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;

/**
 * Created by taoran on 2018/12/17
 */
public class HttpUtils {

    public static final Logger logger = LoggerFactory.getLogger(HttpUtils.class);

    private static final String CHEST_CODE = "utf-8";

    /**
     * 向指定URL发送GET方法的请求
     *
     * @param url
     *            发送请求的URL
     * @param param
     *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return URL 所代表远程资源的响应结果
     */
    public static String sendGet(String url, String param) {
        String result = "";
        BufferedReader in = null;
        try {
            if(param==null){
                param="";
            }
            String urlNameString = url + "?" + param;

            //URL realUrl = new URL(urlNameString);
            if (urlNameString.equals(new String(urlNameString.getBytes("iso8859-1"), "iso8859-1"))) {
                urlNameString = new String(urlNameString.getBytes("iso8859-1"), "utf-8");
            }
            URL realUrl = new URL(urlNameString);
            // 打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();
            // 设置通用的请求属性
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent","Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            connection.setRequestProperty("Accept-Charset", CHEST_CODE);
            connection.setRequestProperty("contentType", CHEST_CODE);
            // 建立实际的连接
            connection.connect();
            // 获取所有响应头字段
            Map<String, List<String>> map = connection.getHeaderFields();
            // 遍历所有的响应头字段
            for (String key : map.keySet()) {
                System.out.println(key + "--->" + map.get(key));
            }
            // 定义 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(connection.getInputStream(),CHEST_CODE));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            logger.error("发送GET请求出现异常！="+e.getMessage(),e);
        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                logger.error("异常信息="+e2.getMessage(),e2);
            }
        }
        return result;
    }

    /**
     * 向指定 URL 发送POST方法的请求
     *
     * @param url
     *            发送请求的 URL
     * @param param
     *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return 所代表远程资源的响应结果
     */
    public static String sendPost(String url, String param) {
        return sendPost(url, param, null);
    }

    public static String sendPost(String serverUrl, String param, String charsetName) {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL url = new URL(serverUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);    // 可以发送数据
            conn.setDoInput(true);    // 可以接收数据
            conn.setRequestMethod("POST");    // POST方法
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            // 必须注意此处需要设置UserAgent，否则google会返回403
            conn.setRequestProperty
                    ("User-Agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.0)");
            //conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            if (charsetName != null) {
                conn.setRequestProperty("Accept-Charset", charsetName);
                conn.setRequestProperty("contentType", charsetName);
            }
            conn.connect();
            // 写入的POST数据
            OutputStreamWriter osw = new OutputStreamWriter(conn.getOutputStream(), charsetName);
            osw.write(param);
            osw.flush();
            osw.close();
            // 读取响应数据
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream(), charsetName));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            logger.error("发送 POST 请求出现异常！"+e.getMessage(),e);
        }finally{
            try{
                if(out!=null){
                    out.close();
                }
                if(in!=null){
                    in.close();
                }
            }
            catch(IOException ex){
                logger.error("异常信息="+ex.getMessage(),ex);
            }
        }

        return result;
    }

    public static String checkAndConvertStr(String str) {
        try {
            if(!StringUtils.isEmpty(str)){
                if (str.equals(new String(str.getBytes("iso8859-1"), "iso8859-1"))) {
                    str = new String(str.getBytes("iso8859-1"), "utf-8");
                }
            }
        } catch (UnsupportedEncodingException e) {
            logger.error("转换失败，原因:"+e.getMessage());
        }
        return str;
    }
}
