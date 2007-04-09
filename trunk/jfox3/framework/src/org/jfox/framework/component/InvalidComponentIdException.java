package org.jfox.framework.component;

import org.jfox.framework.BaseRuntimeException;

/**
 * @author <a href="mailto:jfox.young@gmail.com">Young Yang</a>
 */
public class InvalidComponentIdException extends BaseRuntimeException {


    public InvalidComponentIdException() {
    }

    public InvalidComponentIdException(String message) {
        super(message);
    }

    public InvalidComponentIdException(String message, Throwable cause) {
        super(message, cause);
    }

    public static void main(String[] args) {

    }
}