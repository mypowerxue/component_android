package com.xxx.common.model.http.bean;

public class MyUserInfoBean {

    private String avatar;
    private String promotionCode;
    private String username;
    private int memberLevel;
    private int userStar;
    private int googleState;        //性别

    public int getMemberLevel() {
        return memberLevel;
    }

    public String getAvatar() {
        return avatar;
    }

    public String getPromotionCode() {
        return promotionCode;
    }

    public String getUsername() {
        return username;
    }

    public int getSex() {
        return googleState;
    }

}
