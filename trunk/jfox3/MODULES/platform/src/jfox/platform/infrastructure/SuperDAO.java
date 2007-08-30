package cn.iservicedesk.infrastructure;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.jfox.entity.QueryExt;
import org.jfox.entity.dao.DAOSupport;

/**
 * //TODO: �0�1�1�7�0�6�1�7�1�7�1�7�1�3�1�7SQLTemplate
 *
 * @author <a href="mailto:jfox.young@gmail.com">Young Yang</a>
 */
public abstract class SuperDAO extends DAOSupport implements DataAccessObject {
    
    /**
     * �0�0�1�7�1�7 JPA EntityManager
     */
    @PersistenceContext(unitName = "iServiceDesk_DS")
    private EntityManager em = null;

    /**
     * �1�7�1�7�1�7�1�7 EntityManager�1�7�1�7�0�8�1�7�1�7�0�0�1�7�1�7�1�7�1�7�1�7 default
     */
    protected EntityManager getEntityManager() {
        return em;
    }

    /**
     * �1�7�1�7�1�7 id �1�7�0�9�1�7 Entity �1�7�1�7�1�7�1�7
     *
     * @param namedQuery  named native sql
     * @param placeHolderName sql template column place holder name
     * @param id id
     * @return entity instance
     */
    public EntityObject getEntityObject(String namedQuery, String placeHolderName, long id) {
        Map<String, Long> paramMap = new HashMap<String, Long>(1);
        paramMap.put(placeHolderName,id);
        List<? extends EntityObject> entities = processNamedNativeQuery(namedQuery,paramMap);
        if(!entities.isEmpty()) {
            return entities.get(0);
        }
        else {
            return null;
        }
    }


    public int executeNamedNativeUpdate(String namedQuery, Map<String, Object> paramMap) {
        Query query = createNamedNativeQuery(namedQuery);
        if (paramMap != null) {
            for (Map.Entry<String, Object> entry : paramMap.entrySet()) {
                query.setParameter(entry.getKey(), entry.getValue());
            }
        }
        return query.executeUpdate();
    }

    /**
     * �0�0�1�7�1�7�0�5�1�7�1�7�0�0�1�7�1�7�1�7�1�7�1�7 query �1�7�1�7�1�7�1�7�1�7�ӄ1�7�0�9�1�7�1�7�1�7�1�7�1�7�1�7 entity list
     *
     * @param namedQuery   named query
     */
    public List<? extends EntityObject> processNamedNativeQuery(String namedQuery, Map<String, ?> paramMap) {
        return processNamedNativeQuery(namedQuery, paramMap, 0, Integer.MAX_VALUE);
    }

    /**
     * �0�0�1�7�1�7�0�5�1�7�1�7�0�0�1�7�1�7�1�7�1�7�1�7 query �1�7�1�7�1�7�1�7�1�7�ӄ1�7�0�9�1�7�1�7�1�7�1�7�1�7�1�7 entity list
     *
     * @param namedQuery   named query
     * @param paramMap parameter map
     * @param firstResult �1�7�1�7�0�5�1�7�1�7�0�5�1�7�1�7�˄1�7�1�7
     * @param maxResult �0�0�0�5�1�7�1�7��
     * @return �1�7�1�7�1�7�1�5�1�7�1�7�1�7�1�7�0�8�1�7�1�7 entity list
     */
    public List<? extends EntityObject> processNamedNativeQuery(String namedQuery, Map<String, ?> paramMap, int firstResult, int maxResult) {
        Query query = createNamedNativeQuery(namedQuery);
        if (paramMap != null) {
            for (Map.Entry<String, ?> entry : paramMap.entrySet()) {
                query.setParameter(entry.getKey(), entry.getValue());
            }
        }
        query.setFirstResult(firstResult);
        query.setMaxResults(maxResult);
        return (List<? extends EntityObject>)query.getResultList();
    }

    public QueryExt createNativeQuery(String sql) {
        throw new UnsupportedOperationException("Can not create native query, only named native query supported!");
    }

    public QueryExt createNativeQuery(String sql, Class<?> resultClass) {
        throw new UnsupportedOperationException("Can not create native query, only named native query supported!");
    }
}
