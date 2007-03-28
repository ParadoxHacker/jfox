package org.jfox.mvc.validate;

import org.jfox.framework.BaseException;

/**
 * @author <a href="mailto:jfox.young@gmail.com">Young Yang</a>
 */
public class ValidateException extends BaseException {

    // 输入框名称
    private String inputField;
    private Object inputValue;

    public ValidateException(String message, Object inputValue) {
        super(message);
        this.inputValue = inputValue;
    }

    public ValidateException(String message, String inputFieldName, Object inputValue) {
        super(message);
        this.inputValue = inputValue;
        this.inputField = inputFieldName;
    }

    public void setInputField(String inputField) {
        this.inputField = inputField;
    }

    public String getInputField() {
        return inputField;
    }

    public Object getInputValue() {
        return inputValue;
    }

    public static void main(String[] args) {

    }
}
