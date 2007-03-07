package net.sourceforge.jfox.entity.cache;

import java.util.Comparator;

/**
 * @author <a href="mailto:yang_y@sysnet.com.cn">Young Yang</a>
 */
public class LRUComparator implements Comparator<CachedObject> {

    public int compare(CachedObject thisCachedObject, CachedObject thatCachedObject) {
        return thisCachedObject.getLastAccessTime() < thatCachedObject.getLastAccessTime() ? -1
                : thisCachedObject.getLastAccessTime() == thatCachedObject.getLastAccessTime() ?
                (thisCachedObject.getCreateTime() < thatCachedObject.getCreateTime() ? -1 : (thisCachedObject.getCreateTime() == thatCachedObject.getCreateTime() ? 0 : 1))
                : 1;
    }

    public static void main(String[] args) {

    }
}
