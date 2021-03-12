package com.zh.handler;

import com.google.gson.Gson;
import com.zh.bean.GraphBean;
import com.zh.util.HttpConnUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GraphHandler {

    private static String url = "https://api.inews.qq.com/newsqa/v1/query/inner/publish/modules/list?" +
            "modules=chinaDayList,chinaDayAddList,cityStatis,nowConfirmStatis,provinceCompare";

    public static List<GraphBean> getGraphData(){
        String str  = HttpConnUtil.doGet(url);
        Gson gson = new Gson();
        Map map = gson.fromJson(str, Map.class);
        Map dateMap = (Map) map.get("data");
        ArrayList list = (ArrayList) dateMap.get("chinaDayAddList");
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
    public static void main(String[] args){
        List<GraphBean> result =  GraphHandler.getGraphData();
        System.out.println(result);
    }
}
