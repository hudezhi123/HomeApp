package com.hudezhi.freedom.homeapp.live_video.bean;

import java.io.Serializable;

/**
 * Created by boy on 2017/8/15.
 */

public class OtherLoginInfoResult implements Serializable{

    /**
     * userID : 80ABCEAC73C9593FC210949FADF03EF9
     * icon : http://q.qlogo.cn/qqapp/100371282/80ABCEAC73C9593FC210949FADF03EF9/100
     * expiresTime : 1502768911062
     * nickname : 冰冷世界
     * token : FE0EAD311DB31A49ECDEE255B15A4E9C
     * secretType : 0
     * gender : 0
     * pf : desktop_m_qq-10000144-android-2002-
     * pay_token : A2366FB609F27EC6C2A959ECA2F05E1F
     * secret :
     * iconQzone : http://qzapp.qlogo.cn/qzapp/100371282/80ABCEAC73C9593FC210949FADF03EF9/100
     * pfkey : b98d533735a6032657416592c1a37233
     * expiresIn : 7776000
     */

    private String userID;
    private String icon;
    private long expiresTime;
    private String nickname;
    private String token;
    private String secretType;
    private String gender;
    private String pf;
    private String pay_token;
    private String secret;
    private String iconQzone;
    private String pfkey;
    private int expiresIn;

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public long getExpiresTime() {
        return expiresTime;
    }

    public void setExpiresTime(long expiresTime) {
        this.expiresTime = expiresTime;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getSecretType() {
        return secretType;
    }

    public void setSecretType(String secretType) {
        this.secretType = secretType;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPf() {
        return pf;
    }

    public void setPf(String pf) {
        this.pf = pf;
    }

    public String getPay_token() {
        return pay_token;
    }

    public void setPay_token(String pay_token) {
        this.pay_token = pay_token;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getIconQzone() {
        return iconQzone;
    }

    public void setIconQzone(String iconQzone) {
        this.iconQzone = iconQzone;
    }

    public String getPfkey() {
        return pfkey;
    }

    public void setPfkey(String pfkey) {
        this.pfkey = pfkey;
    }

    public int getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(int expiresIn) {
        this.expiresIn = expiresIn;
    }
}
