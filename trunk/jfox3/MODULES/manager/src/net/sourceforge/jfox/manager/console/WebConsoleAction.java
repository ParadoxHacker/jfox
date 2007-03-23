package net.sourceforge.jfox.manager.console;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.naming.Binding;
import javax.naming.NamingEnumeration;

import net.sourceforge.jfox.ejb3.EJBContainer;
import net.sourceforge.jfox.framework.Framework;
import net.sourceforge.jfox.framework.annotation.Service;
import net.sourceforge.jfox.framework.component.Module;
import net.sourceforge.jfox.mvc.ActionSupport;
import net.sourceforge.jfox.mvc.InvocationContext;
import net.sourceforge.jfox.mvc.PageContext;
import net.sourceforge.jfox.mvc.WebContextLoader;
import net.sourceforge.jfox.mvc.annotation.ActionMethod;

/**
 *
 *
 * @author <a href="mailto:yang_y@sysnet.com.cn">Young Yang</a>
 */
@Service(id = "console")
public class WebConsoleAction extends ActionSupport {

    @ActionMethod(successView = "console/console.vhtml")
    public void doGetView(InvocationContext invocationContext) throws Exception {

    }

    @ActionMethod(successView = "persistence.vhtml")
    public void doGetJPAAction(InvocationContext invocationContext) throws Exception{
        //DataSource, NamedNativeQuery, PersistenceUnit
        
    }

    @ActionMethod(successView = "modules.vhtml")
    public void doGetModulesAction(InvocationContext invocationContext) throws Exception{
        Framework framework = WebContextLoader.getManagedFramework();
        Module systemModule = framework.getSystemModule();
        List<Module> allModules = framework.getAllModules();

        List<Module> modules = new ArrayList<Module>(allModules.size()+1);
        modules.add(systemModule);
        modules.addAll(allModules);
        PageContext pageContext = invocationContext.getPageContext();
        pageContext.setAttribute("modules", modules);
    }

    @ActionMethod(successView = "namings.vhtml")
    public void doGetJNDIAction(InvocationContext invocationContext) throws Exception{
        NamingEnumeration<Binding> bindings = getEJBContainer().getNamingContext().listBindings("");
        
    }

    @ActionMethod(successView = "container.vhtml")
    public void doGetEJBContainerAction(InvocationContext invocationContext) throws Exception{
        EJBContainer container = getEJBContainer();
        int defaultTransactionTimeout = container.getTransactionTimeout();

    }

    private EJBContainer getEJBContainer(){
        Framework framework = WebContextLoader.getManagedFramework();
        Collection<EJBContainer> containers = framework.getSystemModule().findComponentByInterface(EJBContainer.class);
        return containers.iterator().next();
    }

    public static void main(String[] args) {

    }
}
