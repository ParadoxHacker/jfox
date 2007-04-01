package jfox.test.ejb3.interceptor;

import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;

/**
 * @author <a href="mailto:jfox.young@gmail.com">Young Yang</a>
 */
public class OuterMethodInterceptor {

    @AroundInvoke
    public Object aourndInvoke(InvocationContext invocationContext) throws Exception{
        System.out.println("AroundInvoke: " + this.getClass().getName() + ".aroundInvoke, method: " + invocationContext.getMethod().getName());
        return invocationContext.proceed();
    }
}
