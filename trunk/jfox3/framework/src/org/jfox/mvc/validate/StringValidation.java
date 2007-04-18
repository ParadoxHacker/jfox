package org.jfox.mvc.validate;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.annotation.ElementType;

/**
 * 字符串验证 Annotation
 * @author <a href="mailto:jfox.young@gmail.com">Yang Yong</a>
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface StringValidation {

    /**
     * default String validator class 
     */
    Class<? extends Validator> validatorClass() default StringValidator.class;

    /**
     * 最小长度
     */
    int minLength() default Integer.MIN_VALUE;

    /**
     * 最大长度
     */
    int maxLength() default Integer.MAX_VALUE;

    /**
     * 是否可以为空
     */
    boolean nullable() default false;
}