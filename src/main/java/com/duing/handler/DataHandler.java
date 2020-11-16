package com.duing.handler;

import com.duing.bean.DataBean;
import com.duing.service.DataService;
import com.duing.util.HttpConnUtil;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.FileReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Component
public class DataHandler {

    @Autowired
    private DataService dataService;

    public static void main(String[] args) {
        getData();
    }


//    @PostConstruct 被此注解修饰的方法  会在服务器启动时执行一次
    //  往往用来进行数据初始化

    @PostConstruct
    public void saveData() {
        System.out.println("初始化数据的存储");
        List<DataBean> dataBeans = getData();

        // 先清空 再存储
        dataService.remove(null);
        dataService.saveBatch(dataBeans);
    }


    private static final SimpleDateFormat dateFormat =
            new SimpleDateFormat("HH:mm:ss");

    //    @Scheduled(fixedDelay = 10000)
//    @Scheduled(fixedRate = 10000)
//    @Scheduled(cron = "0 0/1 * * * ? ")
    public void updateData() {
        System.out.println("更新数据,当前时间" + dateFormat.format(new Date()));
        List<DataBean> dataBeans = getData();

        // 先清空 再存储
        dataService.remove(null);
        dataService.saveBatch(dataBeans);
    }


    public static String urlStr = "https://view.inews.qq.com/g2/getOnsInfo?name=disease_h5";

    public static List<DataBean> getData() {
//        StringBuilder builder = new StringBuilder();
//        try {
//        String  tmpJson = "{\"hello\":\"world\"}";
//
//        Gson gson = new Gson();
//        Map map = gson.fromJson(tmpJson,Map.class);
//        System.out.println(map);

//            // 读取tmp.json
//            FileReader fr = new FileReader("tmp.json");
//            char[] cBuf = new char[1024];
//
//            int cRead = 0;
//
//            while ((cRead = fr.read(cBuf)) > 0) {
//                builder.append(new String(cBuf, 0, cRead));
//            }
//            fr.close();
//        } catch (Exception e) {
//
//        }


        String str = HttpConnUtil.doGet(urlStr);

//        System.out.println(builder.toString());
        // 层层读取  找到要显示的数据  存到ArrayList之中

        Gson gson = new Gson();
        Map map = gson.fromJson(str, Map.class);

        String subStr = (String) map.get("data");
        Map subMap = gson.fromJson(subStr, Map.class);

        ArrayList areaList = (ArrayList) subMap.get("areaTree");

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

            DataBean dataBean = new DataBean(null, name, (int) nowConfirm,
                    (int) confirm, (int) dead, (int) heal);
            result.add(dataBean);

        }

//        System.out.println(result);
        return result;
    }
}
