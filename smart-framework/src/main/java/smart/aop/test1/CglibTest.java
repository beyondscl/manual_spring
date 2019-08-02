package smart.aop.test1;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import org.apache.commons.lang3.ArrayUtils;

import java.lang.reflect.Method;
import java.util.Date;

/**
 * @author : pettygadfly@gmail.com
 * @description :
 * 1.简单代理实现
 * 2.如果代理的对象中有n个被注入的对象呢
 * @date : 2019-07-31
 */
interface MyLogger {
    void log(Object... args);
}

class Log4j implements MyLogger {
    private Date now;

    static {
    }

    @Override
    public void log(Object... args) {
        System.out.println("这里是日志:" + ArrayUtils.toString(args));
    }
}

class Log4jProxy implements MethodInterceptor {

    public <T> T getProxy(Class<T> cla) {
        return (T) Enhancer.create(cla, this);
    }

    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        System.out.println("start time " + System.nanoTime());
        Object r = methodProxy.invokeSuper(o, objects);
        System.out.println("end time " + System.nanoTime());
        return r;
    }
}

public class CglibTest {
    public static void main(String[] args) {
        Log4jProxy p = new Log4jProxy();
        Log4j real = p.getProxy(Log4j.class);


        real.log("哈哈", "呵呵");
    }
}
