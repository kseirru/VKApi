package com.kseirru.Models;

public class OnReadyEvent {
    private VKApi vkApi;
    public OnReadyEvent(VKApi vkApi) {
        this.vkApi = vkApi;
    }

    public VKApi getVkApi() {
        return vkApi;
    }
}
