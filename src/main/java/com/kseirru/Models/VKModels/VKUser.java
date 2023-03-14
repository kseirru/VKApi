package com.kseirru.Models.VKModels;

import java.util.Map;
import java.util.Objects;

public class VKUser {

    private int id;
    private String urlId;
    private String urlString;
    private String stringId;
    private String birthDay;
    private VKCountry country;
    private VKCity city;
    private String avatar;
    private boolean hasPhoto;
    private boolean hasMobile;
    private String site;
    private String status;
    private int followersCount;
    private VKSex sex;
    private boolean verified;
    private String FirstName;
    private String LastName;
    private boolean isClosed;

    public VKUser(Map<String, Object> response) {
        this.id = (Integer) response.get("id");
        this.urlId = "https://vk.com/id" + this.id;
        this.urlString = "https://vk.com/" + response.get("domain");
        this.birthDay = (String) response.get("bdate");
        this.city = new VKCity((Map<String, Object>) response.get("city"));
        this.country = new VKCountry((Map<String, Object>) response.get("country"));
        this.avatar = (String) response.get("photo_400_orig");
        this.hasPhoto = Objects.equals((Integer) response.get("has_photo"), 1);
        this.hasMobile = Objects.equals((Integer) response.get("has_mobile"), 1);
        this.sex = new VKSex((Integer) response.get("sex"));
        this.verified = Objects.equals((Integer) response.get("verified"), 1);
        this.FirstName = (String) response.get("first_name");
        this.LastName = (String) response.get("last_name");
        this.isClosed = (boolean) response.get("is_closed");
    }

    public int getId() {
        return id;
    }

    public String getStringId() {
        return stringId;
    }

    public VKCountry getCountry() {
        return country;
    }

    public String getUrlId() {
        return urlId;
    }

    public String getUrlString() {
        return urlString;
    }

    public String getBirthDay() {
        return birthDay;
    }

    public VKCity getCity() {
        return city;
    }

    public String getAvatar() {
        return avatar;
    }

    public boolean isHasPhoto() {
        return hasPhoto;
    }

    public boolean isHasMobile() {
        return hasMobile;
    }

    public String getSite() {
        return site;
    }

    public String getStatus() {
        return status;
    }

    public int getFollowersCount() {
        return followersCount;
    }

    public VKSex getSex() {
        return sex;
    }

    public boolean isVerified() {
        return verified;
    }

    public String getFirstName() {
        return FirstName;
    }

    public String getLastName() {
        return LastName;
    }

    public boolean isClosed() {
        return isClosed;
    }

}
