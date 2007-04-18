package org.jfox.mvc.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.jfox.mvc.Invocation;

/**
 * 表示一个 Action 方法
 * 用该 Annotation 描述的方法，需要满足以下条件
 * 1.只有一个参数 Invotation
 * 2.返回 PageContext 类型
 *
 * @author <a href="mailto:jfox.young@gmail.com">Young Yang</a>
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface ActionMethod {

    public static enum TargetMethod {
        FORWARD, REDIRECT
    }

    /**
     * 调用成功时，跳转的页面 
     */
    String successView();

    /**
     * 发生错误时，跳转的页面，如果没有定义，讲直接在浏览器中显示异常信息
     */
    String errorView() default "";

    /**
     * 跳转的方式，默认为 forward
     */
    TargetMethod targetMethod() default TargetMethod.FORWARD;

    /**
     * 用来组装HttpRequest参数的类，为一个标准的Java Bean，file name 与 form input name对应。
     * MVC framework会自动根据Field进行组装。
     *
     * 可以在field上加上validate annotation来进行数据校验。
     *
     * 支持 file upload，文件上传的Field类型必须为 FileUploaded
     */
    Class invocationClass() default Invocation.class;
}