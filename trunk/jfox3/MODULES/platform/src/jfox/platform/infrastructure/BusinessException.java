package cn.iservicedesk.infrastructure;

import javax.ejb.EJBException;

/**
 * BO �1�7�0�7�1�7�1�7�1�7�4�4
 *
 * @author <a href="mailto:jfox.young@gmail.com">Young Yang</a>
 */
public class BusinessException extends EJBException {

    public BusinessException(String msg) {
        super(msg);
    }

    public BusinessException(Exception e) {
        super(e);
    }

    public BusinessException(String msg, Exception e) {
        super(msg, e);
    }

}
