package smart.business.aspect;

import lombok.extern.slf4j.Slf4j;
import smart.annotations.Sservice;
import smart.aop.AspectProxy;
import smart.aop.annotaions.Aspect;
import smart.aop.annotaions.Transaction;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * @author : pettygadfly@gmail.com
 * @description :
 * @date : 2019-08-01
 */
@Slf4j
@Aspect(value = Sservice.class)
public class TransactionAspect extends AspectProxy {
    @Override
    protected void aspectAfter(Class<?> cls, Method method, Object[] params, Object result) {
        System.out.println("获取connection 提交并 开启自动提交");
    }

    @Override
    public void aspectBefore(Class<?> cls, Method method, Object[] params) {
        System.out.println("获取connection 关闭自动提交");
    }

    @Override
    public boolean intercept(Class<?> cls, Method method, Object[] params) {
        Annotation[] ans = method.getAnnotations();
        if (null == ans || ans.length == 0) {
            return false;
        }
        for (int i = 0; i < ans.length; i++) {
            if (ans[i].annotationType().getSimpleName().equals(Transaction.class.getSimpleName())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void aspectError() {
        System.out.println("判断异常并决定是否需要回滚");
    }
}