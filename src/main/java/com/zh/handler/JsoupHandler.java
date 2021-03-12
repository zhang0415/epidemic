package com.zh.handler;

import com.google.gson.Gson;
import com.zh.bean.DataBean;
import com.zh.util.HttpConnUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.beans.beancontext.BeanContext;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class JsoupHandler {

    public static ArrayList<DataBean> getData() {
        String urlStr = "https://ncov.dxy.cn/ncovh5/view/pneumonia";
        String str = HttpConnUtil.doGet(urlStr);
        Document doc = Jsoup.parse(str);
//        Document doc = null;
//        try {
//            doc = Jsoup.connect(urlStr).get();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        Element oneScript = doc.getElementById("getAreaStat");
        String data = oneScript.data();
        String subData = data.substring(data.indexOf("["),data.lastIndexOf("]")+1);
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
            DataBean dataBean = new DataBean(null,name, (int)nowConfirm, (int) confirm, (int) dead, (int) heal);
            result.add(dataBean);
        }
        return result;
    }

    public static void main(String[] args) {
        ArrayList<DataBean> beans = JsoupHandler.getData();
        for (DataBean bean : beans){
            System.out.println(bean);
        }
    }
}
