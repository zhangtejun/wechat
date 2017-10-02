import com.wechat.AccessToken;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by 10539 on 2017/9/17.
 */
public class Testmybatis {
    public static void main(String[] args) {
        String resource = "mybatis/mapping/sqlMapConfig.xml";
        InputStream inputStream = null;
        try {
            inputStream = Resources.getResourceAsStream(resource);
        } catch (IOException e) {
            e.printStackTrace();
        }
        SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
        SqlSessionFactory factory = builder.build(inputStream);
        SqlSession sqlSession = factory.openSession();
        //sqlSession.getMapper(AccessToken.class);
//        sqlSession.selectList()

    }
}
