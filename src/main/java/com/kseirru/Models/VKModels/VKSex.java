package com.kseirru.Models.VKModels;

public class VKSex {
    private int id;
    private VKSexEnum vkSexEnum;

    public VKSex(int id) {
        switch (id) {
            case 0 -> vkSexEnum = VKSexEnum.Unknown;
            case 1 -> vkSexEnum = VKSexEnum.Female;
            case 2 -> vkSexEnum = VKSexEnum.Male;
        }
    }

    public int getId() {
        return id;
    }

    public VKSexEnum getVkSexEnum() {
        return vkSexEnum;
    }
}
