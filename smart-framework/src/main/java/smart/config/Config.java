package smart.config;


import java.util.ResourceBundle;

/**
 * @author : pettygadfly@gmail.com
 * @description :
 * @date : 2019-07-31
 */
public class Config {
    private static final String DEFAULT_FILE_NAME = "smart-framework";
    private static final String NAME = "smart.mvc.app.name";
    private static final String BASE_PACKAGE = "smart.mvc.app.base_package";
    private static final String JSP_PACKAGE = "smart.mvc.app.jsp_package";
    private static final String ASSETS_PACKAGE = "smart.mvc.app.assets_package";

    private final static ResourceBundle PROPS = ResourceBundle.getBundle(DEFAULT_FILE_NAME);

    public static String getAppName() {
        return PROPS.getString(NAME);
    }

    public static String getBasePackage() {
        return PROPS.getString(BASE_PACKAGE);
    }

}
