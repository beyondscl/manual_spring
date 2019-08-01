package smart.aop;

import java.lang.reflect.Method;

/**
 * @author : pettygadfly@gmail.com
 * @description :
 * @date : 2019-08-01
 */
public class AspectProxy implements Proxy {
    @Override
    public Object doProxy(ProxyChain proxyChain) {
        Object result = null;
        Class<?> cls = proxyChain.getTargetClass();
        Method method = proxyChain.getTargetMethod();
        Object[] params = proxyChain.getMethodParams();
        System.out.println("下面打印的都可以作为钩子方法，在具体的实现类中实现自己想要的即可");
        System.out.println("比如拦截controller记录请求参数与时间");
        System.out.println("比如拦截service开启事物");
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

    boolean intercept(Class<?> cls, Method method, Object[] params) {
        return true;
    }
}
