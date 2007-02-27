package net.sourceforge.jfox.framework.event;

import net.sourceforge.jfox.framework.ComponentId;

/**
 * @author <a href="mailto:jfox.young@gmail.com">Young Yang</a>
 */
public class ComponentUnloadedEvent extends ComponentEvent{

    public ComponentUnloadedEvent(ComponentId id) {
        super(id);
    }
}