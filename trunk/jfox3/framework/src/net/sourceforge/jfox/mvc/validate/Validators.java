package net.sourceforge.jfox.mvc.validate;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import net.sourceforge.jfox.mvc.InvocationException;


/**
 * @author <a href="mailto:yang_y@sysnet.com.cn">Young Yang</a>
 */
@SuppressWarnings("unchecked")
public class Validators {

    /**
     * validator annotation class => validatorClass() method
     */
    private static Map<Class<? extends Annotation>, Method> validatorMethodMap = new HashMap<Class<? extends Annotation>, Method>();

    /**
     * Validator class => Validator instance
     */
    private static Map<Class<? extends Validator>, Validator> validatorMap = new HashMap<Class<? extends Validator>, Validator>();

    public static Object validate(Field field, String input, Annotation validation) throws ValidateException, InvocationException {
        Validator validator = getValidator(validation);
        try {
            return validator.validate(input, validation);
        }
        catch (ValidateException ve) {
            ve.setInputField(field.getName());
            throw ve;
        }
    }

    synchronized static Validator getValidator(Annotation validation) throws InvocationException {

        if (!validatorMethodMap.containsKey(validation.getClass())) {
            try {
                Method validatorClassMethod = validation.getClass().getMethod("validatorClass");
                validatorMethodMap.put(validation.getClass(), validatorClassMethod);
            }
            catch (Exception e) {
                throw new InvocationException("Failed to reflect method Class<? extends Validator> validatorClass() from validator: " + validation, e);
            }
        }
        Method validatorClassMethod = validatorMethodMap.get(validation.getClass());

        Class<? extends Validator> validatorClass = null;
        try {
            validatorClass = (Class<? extends Validator>)validatorClassMethod.invoke(validation);
        }
        catch(Exception e) {
            throw new InvocationException("Failed to invoke  validatorClass() method from validator: " + validation, e);
        }
        
        if (!validatorMap.containsKey(validatorClass)) {
            try {
                Validator validator = validatorClass.newInstance();
                validatorMap.put(validatorClass, validator);
            }
            catch (Exception e) {
                throw new InvocationException("Failed Instantiate validator, " + validatorClass.getName(), e);
            }
        }
        return validatorMap.get(validatorClass);
    }

    public static void main(String[] args) {

    }
}