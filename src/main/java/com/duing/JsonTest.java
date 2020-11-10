package com.duing;

import com.google.gson.Gson;

import java.util.Map;

public class JsonTest {


    public static void main(String[] args) {
        String  tmpJson = "{\"hello\":\"world\"}";

        Gson gson = new Gson();
        Map map = gson.fromJson(tmpJson,Map.class);
        System.out.println(map);


    }
}
