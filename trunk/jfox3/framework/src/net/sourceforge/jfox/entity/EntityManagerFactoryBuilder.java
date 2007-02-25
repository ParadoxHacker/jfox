package net.sourceforge.jfox.entity;

import javax.persistence.NamedNativeQuery;

import net.sourceforge.jfox.entity.NamedSQLTemplate;
import org.w3c.dom.Document;

/**
 * @author <a href="mailto:jfox.young@gmail.com">Young Yang</a>
 */
public interface EntityManagerFactoryBuilder {

    void registerNamedQuery(NamedNativeQuery namedNativeQuery, Class<?> definedClass);

    NamedSQLTemplate getNamedQuery(String name);

    Document getPersistenceXMLDocument();
}
