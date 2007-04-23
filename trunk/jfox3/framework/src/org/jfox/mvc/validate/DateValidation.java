package org.jfox.mvc.validate;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.annotation.ElementType;

/**
 * @author <a href="mailto:jfox.young@gmail.com">Yang Yong</a>
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface DateValidation {

    /**
     * default email validator class
     */
    Class<? extends Validator> validatorClass() default DateValidator.class;

    String format() default "yyyy-MM-dd";

    /**
     * 是否可以为空
     */
    boolean nullable() default false;
}