package com.zh.util;


import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;



public class HttpConnUtil {


    public static String doGet(String urlStr){
        HttpURLConnection conn = null;
        InputStream is = null;
        BufferedReader reader = null;
        StringBuilder result = new StringBuilder();
        try {
            //获取url
            URL url = new URL(urlStr);
            conn = (HttpURLConnection)url.openConnection();
            //设置请求方法 连接时间 读取时间
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(10000);
            conn.setReadTimeout(60000);
            conn.setRequestProperty("Accept", "application/json");
            //发送链接
            conn.connect();

            if(200 == conn.getResponseCode()){
                is = conn.getInputStream();
                reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
                String line;
                while ((line = reader.readLine()) != null){
                    result.append(line);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                if(is != null){
                    is.close();
                }
                if(reader != null){
                    reader.close();
                }
                if(conn != null){
                    conn.disconnect();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


        return result.toString();
    }

    public static void main(String[] args) {
//        String str = "https://view.inews.qq.com/g2/getOnsInfo?name=disease_h5";
        String urlStr = "https://ncov.dxy.cn/ncovh5/view/pneumonia";
        String result = HttpConnUtil.doGet(urlStr);
        System.out.println(result);
    }


}
