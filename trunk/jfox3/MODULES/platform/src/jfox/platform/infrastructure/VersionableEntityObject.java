package cn.iservicedesk.infrastructure;

import javax.persistence.Column;

/**
 * �0�1�1�7�0�0�ڄ1�7�1�7�0�4�1�7�1�7�1�7�0�9�1�7�1�7�1�7�1�7 EntityObject
 *
 * @author <a href="mailto:yang_y@sysnet.com.cn">Young Yang</a>
 */
public abstract class VersionableEntityObject extends EntityObject{

    @Column(name="VERSION")
    private int version;

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public void increaseVersion(){
        this.version++;
    }

}