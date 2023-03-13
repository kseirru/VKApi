package com.kseirru.Models;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class MessageReceivedContext {

    private VkMessage vkMessage;
    private VKApi VKApi;

    public MessageReceivedContext(VKApi vkApi, VkMessage message) {
        vkMessage = message;
        VKApi = vkApi;
    }

    public VKApi getVKApi() {
        return VKApi;
    }

    public VkMessage getMessage() {
        return vkMessage;
    }

    public void sendMessage(String content) {
        Map<String, String> args = new HashMap<>();
        args.put("user_id", vkMessage.authorId);
        args.put("random_id", String.valueOf(new Random().nextInt(1, 2147483647)));
        args.put("message", content);
        args.put("dont_parse_links", "0");
        args.put("disable_mentions", "0");
        args.put("access_token", VKApi.getToken());
        args.put("v", VKApi.getAPI_VERSION());
        System.out.println(args);
        System.out.println(VKApi.getHttpClient().execute(VKApi.BASE_URL + "messages.send", args));
    }

}
