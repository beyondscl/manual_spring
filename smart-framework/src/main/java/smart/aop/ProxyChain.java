package smart.aop;

import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.util.List;

/**
 * @author : pettygadfly@gmail.com
 * @description :
 * @date : 2019-08-01
 */
public class ProxyChain {
    private Class<?> targetClass;
    private Object targetObject;
    private Method targetMethod;
    private MethodProxy methodProxy;
    private Object[] methodParams;

    private List<Proxy> proxyList;
    private int proxyIndex = 0;

    public ProxyChain(Class<?> targetClass, Object targetObject,
                      Method targetMethod, MethodProxy methodProxy,
                      Object[] methodParams, List<Proxy> proxyList) {
        this.targetClass = targetClass;
        this.targetObject = targetObject;
        this.targetMethod = targetMethod;
        this.methodProxy = methodProxy;
        this.methodParams = methodParams;
        this.proxyList = proxyList;
    }

    public Object doProxyChain() throws Throwable {
        Object result;
        if (proxyIndex < proxyList.size()) {
            result = proxyList.get(proxyIndex++).doProxy(this);
        } else {
            result = methodProxy.invokeSuper(targetObject, methodParams);
        }
        return result;
    }

    public Class<?> getTargetClass() {
        return targetClass;
    }

    public Object getTargetObject() {
        return targetObject;
    }

    public Method getTargetMethod() {
        return targetMethod;
    }

    public MethodProxy getMethodProxy() {
        return methodProxy;
    }

    public Object[] getMethodParams() {
        return methodParams;
    }

    public List<Proxy> getProxyList() {
        return proxyList;
    }

    public int getProxyIndex() {
        return proxyIndex;
    }
}
