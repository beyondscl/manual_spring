package smart.utils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author : pettygadfly@gmail.com
 * @description :
 * @date : 2019-07-31
 */
public class ReflectionUtil {

    public static Object newInstance(Class<?> cla) {
        try {
            return cla.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return null;
    }

    public Object invokeMethod(Object o, Method m, Object... args) {
        m.setAccessible(true);
        try {
            return m.invoke(o, args);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return o;
    }

    public static void setField(Object obj, Field field, Object arg) {
        field.setAccessible(true);
        try {
            field.set(obj, arg);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
