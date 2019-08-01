import org.junit.Test;
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
        LoaderHelper.init();
        LoginService loginService = BeanHelper.getBean(LoginService.class);
        loginService.doLogin();
    }
}
