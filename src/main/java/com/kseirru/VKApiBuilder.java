package com.kseirru;

import com.kseirru.Models.VKApi;
import com.kseirru.Models.VKEventHandlerInterface;
import com.kseirru.Models.VkEventType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class VKApiBuilder {
    private String token;
    private String groupId;
    private Map<VkEventType, ArrayList<VKEventHandlerInterface>> eventHandlers = new HashMap<>();

    public VKApiBuilder setToken(String token) {
        this.token = token;
        return this;
    }

    public VKApiBuilder setGroupId(String groupId) {
        this.groupId = groupId;
        return this;
    }

    public VKApiBuilder addEventHandler(VkEventType eventType, VKEventHandlerInterface eventHandler) {
        if (!this.eventHandlers.containsKey(eventType)) {
            this.eventHandlers.put(eventType, new ArrayList<>());
        }
        this.eventHandlers.get(eventType).add(eventHandler);
        return this;
    }


    public VKApi build() {
        if (token == null) {
            throw new IllegalArgumentException("Token is required");
        }
        if (groupId == null) {
            throw new IllegalArgumentException("Group ID is required");
        }
        return new VKApi(token, groupId, eventHandlers);
    }
}