package cn.iservicedesk.infrastructure;

import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

/**
 * @author <a href="mailto:jfox.young@gmail.com">Young Yang</a>
 */

public abstract class SuperBO implements BusinessObject{

    @Resource
    private SessionContext sessionContext;

    protected SessionContext getSessionContext(){
        return sessionContext;
    }

    /**
     * �1�7�1�7�1�7�1�7EntityObject�1�7�0�9�1�7�1�7�1�7�1�7�1�7
     *
     * @param entityObject entity
     */
    protected boolean isEntityReferenced(RefInspectableEntityObject entityObject){
        return entityObject.isReferenced();
    }

    /**
     * �1�7�1�7�1�7 Version �1�7�0�9�8�4�1�7�1�7�1�7
     * �0�0�1�7�0�1�1�7 namedQuery �1�7�1�7�1�7�1�7�0�2�0�0�1�7�1�7 ID �1�7�1�7�1�7 Entity �1�7�1�7 query
     * �1�7�1�7�0�0�1�7�1�7�1�7�1�7�1�7�1�7�1�7�0�3�1�7�1�7�8�7�0�2�1�7�1�7�1�7�1�7�1�7�1�9�1�7�1�7�1�7�0�4�1�7�1�7�1�6�1�7�1�7�0�8�1�7�1�7�1�7�1�7�1�7
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public boolean isVersionValid(VersionableEntityObject entity, String namedQuery) {
        VersionableEntityObject storedEntity = (VersionableEntityObject)getDataAccessObject().getEntityObject(namedQuery,"ID",entity.getId());
        return storedEntity.getVersion() < entity.getVersion();
    }

    public abstract DataAccessObject getDataAccessObject();
}
