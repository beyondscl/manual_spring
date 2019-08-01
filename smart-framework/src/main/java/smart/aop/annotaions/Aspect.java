package smart.aop.annotaions;

import java.lang.annotation.*;

/**
 * @author : pettygadfly@gmail.com
 * @description : 定义一个切面，用于增强？注解的类
 * @date : 2019-08-01
 */
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Aspect {
    Class<? extends Annotation> value();
}
