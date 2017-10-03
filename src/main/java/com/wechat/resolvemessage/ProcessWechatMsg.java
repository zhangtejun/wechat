package com.wechat.resolvemessage;

import com.wechat.Util;
import com.wechat.entity.WechatXmlEntity;
import com.wechat.tuling.TulingApiProcess;
import org.dom4j.DocumentException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.beans.BeanMap;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Created by zhangtj on 2017/10/3.
 */
@Component
public class ProcessWechatMsg {

    @Autowired
    private TulingApiProcess tulingApiProcess;

    public String resolveWechatMsg(WechatXmlEntity wxe) throws DocumentException {
        /** 以文本消息为例，调用图灵机器人api接口，获取回复内容 */
        String result = "default text";
        if("text".equals(wxe.getMsgType())){
            result = tulingApiProcess.getResult(wxe.getContent());
        }else{
            //临时处理
            result = "未搜索到关键字";
            wxe.setMsgType("text");
        }
        return  resolveSendMsg(wxe,result);//封装返回报文
    }

    /**
     * 封装返回报文
     * @param wxe
     * @param result
     * @return
     */
    private String resolveSendMsg(WechatXmlEntity wxe, String result) throws DocumentException {

        WechatXmlEntity send = new WechatXmlEntity();

        send.setToUserName(wxe.getFromUserName());
        send.setFromUserName(wxe.getToUserName());
        send.setCreateTime(System.currentTimeMillis()+"");
        send.setMsgType(wxe.getMsgType());
        send.setContent(result);


        return Util.objToXml(send);
    }


}
