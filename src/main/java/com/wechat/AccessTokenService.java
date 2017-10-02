package com.wechat;

/**
 * Created by 10539 on 2017/9/14.
 * 根据凭证和凭证密钥获取access_token
 */
public interface AccessTokenService {

    public String getAccessTokenFirst(AccessToken accessTokenModel,String appID, String appSecret);

    public String getAccessToken(AccessToken accessTokenModel,String appID, String appSecret);
}
