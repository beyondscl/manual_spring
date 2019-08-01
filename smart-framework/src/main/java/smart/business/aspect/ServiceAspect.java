package smart.business.aspect;

import lombok.extern.slf4j.Slf4j;
import smart.annotations.Sservice;
import smart.aop.AspectProxy;
import smart.aop.annotaions.Aspect;

import java.lang.reflect.Method;

/**
 * @author : pettygadfly@gmail.com
 * @description :
 * @date : 2019-08-01
 */
@Slf4j
@Aspect(value = Sservice.class)
public class ServiceAspect extends AspectProxy {
    @Override
    protected void aspectAfter(Class<?> cls, Method method, Object[] params, Object result) {
        System.out.println("ServiceAspect,after");
    }

    @Override
    public void aspectBefore(Class<?> cls, Method method, Object[] params) {
        System.out.println("ServiceAspect,before");
    }
}
