package org.jfox.mvc;

import org.jfox.framework.BaseRuntimeException;

/**
 * @author <a href="mailto:yang_y@sysnet.com.cn">Young Yang</a>
 */
public class PermissionNotAllowedException extends BaseRuntimeException{

    private String actionName;

    private String username;


    public PermissionNotAllowedException(String actionName, String username) {
        super("ActionName: " + actionName + ", Username: " + username);
        this.actionName = actionName;
        this.username = username;
    }

    public String getActionName() {
        return actionName;
    }

    public String getUsername() {
        return username;
    }

    public static void main(String[] args) {

    }
}
