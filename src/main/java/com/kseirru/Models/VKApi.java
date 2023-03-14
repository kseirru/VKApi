package com.kseirru.Models;

import com.kseirru.Models.VKModels.VKMessage;
import com.kseirru.Models.VKModels.VKUser;
import com.kseirru.Utils.HTTPClient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VKApi {
    public final String BASE_URL = "https://api.vk.com/method/";
    private final String API_VERSION = "5.131";

    private String LongPoolServer;
    private String token;
    private String groupId;
    private String key;
    private int ts = 0;
    private int wait = 25;
    public HTTPClient httpClient;

    public Map<VKEventType, ArrayList<VKAbstractEventHandler>> eventHandlers;

    @SuppressWarnings("unchecked")
    public VKApi(String token, String groupId, Map<VKEventType, ArrayList<VKAbstractEventHandler>> eventHandlers) {
        httpClient = new HTTPClient();
        this.eventHandlers = eventHandlers;
        this.token = token;
        this.groupId = groupId;

        Map<String, String> args1 = new HashMap<>();
        args1.put("access_token", token);
        args1.put("group_id", groupId);
        args1.put("v", API_VERSION);
        Map<String, Object> responseStart = httpClient.execute(BASE_URL + "groups.getLongPollServer", args1);
        Object responseObject = responseStart.get("response");
        if (responseObject instanceof Map) {
            Map<String, Object> responseMap = (Map<String, Object>) responseObject;
            key = (String) responseMap.get("key");
            LongPoolServer = (String) responseMap.get("server");
            ts = Integer.parseInt((String) responseMap.get("ts"));
        }

        this.handleOnReady();

        while (true) {
            try {
            Map<String, String> args2 = new HashMap<>();
            args2.put("act", "a_check"); args2.put("key", key);
            args2.put("ts", String.valueOf(ts)); args2.put("wait", String.valueOf(wait));
            Map<String, Object> response = httpClient.execute(LongPoolServer, args2);
            if(response != null) {
            if(!(Integer.parseInt(String.valueOf(response.get("ts"))) == ts)) {
                ts = Integer.parseInt(String.valueOf(response.get("ts")));
                ArrayList<Object> updates = (ArrayList<Object>) response.get("updates");
                Map<String, Object> data = (Map<String, Object>) updates.get(0);
                String typeOfUpdate = (String) data.get("type");
                if (typeOfUpdate.equals("message_new")) {
                    this.handleNewMessage(response);
                }
            }
            } } catch (Exception e) {e.printStackTrace();}
        }

    }

    private void handleNewMessage(Map<String, Object> response) {
        VKMessage message = new VKMessage(response);

        MessageReceivedContext messageReceivedContext = new MessageReceivedContext(this, message);

        List<VKAbstractEventHandler> newMessageHandlers = eventHandlers.get(VKEventType.message_new);
        if(newMessageHandlers != null) {
            for(VKAbstractEventHandler handler : newMessageHandlers) {
                handler.onMessageReceived(messageReceivedContext);
            }
        }
    }

    private void handleOnReady() {
        List<VKAbstractEventHandler> onReadyHandlers = eventHandlers.get(VKEventType.on_ready);
        if(onReadyHandlers != null) {
            for(VKAbstractEventHandler handler : onReadyHandlers) {
                handler.onReady(new OnReadyEvent(this));
            }
        }
    }

    @SuppressWarnings("unchecked")
    public VKUser getUser(int userId) {
        Map<String, String> args = new HashMap<>();
        args.put("access_token", this.token);
        args.put("v", this.API_VERSION);
        args.put("user_ids", String.valueOf(userId));
        args.put("fields", "about, bdate, city, country, domain, has_photo, has_mobile, sex, status, verified, photo_400_orig");
        ArrayList<Object> response = (ArrayList<Object>) this.httpClient.execute(this.BASE_URL + "users.get", args).get("response");
        Map<String, Object> data = (Map<String, Object>) response.get(0);
        return new VKUser(data);
    }

    public String getToken() {
        return token;
    }

    public String getAPI_VERSION() {
        return API_VERSION;
    }

    public HTTPClient getHttpClient() {
        return httpClient;
    }
}
