package com.kseirru.Models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class VkMessage {
    private String ts;
    public String groupId;
    public VkEventType vkEventType;
    public String timestamp;
    public String authorId;
    public List<Object> attachments;
    public String content;

    @SuppressWarnings("unchecked")
    public VkMessage(Map<String, Object> response) {
        ts = String.valueOf(response.get("ts"));
        ArrayList<Object> updates = (ArrayList<Object>) response.get("updates");
        Map<String, Object> data = (Map<String, Object>) updates.get(0);
        groupId = String.valueOf(data.get("group_id"));
        if (data.get("type").equals("message_new")) {
            vkEventType = VkEventType.message_new;
        }

        Map<String, Object> objects = (Map<String, Object>) data.get("object");
        Map<String, String> message = (Map<String, String>) objects.get("message");

        timestamp = String.valueOf(message.get("date"));
        authorId = String.valueOf(message.get("from_id"));
        attachments = Collections.singletonList(message.get("attachments"));
        content = String.valueOf(message.get("text"));

    }


}
