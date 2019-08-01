package smart.utils;

/**
 * @author : pettygadfly@gmail.com
 * @description :
 * @date : 2019-07-31
 */
public final class LoaderHelper {
    /**
     * 注意顺序
     */
    public static void init() {
        Class<?>[] classList = {ClassHelper.class, BeanHelper.class, IocHelper.class,AopHelper.class};
        for (Class<?> cla : classList) {
            ClassUtil.loadClass(cla.getName(), true);
        }
    }
}
