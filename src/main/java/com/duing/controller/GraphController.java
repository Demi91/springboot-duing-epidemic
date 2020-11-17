package com.duing.controller;

import com.duing.bean.GraphBarBean;
import com.duing.bean.GraphBean;
import com.duing.handler.GraphHandler;
import com.google.gson.Gson;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Controller
public class GraphController {

    @GetMapping("/graph")
    public String graph(Model model) {
        List<GraphBean> list = GraphHandler.getGraphData();

        ArrayList<String> dateList = new ArrayList<>();
        ArrayList<Integer> confirmList = new ArrayList<>();
        ArrayList<Integer> healList = new ArrayList<>();
        ArrayList<Integer> deadList = new ArrayList<>();

        for (int i = 0; i < list.size(); i++) {
            GraphBean bean = list.get(i);
            dateList.add(bean.getDate());
            confirmList.add(bean.getConfirm());
            healList.add(bean.getHeal());
            deadList.add(bean.getDead());
        }

        model.addAttribute("dateList", new Gson().toJson(dateList));
        model.addAttribute("confirmList", new Gson().toJson(confirmList));
        model.addAttribute("healList", new Gson().toJson(healList));
        model.addAttribute("deadList", new Gson().toJson(deadList));
        return "graph";
    }


    @GetMapping("/graphBar")
    public String graphBar(Model model) {
        List<GraphBarBean> list = GraphHandler.getData();
        Collections.sort(list);

        ArrayList<String> nameList = new ArrayList<>();
        ArrayList<Integer> fromAbroadList = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            GraphBarBean bean = list.get(i);
            nameList.add(bean.getName());
            fromAbroadList.add(bean.getFromAbroad());
        }

        model.addAttribute("nameList", new Gson().toJson(nameList));
        model.addAttribute("fromAbroadList", new Gson().toJson(fromAbroadList));
        return "graphBar";
    }
}
