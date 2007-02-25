package net.sourceforge.jfox.framework.component;

import net.sourceforge.jfox.framework.event.ModuleEvent;

/**
 * @author <a href="mailto:jfox.young@gmail.com">Young Yang</a>
 */
public interface ModuleListener {

    void moduleChanged(ModuleEvent moduleEvent);

}
