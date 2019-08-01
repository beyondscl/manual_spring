package smart.utils;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author : pettygadfly@gmail.com
 * @description :
 * @date : 2019-07-31
 */
public class BeanHelper {
    private static final ConcurrentHashMap<Class<?>, Object> BEAN_MAP = new ConcurrentHashMap<>(256);

    static {
        Set<Class<?>> beanClassSet = ClassHelper.getClassSet();
        for (Class<?> beanClass : beanClassSet) {
            Object obj = ReflectionUtil.newInstance(beanClass);
            BEAN_MAP.put(beanClass, obj);
        }
    }

    public static ConcurrentHashMap<Class<?>, Object> getBeanMap() {
        return BEAN_MAP;
    }

    public static <T> T getBean(Class<T> cla) {
        if (!BEAN_MAP.containsKey(cla)) {
            throw new RuntimeException("no bean found:" + cla);
        }
        return (T) BEAN_MAP.get(cla);
    }

    public static void setBean(Class<?> cla, Object obj) {
        BEAN_MAP.put(cla, obj);

    }
}