package com.kseirru.Models.VKModels;

import java.util.Map;

public class VKCountry {
    private int id;
    private String title;

    public VKCountry(Map<String, Object> countryMap) {
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
