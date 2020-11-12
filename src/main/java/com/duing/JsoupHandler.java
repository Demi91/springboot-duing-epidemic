package com.duing;

import com.duing.bean.DataBean;
import com.duing.util.HttpConnUtil;
import com.google.gson.Gson;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.Map;

public class JsoupHandler {

    public static String html = "<!DOCTYPE html>\n" +
            "<html lang=\"en\">\n" +
            "<head>\n" +
            "    <meta charset=\"UTF-8\">\n" +
            "    <title>Title</title>\n" +
            "</head>\n" +
            "<body>\n" +
            "<p> Hello World</p>\n" +
            "</body>\n" +
            "</html>";

    public static void main(String[] args) throws Exception {
//        Document doc = Jsoup.parse(html);
//        Elements body = doc.select("body");
//        System.out.println(body);

        String url = "https://ncov.dxy.cn/ncovh5/view/pneumonia";
//        String str = HttpConnUtil.doGet(url);
//
//        Document doc = Jsoup.parse(str);

        Document doc = Jsoup.connect(url).get();
//        Elements scripts = doc.select("script");
        Element oneScript = doc.getElementById("getAreaStat");

        String data = oneScript.data();

        String subData = data.substring(data.indexOf("["), data.lastIndexOf("]") + 1);

        Gson gson = new Gson();
        ArrayList list = gson.fromJson(subData, ArrayList.class);


        ArrayList<DataBean> result = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            Map map = (Map) list.get(i);
            String name = (String) map.get("provinceName");
            double nowConfirm = (Double) map.get("currentConfirmedCount");
            double confirm = (Double) map.get("confirmedCount");
            double dead = (Double) map.get("deadCount");
            double heal = (Double) map.get("curedCount");

            DataBean dataBean = new DataBean(name,
                    (int) nowConfirm, (int) confirm, (int) dead, (int) heal);
            result.add(dataBean);
        }


        System.out.println(result);
    }
}
