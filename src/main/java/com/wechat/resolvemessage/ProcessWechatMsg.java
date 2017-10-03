package com.wechat.resolvemessage;

import com.wechat.tuling.TulingApiProcess;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Created by zhangtj on 2017/10/3.
 */
@Component
public class ProcessWechatMsg {

    @Autowired
    private TulingApiProcess tulingApiProcess;

    public String resolveWechatMsg(Map map) {
        /** 以文本消息为例，调用图灵机器人api接口，获取回复内容 */
        String result = "default text";
        if("text".equals(map.get("MsgType"))){
            result = tulingApiProcess.getResult((String) map.get("Content"));
        }
        return result;
    }
}
