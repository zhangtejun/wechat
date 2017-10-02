package com.wechat;

/**
 * Created by 10539 on 2017/9/14.
 * implements AccessToken
 */
public class AccessTokenModel implements AccessToken{
    public String access_token;
    public String expires_in;
    public Long expires_time;

    public boolean isFisrstQry = false;

    public void setFisrstQry(boolean fisrstQry) {
        isFisrstQry = fisrstQry;
    }

    public boolean isFisrstQry() {
        return isFisrstQry;
    }

    /**
     * 获取access_token
     * @return
     */
    public String getAccess_token() {
        return access_token;
    }
    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    /**
     * 返回获取时间
     * @return
     */
    public String getExpires_in() {
        return expires_in;
    }
    public void setExpires_in(String expires_in) {
        this.expires_in = expires_in;
    }

    public void setExpires_time(Long expires_time) {
        this.expires_time = expires_time;
    }

    public Long getExpires_time() {
        return expires_time;
    }
}
