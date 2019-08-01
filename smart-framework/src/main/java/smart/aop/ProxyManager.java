package smart.aop;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;

import java.util.List;

/**
 * @author : pettygadfly@gmail.com
 * @description :
 * @date : 2019-08-01
 */
public class ProxyManager {
    public static <T> T createProxy(final Class<?> targetClass, final List<Proxy> proxyList) {
        try {
            return (T) Enhancer.create(targetClass,
                    (MethodInterceptor) (o, method, objects, methodProxy) ->
                            new ProxyChain(targetClass, o, method, methodProxy, objects, proxyList).doProxyChain());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}

