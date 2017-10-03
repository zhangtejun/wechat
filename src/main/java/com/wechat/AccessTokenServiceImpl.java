package com.wechat;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationObjectSupport;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 10539 on 2017/9/14.
 * 获取微信access_token
 */
public class AccessTokenServiceImpl extends WebApplicationObjectSupport implements AccessTokenService {

    public    boolean  isDebug = false;//测试开关


    SqlSessionTemplate sqlSessionTemplate;

    public boolean isDebug() {
        return isDebug;
    }

    public void setDebug(boolean debug) {
        isDebug = debug;
    }

    public SqlSessionTemplate getSqlSessionTemplate() {
        return sqlSessionTemplate;
    }

    public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
        this.sqlSessionTemplate = sqlSessionTemplate;
    }

    public String getAccessToken(AccessToken accessToken,String appID, String appSecret) {
        AccessTokenModel accessTokenModel = (AccessTokenModel) accessToken;
        String atoken =  accessTokenModel.getAccess_token();
        if (Util.isNullOrEmpty(atoken)){
            return getAccessTokenFirst(accessToken,appID,appSecret);
        }else {//是否过期
            Long nowDate = System.currentTimeMillis();
            if ((nowDate - accessTokenModel.getExpires_time()) < Long.valueOf(accessTokenModel.getExpires_in())*1000) {
                return atoken;//未过期，直接返回
            }
            //已过期重数据库取
            List list = sqlSessionTemplate.selectList("mybatis.testWeChat");
            if (!Util.isNullOrEmpty(list)) {
                Map map = (Map) list.get(0);
                String aToken = (String) map.get("accessToken");
                String expiresIn = (String) map.get("expiresIn");
                Date expiresTime = (Date) map.get("expiresTime");

                if (!(nowDate - expiresTime.getTime() > Long.valueOf(expiresIn))) {
                    accessTokenModel.setAccess_token(aToken);
                    return aToken;
                }
            }
        }
        //数据库无记录或者已过期，
        //发请求去获取
        return getAccessTokenForURL(accessTokenModel,appID,appSecret);
    }


    public String getAccessTokenFirst(AccessToken accessToken,String appID, String appSecret) {
        AccessTokenModel accessTokenModel = (AccessTokenModel) accessToken;

        //优先取数据库里保存的信息
        List list = sqlSessionTemplate.selectList("mybatis.testWeChat");
        if (!Util.isNullOrEmpty(list)) {

            Long nowDate = System.currentTimeMillis();
            Map map = (Map) list.get(0);
            accessTokenModel.setFisrstQry(false);
            String aToken = (String) map.get("accessToken");
            String expiresIn = (String) map.get("expiresIn");
            Date expiresTime = (Date) map.get("expiresTime");
            if (!(nowDate - expiresTime.getTime() > Long.valueOf(expiresIn)*1000)) {
                accessTokenModel.setAccess_token(aToken);
                return aToken;//如果已有且未过期，则返回。否则发请求去获取
            }
        }else {//无记录
            accessTokenModel.setFisrstQry(true);
        }
        //发请求去获取
        return getAccessTokenForURL(accessTokenModel,appID,appSecret);
    }

    /**
     * 发送http get请求
     */
    private String getAccessTokenForURL(AccessToken accessToken,String appID, String appSecret) {
        AccessTokenModel accessTokenModel = (AccessTokenModel) accessToken;
        //测试使用
        if(isDebug){
            return getAccessTokenForURLtest(accessTokenModel,appID,appSecret);
        }

        String param = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=" + appID + "&secret=" + appSecret;

        /** 发送http get请求 */
        String result = Util.getResultFromHttpGet(param);
        JSONObject json = new JSONObject(result);
        Map map = json.toMap();
        if (!Util.isNullOrEmpty(map.get("errmsg"))) {
            System.err.println("获取access_token error " + json.getString("errmsg"));
            return null;
        }
        result = (String) map.get("access_token");
        Long nowDate = System.currentTimeMillis();
        accessTokenModel.setExpires_in((String.valueOf(map.get("expires_in"))));
        accessTokenModel.setExpires_time(nowDate);
        accessTokenModel.setAccess_token(result);//保存access_token至AccessTokenModel
        Map hashMap = new HashMap();
        hashMap.put("AccessToken",result);
        hashMap.put("ExpiresIn",map.get("expires_in"));
        hashMap.put("ExpiresTime",new Date(nowDate));
        hashMap.put("UserId","1053946416");

        //是否已有记录
        if(accessTokenModel.isFisrstQry){//插入操作
            sqlSessionTemplate.insert("mybatis.testWeChatInsert", hashMap);//保存至数据库
        }else{//更新操作
            sqlSessionTemplate.update("mybatis.testWeChatUpdate",hashMap);
        }
        return result;
    }

    /**
     * 测试用例
     * @param accessToken
     * @param appID
     * @param appSecret
     * @return
     */
    private String getAccessTokenForURLtest(AccessTokenModel accessToken,String appID, String appSecret) {
        String result = "{\"access_token\":\"123321\",\"expires_in\":60}";


        JSONObject json = new JSONObject(result);
        Map map = json.toMap();
        if (!Util.isNullOrEmpty(map.get("errmsg"))) {
            System.out.println("获取access_token error " + json.getString("errmsg"));
            return null;
        }
        result = (String) map.get("access_token");
        Long nowDate = System.currentTimeMillis();
        accessToken.setExpires_in((String.valueOf(map.get("expires_in"))));
        accessToken.setExpires_time(nowDate);
        accessToken.setAccess_token(result);//保存access_token至AccessTokenModel
        Map hashMap = new HashMap();
        hashMap.put("AccessToken",result);
        hashMap.put("ExpiresIn",map.get("expires_in"));
        hashMap.put("ExpiresTime",new Date(nowDate));
        hashMap.put("UserId","1053946416");

        //是否已有记录
        if(accessToken.isFisrstQry){//插入操作
            sqlSessionTemplate.insert("mybatis.testWeChatInsert", hashMap);//保存至数据库
        }else{//更新操作
            sqlSessionTemplate.update("mybatis.testWeChatUpdate",hashMap);
        }
        return result;
    }
}

