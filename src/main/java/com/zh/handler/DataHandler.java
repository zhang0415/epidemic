package com.zh.handler;

import com.google.gson.Gson;
import com.zh.bean.DataBean;
import com.zh.service.DataService;
import com.zh.util.HttpConnUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
@Component
public class DataHandler {

    @Autowired
    private DataService dataService;

    @PostConstruct
    public void saveData() {
        System.out.println("初始化数据的存储");
        List<DataBean> dataBeans = getData();

        // 先清空 再存储
        dataService.remove(null);
        dataService.saveBatch(dataBeans);
    }
    private static SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
    @Scheduled(cron = "0 0/59 * * * ?")
    public void upData() {
        System.out.println("更新时间"+format.format(new Date()));
        List<DataBean> dataBeans = getData();

        // 先清空 再存储
        dataService.remove(null);
        dataService.saveBatch(dataBeans);
    }
    public static List getData() {
//        //读文件
//        FileReader fr = null;
//        StringBuilder builder = null;
//        try {
//            fr = new FileReader("tmp.json");
//            char[] CBuf = new char[1024];
//            int cRead = 0;
//            builder = new StringBuilder();
//            while ((cRead = fr.read(CBuf)) > 0){
//                builder.append(new String(CBuf,0,cRead));
//            }
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }finally {
//            if(fr != null){
//                try {
//                    fr.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
        String strUrl = "https://view.inews.qq.com/g2/getOnsInfo?name=disease_h5";
        String str = HttpConnUtil.doGet(strUrl);
        //将读取到的数据转化到List集合中
        Gson gson = new Gson();
        Map map = gson.fromJson(str,Map.class);

        String subStr = (String) map.get("data");
        //获取到地区中国
        Map subMap = gson.fromJson(subStr,Map.class);
        ArrayList areaTree = (ArrayList) subMap.get("areaTree");
        //将ArrayList中的内容转化成Map,因为只有一个areaTree
        Map dataMap = (Map) areaTree.get(0);
        //获取areaTree内的children
        ArrayList childrenList = (ArrayList) dataMap.get("children");

        //存储每一个地区的当前人数、死亡人数、治愈人数、当前城市名
        ArrayList<DataBean> result = new ArrayList();

        for (int i = 0; i < childrenList.size(); i++) {
            //遍历获取每一个地区
            Map tmpMap = (Map)childrenList.get(i);
            //地区名字
            String name = (String)tmpMap.get("name");
            //total内的数据
            Map totalMap = (Map) tmpMap.get("total");
            double nowConfirm = (Double) totalMap.get("nowConfirm");
            double confirm = (Double) totalMap.get("confirm");
            double dead = (Double) totalMap.get("dead");
            double heal = (Double) totalMap.get("heal");
            DataBean dataBean = new DataBean(null,name, (int)nowConfirm, (int) confirm, (int) dead, (int) heal);
            result.add(dataBean);
        }

        return result;

    }
}
