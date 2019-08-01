package smart.aop.test1;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author : pettygadfly@gmail.com
 * @description : jdk 动态代理
 * @date : 2019-07-31
 */
interface SqlExecutoer {
    void doExe();
}

class MysqlExecutoer implements SqlExecutoer {

    @Override
    public void doExe() {
        System.out.println("执行事物");
    }
}

class TransactionProxy implements InvocationHandler {
    private SqlExecutoer target;

    TransactionProxy(SqlExecutoer t) {
        target = t;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("开启事物");
        Object r = method.invoke(target, args);
        System.out.println("关闭事物");
        return r;
    }

    public <T> T getProxy() {
        return (T) Proxy.newProxyInstance(
                ClassLoader.getSystemClassLoader(),
                target.getClass().getInterfaces(),
                this);
    }
}

public class JDKProxy {


    public static void main(String[] args) {
        SqlExecutoer exe = new MysqlExecutoer();
        TransactionProxy transaProxy = new TransactionProxy(exe);
        SqlExecutoer p = transaProxy.getProxy();
        p.doExe();
    }
}
