package org.jfox.ejb3.security;

import java.util.List;
import java.util.ArrayList;
import javax.security.auth.callback.Callback;

/**
 * @author <a href="mailto:yang_y@sysnet.com.cn">Young Yang</a>
 */
public class JAASLoginResponseCallback implements Callback {

    // 一般等于用户名或者 id
    private String principalName;

    private List<String> roles = new ArrayList<String>();

    public String getPrincipalName() {
        return principalName;
    }

    public void setPrincipalName(String principalName) {
        this.principalName = principalName;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void addRole(String role){
        roles.add(role);
    }

    public void removeRole(String role){
        roles.remove(role);
    }

    public static void main(String[] args) {

    }
}
