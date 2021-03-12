package com.zh.controller;

import com.google.gson.Gson;
import com.zh.bean.GraphBean;
import com.zh.handler.GraphHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;

@Controller
public class GraphController {


    @GetMapping("graph")
    public String graph(Model model){
        ArrayList<GraphBean> graphBeans = (ArrayList<GraphBean>) GraphHandler.getGraphData();
        ArrayList<String> dateList = new ArrayList<>();
        ArrayList<Integer> confirmList = new ArrayList<>();
        ArrayList<Integer> healList = new ArrayList<>();
        ArrayList<Integer> deadList = new ArrayList<>();
        for (int i = 0; i < graphBeans.size(); i++) {
            GraphBean graphBean = graphBeans.get(i);
            dateList.add(graphBean.getDate());
            confirmList.add(graphBean.getConfirm());
            healList.add(graphBean.getHeal());
            deadList.add(graphBean.getDead());
        }
        model.addAttribute("dateList", new Gson().toJson(dateList));
        model.addAttribute("confirmList", new Gson().toJson(confirmList));
        model.addAttribute("healList", new Gson().toJson(healList));
        model.addAttribute("deadList", new Gson().toJson(deadList));
        return "graph";
    }
}
