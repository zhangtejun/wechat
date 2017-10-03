package com.wechat.tuling;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wechat.Util;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * 图灵API 处理微信消息
 * Created by zhangtj on 2017/10/3.
 */
@Component
public class TulingApiProcess {

    public String getResult(String msg){
        /** 此处为图灵api接口，参数key需要自己去注册申请，先以11111111代替 */
        String apiUrl = "http://www.tuling123.com/openapi/api?key=b4241f047c3f4c21af8d3ac29e80441d&info=";
        String param = "";
        try {
            param = apiUrl+ URLEncoder.encode(msg,"utf-8")+"&userid=zhangtejun";//将参数转为url编码
        } catch (UnsupportedEncodingException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

        /** 发送httpget请求 */
        String result = Util.getResultFromHttpGet(param);

        /** 请求失败处理 */
        if(Util.isNullOrEmpty(result)){
            return "对不起，你说的话真是太高深了……";
        }

            //json 转  map
            ObjectMapper mapper = new ObjectMapper();
            String str = null;
            Map map = new HashMap();
            try {
                 map = mapper.readValue(result,Map.class);
            } catch (IOException e) {
                //
                e.printStackTrace();
            }

        return (String)map.get("text");
    }
}
