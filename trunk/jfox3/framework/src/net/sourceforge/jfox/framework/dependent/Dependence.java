package net.sourceforge.jfox.framework.dependent;

/**
 * @author <a href="mailto:jfox.young@gmail.com">Young Yang</a>
 */
public interface Dependence {

    /**
     * 解析 Dependence，并注入到 instance 中
     * 如果 instance 为 null, 则说明是 class level 的依赖
     * 对于EJB注入，instance 为 EJBContext
     * @param instance ejb context if EJB
     */
    void inject(Object instance) throws InjectionException;

}
