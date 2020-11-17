package com.duing.handler;

import com.duing.bean.GraphBarBean;
import com.duing.bean.GraphBean;
import com.duing.util.HttpConnUtil;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GraphHandler {

    public static String urlStr = "https://api.inews.qq.com/newsqa/v1/query/inner/publish/modules/list?" +
            "modules=chinaDayList,chinaDayAddList,cityStatis,nowConfirmStatis,provinceCompare";

    public static List<GraphBean> getGraphData() {
        String str = HttpConnUtil.doGet(urlStr);

        Gson gson = new Gson();
        Map map = gson.fromJson(str, Map.class);

        Map subMap = (Map) map.get("data");
        ArrayList list = (ArrayList) subMap.get("chinaDayList");

        ArrayList<GraphBean> result = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            Map tmp = (Map) list.get(i);

            String date = (String) tmp.get("date");
            double confirm = (Double) tmp.get("confirm");
            double heal = (Double) tmp.get("heal");
            double dead = (Double) tmp.get("dead");

            GraphBean graphBean = new GraphBean(
                    date, (int) confirm, (int) heal, (int) dead);
            result.add(graphBean);
        }

        return result;
    }


    public static String urlStrAll = "https://view.inews.qq.com/g2/getOnsInfo?name=disease_h5";

    public static List getData() {
        String str = HttpConnUtil.doGet(urlStrAll);

        // 层层读取  找到要显示的数据  存到ArrayList之中
        Gson gson = new Gson();
        Map map = gson.fromJson(str, Map.class);

        String subStr = (String) map.get("data");
        Map subMap = gson.fromJson(subStr, Map.class);

        ArrayList areaList = (ArrayList) subMap.get("areaTree");

        Map dataMap = (Map) areaList.get(0);

        ArrayList childrenList = (ArrayList) dataMap.get("children");

        ArrayList<GraphBarBean> result = new ArrayList<>();

        for (int i = 0; i < childrenList.size(); i++) {
            Map tmp = (Map) childrenList.get(i);
            String name = (String) tmp.get("name");

            ArrayList children = (ArrayList) tmp.get("children");
            for (int j = 0; j < children.size(); j++) {
                Map subTmp = (Map) children.get(j);
                if ("境外输入".equals((String) subTmp.get("name"))) {
                    Map total = (Map) subTmp.get("total");
                    double fromAbroad = (Double) total.get("confirm");
                    GraphBarBean bean = new GraphBarBean(name, (int) fromAbroad);
                    result.add(bean);
                }

            }

        }
        return result;
    }

    public static void main(String[] args) {
        getData();
    }

}
