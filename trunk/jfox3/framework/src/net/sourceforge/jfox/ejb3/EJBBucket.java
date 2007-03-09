package net.sourceforge.jfox.ejb3;

import java.lang.reflect.Method;
import java.util.Collection;
import javax.ejb.EJBContext;
import javax.ejb.EJBObject;
import javax.naming.Context;

import net.sourceforge.jfox.framework.component.Module;
import net.sourceforge.jfox.ejb3.interceptor.InterceptorMethod;

/**
 * @author <a href="mailto:jfox.young@gmail.com">Young Yang</a>
 */
public interface EJBBucket {

    /**
     * ejb name
     */
    String getEJBName();

    /**
     * description infomation
     */
    String getDescription();

    /**
     * JNDI name
     */
    String[] getMappedNames();

    /**
     * Bean Class
     */
    Class<?> getBeanClass();

    /**
     * EJB 的接口，可以由 @Remote @Local指定，未指定则为Bean所有接口
     */
    Class<?>[] getBeanInterfaces();

    EJBObjectId createEJBObjectId();
    
    /**
     * new EJB instance, return bean instance, not EJBObject
     *
     * @param ejbObjectId ejb object id
     * @throws Exception exception
     */
    AbstractEJBContext newEJBContext(EJBObjectId ejbObjectId) throws Exception;

    /**
     * 重用 ejb instance
     *
     * @param ejbContext ejb context
     * @throws Exception exception
     */
    void reuseEJBContext(AbstractEJBContext ejbContext) throws Exception;

    /**
     * EJB 所在的 Module
     */
    Module getModule();

    /**
     * 部署的 EJB 容器
     */
    EJBContainer getEJBContainer();

    /**
     * 获得 EJB 存根，作为对EJB的引用，用来 bind 到 jndi，以及 inject
     */
    EJBObject createProxyStub();

    /**
     * \@Interceptors on class
     */
    Collection<InterceptorMethod> getClassInterceptorMethods();

    /**
     * \@Interceptors on concreteMethod
     * @param concreteMethod concrete Method
     */
    Collection<InterceptorMethod> getMethodInterceptorMethods(Method concreteMethod);

    /**
     * \@AroundInvoke in bean class
     */
    Collection<InterceptorMethod> getBeanInterceptorMethods();
    
    /**
     * 是否是 @Remote EJB
     */
    boolean isRemote();

    /**
     * 是否是 @Local EJB
     */
    boolean isLocal();

    /**
     * 是否以该接口发布
     * @param beanInterface 是否在 Remote/Local 中指定该 Bean interface
     */
    boolean isBusinessInterface(Class beanInterface);

    Method getConcreteMethod(Method interfaceMethod);

    AbstractEJBContext createEJBContext(EJBObjectId ejbObjectId, Object instance);

    /**
     * EJB env context, java:comp/env
     * @param ejbObjectId ejb object id
     */
    Context getENContext(EJBObjectId ejbObjectId);

    /**
     * 销毁 EJBBucket
     */
    void destroy();
}
