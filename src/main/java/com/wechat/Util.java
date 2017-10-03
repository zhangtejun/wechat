package com.wechat;

import com.thoughtworks.xstream.XStream;
import com.wechat.entity.WechatXmlEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 公共方法
 * 
 * @author 10539
 * 
 */
public class Util {
	private static Logger logging = LoggerFactory.getLogger(Util.class);
	/**
	 * 判断一个Object是否为空 修改为支持List ,Map ,String
	 * 
	 * @param obj
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static boolean isNullOrEmpty(Object obj) {
		if (obj instanceof Object[]) {
			Object[] o = (Object[]) obj;
			if (o == null || o.length == 0) {
				return true;
			}
			return false;
		} else {
			if (obj instanceof String) {
				if ((obj == null) || (("").equals(((String) obj).trim()))) {
					return true;
				}
				return false;
			}
			if (obj instanceof List) {
				List objList = (List) obj;
				if (objList == null || objList.isEmpty()) {
					return true;
				}
				return false;
			}
			if (obj instanceof Map) {
				Map objMap = (Map) obj;
				if (objMap == null || objMap.isEmpty()) {
					return true;
				}
				return false;
			}
			if ((obj == null) || (("").equals(obj))) {
				return true;
			}
		}
		return false;
	}


    /**
     * 微信xml消息转map
     * @param is
     * @return
     * @throws DocumentException
     */
	public static WechatXmlEntity xmlToEntity(InputStream is) throws DocumentException, NoSuchFieldException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
		Map map = new HashMap();

		//inputstream 转dom
		Document dom = new SAXReader().read(is);

		//获取根节点
		Element root = dom.getRootElement();

		//获取根节点下所有子节点
		List<Element> list = root.elements();
		WechatXmlEntity wechatXmlEntity = new WechatXmlEntity();

		//发射set属性
		Class clazz = null;
		try {
			 clazz = Class.forName("com.wechat.entity.WechatXmlEntity");
		} catch (ClassNotFoundException e) {

			logging.info("error Class.forName");
			e.printStackTrace();
		}

		//转map
		for (Element element : list){
			map.put(element.getName(), element.getText());

			Field field = clazz.getDeclaredField(element.getName());//取得类属性
			//获取set方法
			Method method = clazz.getDeclaredMethod("set"+element.getName(),field.getType());
			//调用set方法
			method.invoke(wechatXmlEntity,element.getText());

		}
		System.out.println("##########"+map);
		return wechatXmlEntity;
	}

    /**
     * obj转xml string
     * @param object
     * @return
     * @throws DocumentException
     */
    public static String objToXml(Object object) throws DocumentException{
		XStream xs = new XStream();
		xs.alias("xml",object.getClass());

        return xs.toXML(object);
    }

    /**
     * 发送http get请求获取数据
     * @param url
     * @return
     */
    public static String getResultFromHttpGet(String url){
        HttpGet request = new HttpGet(url);
        String result = "";
        try {
            HttpResponse response = HttpClients.createDefault().execute(request);
            if(response.getStatusLine().getStatusCode()==200){
                result = EntityUtils.toString(response.getEntity());
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }


}
