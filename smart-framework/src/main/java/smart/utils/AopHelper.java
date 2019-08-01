package smart.utils;

import smart.aop.AspectProxy;
import smart.aop.Proxy;
import smart.aop.ProxyManager;
import smart.aop.annotaions.Aspect;

import java.lang.annotation.Annotation;
import java.util.*;

/**
 * @author : pettygadfly@gmail.com
 * @description :
 * @date : 2019-08-01
 */
public class AopHelper {
    /**
     * 初始化
     */
    static {
        /**
         * proxyMap:
         *  key : ServiceAspect.class,Controller.class....
         *  value: ServiceAspect.class拦截的所有类，Controller.class拦截的所有类
         */
        Map<Class<?>, Set<Class<?>>> proxyMap = createProxyMap();
        /**
         * targetMap:
         * key : LoginService.class
         * value: ServiceProxy,LogProxy 等等
         */
        Map<Class<?>, List<Proxy>> targetMap = createTargetMap(proxyMap);

        for (Map.Entry<Class<?>, List<Proxy>> targetEntry : targetMap.entrySet()) {
            Class<?> targetClass = targetEntry.getKey();
            List<Proxy> proxyList = targetEntry.getValue();
            /**
             * 将LoginService.class 替换为代理LoginService.class$..类，
             * 那么放在另外等集合里面，将原始的类保留
             */
            Object proxy = ProxyManager.createProxy(targetClass, proxyList);
            BeanHelper.setBean(targetClass, proxy);
        }
    }

    /**
     * 获取需要被增强的注解，比如@serviceAspect,@controllerAspect...
     *
     * @param aspect
     * @return
     */
    private static Set<Class<?>> createTargetClassSet(Aspect aspect) {
        Set<Class<?>> targetClassSet = new HashSet<>(12);
        Class<? extends Annotation> anno = aspect.value();
        if (anno != null && !anno.equals(Aspect.class)) {
            targetClassSet.addAll(ClassHelper.getClass(anno));
            return targetClassSet;
        }
        return null;
    }

    /**
     * 代理类，对应的目标类
     */
    private static Map<Class<?>, Set<Class<?>>> createProxyMap() {
        Map<Class<?>, Set<Class<?>>> proxyMap = new HashMap<>();
        Set<Class<?>> proxyClassSet = ClassHelper.getClassSetBySuper(AspectProxy.class);

        for (Class<?> proxyClass : proxyClassSet) {
            if (proxyClass.isAnnotationPresent(Aspect.class)) {
                Aspect aspect = proxyClass.getAnnotation(Aspect.class);
                Set<Class<?>> targetClassSet = createTargetClassSet(aspect);
                proxyMap.put(proxyClass, targetClassSet);
            }
        }
        return proxyMap;
    }

    /**
     * 目标类与代理类之间的关系
     *
     * @param proxyMap
     * @return
     */
    private static Map<Class<?>, List<Proxy>> createTargetMap(Map<Class<?>, Set<Class<?>>> proxyMap) {
        Map<Class<?>, List<Proxy>> targetMap = new HashMap<>();
        for (Map.Entry<Class<?>, Set<Class<?>>> proxyEntry : proxyMap.entrySet()) {
            Class<?> proxyClass = proxyEntry.getKey();
            Set<Class<?>> targetClassSet = proxyEntry.getValue();
            for (Class<?> targetClass : targetClassSet) {
                try {
                    Proxy proxy = (Proxy) proxyClass.newInstance();
                    if (targetMap.containsKey(targetClass)) {
                        targetMap.get(targetClass).add(proxy);
                    } else {
                        List<Proxy> proxyList = new ArrayList<>();
                        proxyList.add(proxy);
                        targetMap.put(targetClass, proxyList);
                    }
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        return targetMap;
    }
}
