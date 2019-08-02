import org.junit.Test;
import smart.annotations.Sservice;
import smart.business.service.LoginService;
import smart.utils.BeanHelper;
import smart.utils.LoaderHelper;

/**
 * @author : pettygadfly@gmail.com
 * @description :
 * @date : 2019-07-31
 */
public class TestStarter {
    @Test
    public void bootstrap() {
        // 临时.class文件生成地址
//        System.setProperty(DebuggingClassWriter.DEBUG_LOCATION_PROPERTY, "./");
        LoaderHelper.init();
        LoginService loginService = BeanHelper.getBean(LoginService.class);
        loginService.doLogin();
    }
}
