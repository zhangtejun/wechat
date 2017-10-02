package com.wechat;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

@Controller
public class TestController {
    Logger logging = LoggerFactory.getLogger(TestController.class);
    @RequestMapping(value = "/",produces= MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String index(){
        System.out.printf("iinn");
        return "{\"aa\":\"1231\"}";
    }
    @RequestMapping(value = "/wechat.do",method = RequestMethod.GET)
    @ResponseBody
    public String index1(HttpServletRequest request){
        Enumeration parameterNames = request.getParameterNames();
        Map map = new HashMap();
        while(parameterNames.hasMoreElements()){
            String paraName=(String)parameterNames.nextElement();
            map.put(paraName,request.getParameter(paraName));
        }

        //map 转 json
        ObjectMapper mapper = new ObjectMapper();
        String str = null;
        try {
            str  = mapper.writeValueAsString(map);
        } catch (JsonProcessingException e) {

            e.printStackTrace();
        }
        logging.info(str);//打印日志

        return str;
    }

    @RequestMapping(value = "/index1.do",produces= MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String index2(){
        System.out.printf("iinn");
        return "{\"aa\":\"index2\"}";
    }
}
