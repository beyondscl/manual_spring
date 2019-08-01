package smart.aop;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;

import java.util.List;

/**
 * @author : pettygadfly@gmail.com
 * @description : 创建代理类
 * @date : 2019-08-01
 */
public class ProxyManager {
    public static <T> T createProxy(final Class<?> targetClass, final List<Proxy> proxyList) {
        return (T) Enhancer.create(targetClass,
                (MethodInterceptor) (o, method, objects, methodProxy) ->
                        new ProxyChain(targetClass, o, method, methodProxy, objects, proxyList).doProxyChain()
        );
    }
}

