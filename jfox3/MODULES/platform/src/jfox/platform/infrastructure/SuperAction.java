package cn.iservicedesk.infrastructure;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;

import cn.iservicedesk.common.JSONUtils;
import cn.iservicedesk.function.bo.ModuleBO;
import cn.iservicedesk.function.bo.NodeBO;
import cn.iservicedesk.function.entity.Module;
import cn.iservicedesk.function.entity.Node;
import org.jfox.mvc.ActionSupport;
import org.jfox.mvc.InvocationContext;
import org.jfox.mvc.PageContext;
import org.jfox.mvc.SessionContext;

/**
 * �1�7�1�7�1�7�1�7�0�4�1�7�1�7�0�4�1�7�1�7�0�7�1�7�1�7�1�7�؄1�7
 * <p/>
 * �1�7�1�7�1�7�1�7 Action �1�7�1�7�1�7�1�7�1�7�1�7�0�7�1�7
 * <p/>
 * //TODO: �0�1�1�7�0�6�1�7�1�7�1�7�1�7�1�7 successView
 * //TODO: �0�1�1�7�1�7 themes �0�5�1�7�1�7
 * //TODO: �1�7�1�7�1�7�1�7�0�0�1�7�0�9�1�7
 * //TODO: �1�7�1�7�1�7�1�7�0�8�1�7�1�7�1�7�0�4
 *
 * @author <a href="mailto:jfox.young@gmail.com">Young Yang</a>
 */
public abstract class SuperAction extends ActionSupport {
    public static final String THEME_KEY = "theme";
    public static final String LANG_KEY = "lang";

    @EJB
    NodeBO nodeBO;
    @EJB
    ModuleBO moduleBO;

    private Module currentModule;
    private Node currentNode;


    protected void preAction(InvocationContext invocationContext) {
        super.preAction(invocationContext);
        
        PageContext pageContext = invocationContext.getPageContext();
        // set common attribute
        pageContext.setAttribute("__JSONUTILS__", JSONUtils.getInstance());

        // init currentModule currentNode, �1�7�1�7�1�7 node.BindAction �1�7�0�1�1�7 node
        String actionMethodName = invocationContext.getFullActionMethodName();
        //TODO: uncomment
//        currentNode = nodeBO.getNodeByBindAction(actionMethodName);
//        currentModule = moduleBO.getModuleById(currentNode.getModuleId());

        List<Module> allModules = moduleBO.getAllModules();
        pageContext.setAttribute("__ALL_MODULES__", allModules);
    }

    protected void postAction(InvocationContext invocationContext) {
        SessionContext sessionContext = invocationContext.getSessionContext();
        PageContext pageContext = invocationContext.getPageContext();
        // TODO: set default session

        // �1�7�1�7�1�7�1�7�1�7�1�7�1�7�1�7
        String theme = (String)sessionContext.getAttribute(THEME_KEY);
        if(theme == null) {
            sessionContext.setAttribute(THEME_KEY, "VintageSugar");
        }
        sessionContext.setAttribute(LANG_KEY,"en_US");
        // �1�7�1�7�1�7�0�2�1�7�1�7�1�7�1�7�1�7
        String lang = (String)sessionContext.getAttribute(LANG_KEY);

        if (lang != null) {
            pageContext.setTargetView(lang + "/" + pageContext.getTargeView());
        }



        if(pageContext.hasBusinessException() || invocationContext.getPageContext().hasValidateException()) {
            // log action failed 
        }
        else {
            // log action successful
        }

        //TODO: uncomment
//        buildContextNodes(invocationContext);

    }

    /**
     * �1�7�1�7�1�7�1�7�1�7�1�7�1�7�1�7�0�3�1�7�1�7�0�7�1�7�1�7�1�7�0�3�0�2�1�7�0�2�1�7�1�7�1�7�0�4�1�7�1�7�1�7�1�8�0�5�1�7
     * @param invocationContext
     */
    private void buildContextNodes(InvocationContext invocationContext){
        PageContext pageContext = invocationContext.getPageContext();

        Map<String, List<Node>> nodeGroups = new HashMap<String, List<Node>>();
        List<Node> menuNodes = new ArrayList<Node>();

        // menuNodes in current Module
        List<Node> moduleMenuNodes = nodeBO.getMenuNodesByModuleId(currentModule.getId());
        menuNodes.addAll(moduleMenuNodes);

        // child nodes in current node
        List<Node> childrenNodes = nodeBO.getChildrenNodes(currentNode.getId());
        for(Node node : childrenNodes){
            if(node.isMenu()) { // �1�7�1�7 button node �1�7�1�7�0�6�1�7�1�7�1�7�1�7�0�0�1�7�1�7 menu node
                menuNodes.add(node);
            }
            else {
                String nodeGroup = node.getNodeGroup();
                List<Node> nodes = nodeGroups.get(nodeGroup);
                if(nodes == null) {
                    nodes = new ArrayList<Node>();
                    nodeGroups.put(nodeGroup, nodes);
                }
                nodes.add(node);
            }
        }

        pageContext.setAttribute("_MENU_NODES_", menuNodes);
        // get buttonNodes
        pageContext.setAttribute("_BUTTON_NODE_GROUPS_", nodeGroups);
    }

    /**
     * �1�7�1�7�1�7�0�1�0�5�1�1�1�7
     */
    protected Node getCurrentNode(){
        return currentNode;
    }

    /**
     * �1�7�1�7�1�7�1�7�1�7�1�7�1�7�1�1�1�7�0�0�1�7�1�7
     */
    protected Module getCurrentModule() {
        return currentModule;
    }

    public static void main(String[] args) {

    }
}
