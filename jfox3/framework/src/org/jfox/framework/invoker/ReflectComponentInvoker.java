package org.jfox.framework.invoker;

import java.lang.reflect.Method;

import org.jfox.framework.BaseException;
import org.jfox.framework.ComponentId;
import org.jfox.framework.component.Component;
import org.jfox.framework.component.ComponentInvocationException;

/**
 * 在同一个JVM中，直接通过反射调用 Component
 *
 * @author <a href="mailto:jfox.young@gmail.com">Young Yang</a>
 */
public class ReflectComponentInvoker implements ComponentInvoker {

    public Object invokeMethod(Component theComponent, ComponentId componentId, Method method, Object... args) throws BaseException {
        try {
            //TODO: maybe need to get the concrete method of component implementation if use Annotation
            return method.invoke(theComponent, args);
        }
        catch (Exception e) {
            throw new ComponentInvocationException("Invoke method " + theComponent.getClass().getName() + "." + method.getName() + " failed, ComponentId is " + componentId, e);
        }
    }

    public static void main(String[] args) {

    }
}
