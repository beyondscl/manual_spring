package smart.aop;

import java.lang.reflect.Method;

/**
 * @author : pettygadfly@gmail.com
 * @description : 切面的抽象实现，
 * 增强类的具体实现可以重写固定的几个方法，
 * 比如拦截service开启事物，拦截controller记录请求参数与时间等等
 * @date : 2019-08-01
 */
public class AspectProxy implements Proxy {
    @Override
    public Object doProxy(ProxyChain proxyChain) {
        Object result = null;
        Class<?> cls = proxyChain.getTargetClass();
        Method method = proxyChain.getTargetMethod();
        Object[] params = proxyChain.getMethodParams();
        aspectStart();
        try {
            if (intercept(cls, method, params)) {
                aspectBefore(cls, method, params);
                result = proxyChain.doProxyChain();
                aspectAfter(cls, method, params, result);
            } else {
                result = proxyChain.doProxyChain();
            }
        } catch (Exception e) {
            aspectError();
            e.printStackTrace();
        } catch (Throwable throwable) {
            aspectError();
            throwable.printStackTrace();
        } finally {
            aspectEnd();
        }
        return result;
    }

    public void aspectError() {
    }

    public void aspectEnd() {
    }

    protected void aspectAfter(Class<?> cls, Method method, Object[] params, Object result) {
    }

    public void aspectBefore(Class<?> cls, Method method, Object[] params) {
    }

    public void aspectStart() {
    }

    /**
     * 判断方法是否需要代理,由子类重写
     * @param cls
     * @param method
     * @param params
     * @return
     */
    boolean intercept(Class<?> cls, Method method, Object[] params) {
        return true;
    }
}
