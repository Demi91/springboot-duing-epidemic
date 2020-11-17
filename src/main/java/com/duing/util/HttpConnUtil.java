package com.duing.util;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Iterator;
import java.util.Map;

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


    public String doPost(String urlStr, Map parameterMap) throws Exception {

        StringBuffer parameterBuffer = new StringBuffer();
        if (parameterMap != null) {
            Iterator iterator = parameterMap.keySet().iterator();
            while (iterator.hasNext()) {
                String key = (String) iterator.next();
                String value = "";
                if (parameterMap.get(key) != null) {
                    value = (String) parameterMap.get(key);
                }

                parameterBuffer.append(key).append("=").append(value);
                if (iterator.hasNext()) {
                    parameterBuffer.append("&");
                }
            }
        }

        System.out.println("POST parameter : " + parameterBuffer.toString());

        URL url = new URL(urlStr);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        connection.setDoOutput(true);
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Accept-Charset", "utf-8");
        connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        connection.setRequestProperty("Content-Length",
                String.valueOf(parameterBuffer.length()));

        OutputStream outputStream = null;
        OutputStreamWriter outputStreamWriter = null;
        InputStream inputStream = null;
        InputStreamReader inputStreamReader = null;
        BufferedReader reader = null;

        StringBuffer resultBuffer = new StringBuffer();

        try {
            outputStream = connection.getOutputStream();
            outputStreamWriter = new OutputStreamWriter(outputStream);

            outputStreamWriter.write(parameterBuffer.toString());
            outputStreamWriter.flush();

            connection.connect();

            if (connection.getResponseCode() != 200) {
                throw new Exception("HTTP Request is not success, " +
                        "Response code is " + connection.getResponseCode());
            }

            inputStream = connection.getInputStream();
            inputStreamReader = new InputStreamReader(inputStream);
            reader = new BufferedReader(inputStreamReader);

            String tempLine = null;
            while ((tempLine = reader.readLine()) != null) {
                resultBuffer.append(tempLine);
            }

        } finally {

            if (outputStreamWriter != null) {
                outputStreamWriter.close();
            }
            if (outputStream != null) {
                outputStream.close();
            }
            if (reader != null) {
                reader.close();
            }
            if (inputStreamReader != null) {
                inputStreamReader.close();
            }
            if (inputStream != null) {
                inputStream.close();
            }

        }

        return resultBuffer.toString();
    }
}
