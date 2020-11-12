package com.duing.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

// 用java代码  进行http请求
public class HttpConnUtil {


    public static String doGet(String urlStr) {
        HttpURLConnection conn = null;

        InputStream is = null;
        BufferedReader br = null;

        StringBuilder result = new StringBuilder();

        try {
            URL url = new URL(urlStr);
            conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("GET");

            // 连接时间
            //   发送请求的主机  开始连接到 url所对应的远程主机的时间 (受距离、带宽影响)
            // 读取时间
            //   建立连接后  获取数据所需要的时间 (受数据大小影响)
            conn.setConnectTimeout(15000);
            conn.setReadTimeout(60000);

            conn.setRequestProperty("Accept", "application/json");
            // 发送请求
            conn.connect();

            if (200 == conn.getResponseCode()) {
                is = conn.getInputStream();
                br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
                String line;
                while ((line = br.readLine()) != null) {
                    result.append(line);
                }
            }


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null) br.close();
                if (is != null) is.close();

            } catch (Exception e) {

            }
            conn.disconnect();
        }
        return result.toString();
    }

    public static void main(String[] args) {
//        String str = "https://view.inews.qq.com/g2/getOnsInfo?name=disease_h5";
        String str1 = "https://ncov.dxy.cn/ncovh5/view/pneumonia";
        String result = doGet(str1);
        System.out.println(result);
    }
}
