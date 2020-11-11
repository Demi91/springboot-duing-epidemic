package com.duing;

import com.duing.bean.DataBean;
import com.google.gson.Gson;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.Map;


public class JsonTest {


    public static void main(String[] args) throws Exception {
//        String  tmpJson = "{\"hello\":\"world\"}";
//
//        Gson gson = new Gson();
//        Map map = gson.fromJson(tmpJson,Map.class);
//        System.out.println(map);

        // 读取tmp.json
        FileReader fr = new FileReader("tmp.json");
        char[] cBuf = new char[1024];

        int cRead = 0;
        StringBuilder builder = new StringBuilder();
        while ((cRead = fr.read(cBuf)) > 0) {
            builder.append(new String(cBuf, 0, cRead));
        }
        fr.close();


//        System.out.println(builder.toString());
        // 层层读取  找到要显示的数据  存到ArrayList之中

        Gson gson = new Gson();
        Map map = gson.fromJson(builder.toString(), Map.class);

        ArrayList areaList = (ArrayList) map.get("areaTree");

        Map dataMap = (Map) areaList.get(0);

        ArrayList childrenList = (ArrayList) dataMap.get("children");

        ArrayList<DataBean> result = new ArrayList();

        for (int i = 0; i < childrenList.size(); i++) {

            Map tmp = (Map) childrenList.get(i);
            String name = (String) tmp.get("name");

            Map totalMap = (Map) tmp.get("total");

            double nowConfirm = (Double) totalMap.get("nowConfirm");
            double confirm = (Double) totalMap.get("confirm");
            double dead = (Double) totalMap.get("dead");
            double heal = (Double) totalMap.get("heal");

            DataBean dataBean = new DataBean(name, (int) nowConfirm,
                    (int) confirm, (int) dead, (int) heal);
            result.add(dataBean);

        }

        System.out.println(result);

    }
}
