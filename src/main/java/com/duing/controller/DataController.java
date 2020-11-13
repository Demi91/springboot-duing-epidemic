package com.duing.controller;

import com.duing.bean.DataBean;
import com.duing.service.DataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class DataController {

    @Autowired
    private DataService dataService;

    @GetMapping("/")
    public String list(Model model) {
        // 此时使用的list 是mybatis plus提供的
        // 查询全表数据
        List<DataBean> dataList = dataService.list();
        model.addAttribute("dataList", dataList);
        return "list";
    }
}
