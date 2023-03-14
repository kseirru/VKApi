package com.kseirru.Models;

public abstract class VKAbstractEventHandler implements VKEventHandler {
    @Override
    public void onMessageReceived(MessageReceivedContext messageReceivedContext) {}

    @Override
    public void onReady(OnReadyEvent onReadyEvent) {}
}