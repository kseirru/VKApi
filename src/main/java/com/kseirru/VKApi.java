package com.kseirru;

import com.kseirru.Models.VKEventHandler;
import com.kseirru.Models.VkEventType;
import com.kseirru.Models.VkMessage;
import com.kseirru.Utils.HTTPClient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VKApi {
    private final String BASE_URL = "https://api.vk.com/method/";
    private final String API_VERSION = "5.131";

    private String LongPoolServer;
    private String token;
    private String key;
    private int ts = 0;
    private int wait = 25;
    public HTTPClient httpClient;

    private Map<VkEventType, List<VKEventHandler>> eventHandlers = new HashMap<>();

    @SuppressWarnings("unchecked")
    public VKApi(String token, String groupId) {
        httpClient = new HTTPClient();

        Map<String, String> args1 = new HashMap<>();
        args1.put("access_token", token);
        args1.put("group_id", groupId);
        args1.put("v", API_VERSION);
        Map<String, Object> responseStart = httpClient.execute(BASE_URL + "groups.getLongPollServer", args1);
        Object responseObject = responseStart.get("response");
        System.out.println(eventHandlers); ////////
        if (responseObject instanceof Map) {
            Map<String, Object> responseMap = (Map<String, Object>) responseObject;
            key = (String) responseMap.get("key");
            LongPoolServer = (String) responseMap.get("server");
            ts = Integer.parseInt((String) responseMap.get("ts"));
        }

        while (true) {
            try {
            Map<String, String> args2 = new HashMap<>();
            args2.put("act", "a_check"); args2.put("key", key);
            args2.put("ts", String.valueOf(ts)); args2.put("wait", String.valueOf(wait));
            Map<String, Object> response = httpClient.execute(LongPoolServer, args2);
            if(!(Integer.parseInt(String.valueOf(response.get("ts"))) == ts)) {
                ts = Integer.parseInt(String.valueOf(response.get("ts")));
                // System.out.println(response);
                ArrayList<Object> updates = (ArrayList<Object>) response.get("updates");
                Map<String, Object> data = (Map<String, Object>) updates.get(0);
                if(data.get("type").equals("message_new")) {
                    handleNewMessage(response);
                }
            } } catch (Exception ignored) {}
        }

    }

    public void addEventHandler(VkEventType eventType, VKEventHandler eventHandler) {
        if (!eventHandlers.containsKey(eventType)) {
            eventHandlers.put(eventType, new ArrayList<>());
        }
        eventHandlers.get(eventType).add(eventHandler);
    }
    private void handleNewMessage(Map<String, Object> response) {
        VkMessage message = new VkMessage(response);
        List<VKEventHandler> handlers = eventHandlers.get(VkEventType.message_new);
        if(handlers != null) {
            for (VKEventHandler handler : handlers) {
                if (handler instanceof VKEventHandler) {
                    ((VKEventHandler) handler).onMessageReceived(message);
                }
            }
        }
    }
}
