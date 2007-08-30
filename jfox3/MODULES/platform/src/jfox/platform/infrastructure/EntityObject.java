package cn.iservicedesk.infrastructure;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import org.jfox.entity.dao.PKGenerator;
import org.json.JSONObject;

/**
 * @author <a href="mailto:jfox.young@gmail.com">Young Yang</a>
 */
public abstract class EntityObject implements Comparable<EntityObject>, Serializable {
    /**
     * �0�7�1�7�1�7�1�7�1�7�1�7�1�7�1�7�1�7ID�1�7�0�6���1�7�1�7�1�7�1�7�1�7�0�2 Primary Key
     */
    @Column(name = "ID")
    private long id = -1;

    @Column(name = "NAME")
    private String name;

    // �1�7�0�4�1�7�1�7�1�7 CODE
    @Column(name = "CREATOR")
    private String creator;

    @Column(name = "CREATE_TIME")
    private long createTime = -1;

    @Column(name = "LAST_MODIFIER")
    private String lastModifier;

    @Column(name = "LAST_MODIFIED")
    private long lastModified = -1;

    @Column(name = "PRIORITY")
    private int priority = 0;


    /**
     * VALID STATUS, �1�7�1�7�1�7�1�7�1�7���0�8�0�0
     * 0 �1�7�1�7
     * 1 DISABLED
     * 2 REMOVED
     */
    @Column(name = "VSTATUS")
    protected int vstatus;


    @Column(name = "DESCRIPTION")
    protected String description;

    public EntityObject() {
        // �0�0�1�7�1�7PKgen�1�7�1�7�1�7id
        id = PKGenerator.getInstance(0).nextPK();
        createTime = System.currentTimeMillis();
    }

    /**
     * �1�7�1�7�1�7 Id�1�7�1�7like: 2006121916471910560
     */
    @Id
    @Column(name = "ID")
    public long getId() {
        return id;
    }

    protected void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getVstatus() {
        return vstatus;
    }

    public void setVstatus(int vstatus) {
        this.vstatus = vstatus;
    }

    public boolean isDisabled() {
        return getVstatus() == 1;
    }

    public boolean isRemoved() {
        return getVstatus() == 2;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public long getLastModified() {
        if (lastModified == -1) {
            lastModified = createTime;
        }
        return lastModified;
    }

    public void setLastModified(long lastModified) {
        this.lastModified = lastModified;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getLastModifier() {
        if (lastModifier == null) {
            lastModifier = creator;
        }
        return lastModifier;
    }

    public void setLastModifier(String lastModifier) {
        this.lastModifier = lastModifier;
    }

    /**
     * �1�7�1�74�1�7�1�7�1�7�1�7
     *
     * @param thatEntity entity to be compared
     */
    public int compareTo(EntityObject thatEntity) {
        int comparePriority = new Integer(this.getPriority()).compareTo(thatEntity.getPriority());
        if (comparePriority == 0) {
            return new Long(this.getId()).compareTo(thatEntity.getId());
        }
        else {
            return comparePriority;
        }
    }

    /**
     * �1�7�1�7�1�7 @Entity �1�7�0�1�1�7 Table Name
     */
    public String getTableName() {
        Entity entity = this.getClass().getAnnotation(Entity.class);
        String tableName = entity.name();
        if (tableName == null || tableName.trim().length() == 0) {
            return "UNKNOWN";
        }
        else {
            return tableName;
        }
    }

    /**
     * �0�8�1�7�1�7 Map�1�7�1�7�1�7�0�3�1�2�1�7�1�7�1�7�1�7�1�7�1�7 JSON �1�7�1�7�1�7�1�7
     */
    public Map<String, Object> convertToMap() {
        Map<String, Object> valueMap = new HashMap<String, Object>();
        Field[] fields = getAllColumnFields(this.getClass());
        for (Field field : fields) {
            try {
                field.setAccessible(true);
                String columnName = field.getAnnotation(Column.class).name();
                valueMap.put(columnName, field.get(this));
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        return valueMap;
    }

    public String toJSONString(){
        /*Map<String, Object> columnMap = convertToMap();
        StringBuffer sb = new StringBuffer("{");
        int i=0;
        for(Map.Entry<String, Object> entry : columnMap.entrySet()){
            if(i>0) {
                sb.append(",");
            }
            String key = entry.getKey();
            sb.append("\"").append(key).append("\":");
            Object value = entry.getValue();
            if(value instanceof Number) {
                sb.append(value);
            }
            else {
                sb.append("\"").append(value).append("\"");
            }
            i++;
        }
        sb.append("}");
        return sb.toString();*/

        return new JSONObject(convertToMap()).toString();
    }

    /**
     * �0�0�1�7�1�7clz �1�7�1�7�1�7�ք1�7 Field
     *
     * @param clazz class
     */
    protected static Field[] getAllColumnFields(Class clazz) {
        List<Field> columnFields = new ArrayList<Field>();
        Class[] superClasses = getAllSuperclasses(clazz);
        for (Class superClass : superClasses) {
            for (Field field : superClass.getDeclaredFields()) {
                if (field.isAnnotationPresent(Column.class)) {
                    columnFields.add(field);
                }
            }
        }
        return columnFields.toArray(new Field[columnFields.size()]);
    }

    protected static Class[] getAllSuperclasses(Class cls) {
        if (cls == null) {
            return new Class[0];
        }
        List<Class> classList = new ArrayList<Class>();
        classList.add(cls);
        Class superClass = cls.getSuperclass();
        while (superClass != null && !superClass.equals(Object.class)) { // java.lang.Object �1�7�1�7�1�7�1�7�0�2�1�7�1�7�1�7�1�7
            classList.add(superClass);
            superClass = superClass.getSuperclass();
        }
        Collections.reverse(classList); // reverse�1�7�1�7�1�7�0�3�1�7�0�8�1�7�1�7�1�7�2�8�1�7�0�5�1�7�1�7�1�7
        return classList.toArray(new Class[classList.size()]);
    }
}
