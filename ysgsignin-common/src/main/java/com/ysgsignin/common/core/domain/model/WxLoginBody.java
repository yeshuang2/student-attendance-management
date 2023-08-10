package com.ysgsignin.common.core.domain.model;

/**
 * 微信用户登录对象
 *
 * @author ruoyi
 */


public class WxLoginBody
{
    /**
     * 暂时登陆凭据 code 只能运用一次
     */
    private String code;
    /**
     * 偏移量
     */
    private String encryptedIv;
    /**
     * 加密数据
     */
    private String encryptedData;



    private String  nickName;

    private String gender;


    private  String avatarUrl;


    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getCode() {
        return code;
    }
    public void setCode(String code) {
        this.code = code;
    }
    public String getEncryptedIv() {
        return encryptedIv;
    }
    public void setEncryptedIv(String encryptedIv) {
        this.encryptedIv = encryptedIv;
    }
    public String getEncryptedData() {
        return encryptedData;
    }
    public void setEncryptedData(String encryptedData) {
        this.encryptedData = encryptedData;
    }
}
