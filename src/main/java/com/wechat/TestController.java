package com.wechat;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wechat.entity.WechatXmlEntity;
import com.wechat.resolvemessage.ProcessWechatMsg;
import com.wechat.tuling.TulingApiProcess;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class TestController {
    Logger logging = LoggerFactory.getLogger(TestController.class);

    @Autowired
    ProcessWechatMsg processWechatMsg;



    @RequestMapping(value = "/",produces= MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String index(){
        System.out.printf("iinn");
        return "{\"aa\":\"1231\"}";
    }
    @RequestMapping(value = "/wechat.do",produces="text/plain;charset=UTF-8")
    @ResponseBody
    public String index1(HttpServletRequest request) throws IOException, DocumentException, InvocationTargetException, NoSuchMethodException, IllegalAccessException, NoSuchFieldException {

        System.out.println("start....");
        Enumeration parameterNames = request.getParameterNames();
        Map map = new HashMap();
        while(parameterNames.hasMoreElements()){
            String paraName=(String)parameterNames.nextElement();
            map.put(paraName,request.getParameter(paraName));
        }
        System.out.println(map);

        //处理微信数据
        InputStream  is = request.getInputStream();
       logging.info("将微信消息xml转为obj start");
        WechatXmlEntity wxe = Util.xmlToEntity(is);//将微信消息xml转为obj
        logging.info("将微信消息xml转为obj  end");
        logging.info("@@@@@@toString: "+wxe.toString());

        String str = processWechatMsg.resolveWechatMsg(wxe);//解析来自微信的信息

        logging.info("return  str : "+str);
        return str;
    }

    @RequestMapping(value = "/index1.do",produces= MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String index2(){
        System.out.printf("iinn");
        return "{\"aa\":\"index2\"}";
    }
}
