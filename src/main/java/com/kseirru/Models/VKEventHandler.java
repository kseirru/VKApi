package com.kseirru.Models;

public interface VKEventHandler {
    void onMessageReceived(MessageReceivedContext messageReceivedContext);
    void onReady(OnReadyEvent onReadyEvent);
}
