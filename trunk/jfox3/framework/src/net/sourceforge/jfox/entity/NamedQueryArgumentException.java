package net.sourceforge.jfox.entity;

import javax.persistence.PersistenceException;

/**
 * @author <a href="mailto:jfox.young@gmail.com">Young Yang</a>
 */
public class NamedQueryArgumentException extends PersistenceException {


    public NamedQueryArgumentException() {
    }

    public NamedQueryArgumentException(Throwable cause) {
        super(cause);
    }

    public NamedQueryArgumentException(String message) {
        super(message);
    }

    public NamedQueryArgumentException(String message, Throwable cause) {
        super(message, cause);
    }

    public static void main(String[] args) {

    }
}
