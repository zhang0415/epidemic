package com.zh.controller;

import com.zh.bean.DataBean;
import com.zh.service.DataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class DataController {
    @Autowired
    private DataService service;
    @GetMapping("/")
    public String list(Model model){
        List<DataBean> dataList = service.list();
        model.addAttribute("dataList",dataList);
        return "list";
    }
    @GetMapping("/1")
    public String list1(Model model){
        List<DataBean> dataList = service.list();
        model.addAttribute("dataList",dataList);
        return "list";
    }
}
