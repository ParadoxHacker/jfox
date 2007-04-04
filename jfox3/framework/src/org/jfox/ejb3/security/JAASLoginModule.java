package org.jfox.ejb3.security;

import java.util.Map;
import java.util.List;
import javax.security.auth.spi.LoginModule;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.Callback;
import javax.security.auth.login.LoginException;
import javax.security.auth.Subject;

import org.apache.log4j.Logger;

/**
 * @author <a href="mailto:yang_y@sysnet.com.cn">Young Yang</a>
 */
public class JAASLoginModule implements LoginModule {

    static Logger logger = Logger.getLogger(JAASLoginModule.class);

    private CallbackHandler callbackHandler;

    public boolean abort() throws LoginException {
        return false;
    }

    public boolean commit() throws LoginException {
        return true;
    }

    public void initialize(Subject subject, CallbackHandler callbackHandler, Map<String, ?> sharedState, Map<String, ?> options) {
        logger.debug("initialize, callbackHandler: " + callbackHandler);
        this.callbackHandler = callbackHandler;
    }

    public boolean login() throws LoginException {
        JAASLoginRequestCallback loginRequestCallback = JAASLoginService.loginRequestThreadLocal.get();
        try {
            JAASLoginResultCallback loginResultCallback =  new JAASLoginResultCallback();
            callbackHandler.handle(new Callback[]{loginRequestCallback, loginResultCallback});

            // 处理 loginResultCallback，构造 Subject, 设置 SecurityContext
            String principalName = loginResultCallback.getPrincipalId();
            List<String> roles = loginResultCallback.getRoles();
            Subject subject = SecurityContext.buildSubject(principalName, roles);
            SecurityContext securityContext = new SecurityContext(subject);
            //TODO: 构造 initialize 中 的 subject
        }
        catch(Exception e) {
            e.printStackTrace();
        }

        return true;
    }

    public boolean logout() throws LoginException {
        return false;
    }

    public static void main(String[] args) {

    }
}