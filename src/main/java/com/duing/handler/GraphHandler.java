package com.duing.handler;

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


}
