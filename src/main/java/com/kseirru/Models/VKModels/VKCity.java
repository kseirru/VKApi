package com.kseirru.Models.VKModels;

import java.util.Map;

public class VKCity {
    private int id;
    private String title;

    public VKCity(Map<String, Object> countryMap) {
        this.id = (int) countryMap.get("id");
        this.title = (String) countryMap.get("title");
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }
}
