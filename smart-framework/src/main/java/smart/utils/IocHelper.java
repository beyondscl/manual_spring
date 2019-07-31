package smart.utils;

import smart.annotations.Aautowire;

import java.lang.reflect.Field;
import java.util.Map;

/**
 * @author : pettygadfly@gmail.com
 * @description :
 * @date : 2019-07-31
 */
public class IocHelper {
    static {
        Map<Class<?>, Object> beanMap = BeanHelper.getBeanMap();
        if (CollectionUtil.isNotEmpty(beanMap)) {
            for (Map.Entry<Class<?>, Object> beanEntry : beanMap.entrySet()) {
                Class<?> cla = beanEntry.getKey();
                Object beanInstance = beanEntry.getValue();
                Field[] beanFields = cla.getDeclaredFields();
                for (Field f:beanFields){
                    if (f.isAnnotationPresent(Aautowire.class)){
                        Class<?> beanFieldClass = f.getType();
                        Object beanFieldInstance = beanMap.get(beanFieldClass);
                        ReflectionUtil.setField(beanInstance, f, beanFieldInstance);
                    }
                }
//                Arrays.asList(beanFields).stream()
//                        .filter(f -> f.isAnnotationPresent(Aautowire.class))
//                        .map(field -> {
//                            Class<?> beanFieldClass = field.getType();
//                            Object beanFieldInstance = beanMap.get(beanFieldClass);
//                            ReflectionUtil.setField(beanInstance, field, beanFieldInstance);
//                            return field;
//                        });
            }
        }
    }
}
