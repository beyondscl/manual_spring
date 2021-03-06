package smart.utils;

import lombok.extern.slf4j.Slf4j;
import smart.config.Config;

import java.lang.annotation.Annotation;
import java.util.HashSet;
import java.util.Set;

/**
 * @author : pettygadfly@gmail.com
 * @description :
 * @date : 2019-07-31
 */
@Slf4j
public final class ClassHelper {
    private static final String basePackage = Config.getBasePackage();
    private static final Set<Class<?>> CLASS_SET = ClassUtil.getClassSet(basePackage);

    public static Set<Class<?>> getClassSet() {
        return CLASS_SET;
    }

    /**
     * 获取包含注解？的类
     *
     * @param annotationClass
     * @return
     */
    public static Set<Class<?>> getClass(Class<? extends Annotation> annotationClass) {
        Set<Class<?>> set = new HashSet<>(128);
        for (Class<?> c : CLASS_SET) {
            if (c.isAnnotationPresent(annotationClass)) {
                set.add(c);
            }
        }
        return set;
    }

    /**
     * 获取包名下某父类(或接口)的所有子类
     */
    public static Set<Class<?>> getClassSetBySuper(Class<?> superclass) {
        Set<Class<?>> set = new HashSet<>(12);
        for (Class<?> c : CLASS_SET) {
            if (superclass.isAssignableFrom(c)) {
                set.add(c);
            }
        }
        return set;
    }
}
