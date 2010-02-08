package code.google.webactioncontainer;

import code.google.jcontainer.AbstractContainer;
import code.google.jcontainer.annotation.Container;
import code.google.jcontainer.invoke.AnnotationResolverInvocationHandler;
import code.google.jcontainer.invoke.MethodInvocationHandler;
import org.apache.log4j.Logger;
import code.google.webactioncontainer.annotation.ActionMethod;
import code.google.webactioncontainer.annotation.WebAction;
import code.google.webactioncontainer.invocation.CheckSessionActionInvocationHandler;
import code.google.webactioncontainer.invocation.ParseParameterActionInvocationHandler;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * 管理 WebAction
 *
 * @author <a href="mailto:jfox.young@gmail.com">Young Yang</a>
 * @create May 22, 2008 11:15:34 AM
 */
@Container(
        name = "WebActionContainer",
        componentType= WebAction.class,
        supportAnnotations = {ActionMethod.class},
        invocationHandlers = {
                ParseParameterActionInvocationHandler.class, 
                CheckSessionActionInvocationHandler.class,
                AnnotationResolverInvocationHandler.class,
                MethodInvocationHandler.class}
)
public class WebActionContainer extends AbstractContainer {

    protected final Logger logger = Logger.getLogger(this.getClass());

    public static final String POST_METHOD_PREFIX = "POST_";
    public static final String GET_METHOD_PREFIX = "GET_";

   // action name lowercase= > Method
    public final Map<String, Method> actionMap = new HashMap<String, Method>();

    // Action.execute
    private static Method EXECUTE_METHOD = null;

    public WebActionContainer() {
        try {
            EXECUTE_METHOD =  Action.class.getMethod("execute", ActionContext.class);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void addActionMethod(Method actionMethod, ActionMethod actionMethodAnnotation){
        //TODO: Key GET_%ACTION_NAME%_%ACTION_METHOD%
        if (actionMethod.getReturnType().equals(void.class)
                && actionMethod.getParameterTypes().length == 1
                && actionMethod.getParameterTypes()[0].equals(ActionContext.class)) {
            // 统一转换成大写
            String actionMethodName = actionMethodAnnotation.name().trim(); // ActionMethod 指定的名称
            if (actionMethodName.length() == 0) { // 判断是否为空
                logger.error("ActionMethod name can not be empty. WebAction: " + this.getClass().getName() + "." + actionMethod.getName());
                return;
            }

            if (actionMethodAnnotation.httpMethod().equals(ActionMethod.HttpMethod.GET)) {
                String key = (GET_METHOD_PREFIX + actionMethodName).toUpperCase();
                //判断 action key 是否已经存在，如果存在，日志并忽略
                if (actionMap.containsKey(key)) {
                    logger.warn("ActionMethod with name " + actionMethodName + " in WebAction's Method " + this.getClass().getName() + "." + actionMethod.getName() + " has been registed, ignored.");
                }
                else {
                    actionMap.put(key, actionMethod);
                }
            }
            else if (actionMethodAnnotation.httpMethod().equals(ActionMethod.HttpMethod.POST)) {
                String key = (POST_METHOD_PREFIX + actionMethodName).toUpperCase();
                if (actionMap.containsKey(key)) {
                    logger.warn("ActionMethod with name " + actionMethodName + " in WebAction's Method " + this.getClass().getName() + "." + actionMethod.getName() + " has been registed, ignored.");
                }
                else {
                    actionMap.put(key, actionMethod);
                }
            }
            else {
                String key1 = (GET_METHOD_PREFIX + actionMethodName).toUpperCase();
                String key2 = (POST_METHOD_PREFIX + actionMethodName).toUpperCase();

                if (actionMap.containsKey(key1) || actionMap.containsKey(key2)) {
                    logger.warn("ActionMethod with name " + actionMethodName + " in WebAction's Method " + this.getClass().getName() + "." + actionMethod.getName() + " has been registed, ignored.");
                }
                else {
                    actionMap.put((GET_METHOD_PREFIX + actionMethodName).toUpperCase(), actionMethod);
                    actionMap.put((POST_METHOD_PREFIX + actionMethodName).toUpperCase(), actionMethod);
                }
            }
        }
        else {
            logger.warn("ActionMethod ignored, " + actionMethod);
        }
    }

    public Method getActionMethod(ActionContext actionContext) {
        //决定调用 doGetXXX or doPostXXX
        Method actionMethod;
        String name = actionContext.getActionMethodName();
        if (actionContext.isPost()) {
            actionMethod = actionMap.get((POST_METHOD_PREFIX + name).toUpperCase());
        }
        else {
            actionMethod = actionMap.get((GET_METHOD_PREFIX + name).toUpperCase());
        }
        return actionMethod;
    }


    public PageContext invokeAction(ActionContext actionContext) throws Throwable {
        logger.info("Request accepted, URI: " + actionContext.getRequestURI());
        long now = System.currentTimeMillis();
        String actionName = actionContext.getActionName();
        if(!hasComponent(actionName)) {
            throw new ActionNotFoundException(actionName);
        }
        Method method = getActionMethod(actionContext);
        actionContext.setActionMethod(method);
        PageContext pageContext = null;
        try {
            // invoke Action.execute method
            invokeComponent(actionName, EXECUTE_METHOD, actionContext);
            pageContext = actionContext.getPageContext();
        }
        finally {
            if(pageContext != null && (pageContext.hasBusinessException() || pageContext.hasValidateException())) {
                Object action = getComponent(actionName);
                if(action instanceof ActionSupport) {
                    ((ActionSupport)action).doActionFailed(actionContext);
                }
            }
            logger.info("Request done, URI: " + actionContext.getRequestURI() + ", consumed " + (System.currentTimeMillis() - now) + "ms.");
        }
        return pageContext;
    }

}
