import com.wechat.AccessToken;
import com.wechat.AccessTokenModel;
import com.wechat.AccessTokenService;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.web.context.support.XmlWebApplicationContext;

/**
 * Created by 10539 on 2017/9/16.
 */
public class Testtoken {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext classPathXmlApplicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");

         AccessTokenService accessTokenService = (AccessTokenService) classPathXmlApplicationContext.getBean("accessTokenService");
        AccessToken accessToken = (AccessToken) classPathXmlApplicationContext.getBean("accessToken");
        //
        //
        String aa =accessTokenService.getAccessToken(accessToken,"wx21caaaa93e3c0bf5","a1878166fae6de0d467ffff05e48730f");
        System.out.println(aa);
    }
}
