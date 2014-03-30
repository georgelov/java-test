/**
 * 
 */
package se.tain.exercise.cachemap.impl;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import se.tain.exercise.cachemap.CacheMap;
import se.tain.exercise.cachemap.Clock;

/**
 * @author George (georgiy.lovyagin@gmail.com)
 * @author Last changed by: George
 * @version Mar 23, 2014
 */
public final class CacheMapImpl<KeyType, ValueType> implements
        CacheMap<KeyType, ValueType> {

    private static final String WRONG_KEY = "key may not be null";
    private final Log logger = LogFactory.getLog(getClass());
    private final Map<KeyTypeWrapper<KeyType>, ValueType> map = new ConcurrentHashMap<KeyTypeWrapper<KeyType>, ValueType>();
    private Long timeToLive = Long.valueOf(0);

    /*
     * (non-Javadoc)
     * 
     * @see se.tain.exercise.cachemap.CacheMap#setTimeToLive(long)
     */
    @Override
    public void setTimeToLive(long timeToLive) {
        logger.trace("CacheMapImpl.setTimeToLive.start: " + timeToLive);
        this.timeToLive = Long.valueOf(timeToLive);
    }

    /*
     * (non-Javadoc)
     * 
     * @see se.tain.exercise.cachemap.CacheMap#getTimeToLive()
     */
    @Override
    public long getTimeToLive() {
        logger.trace("CacheMapImpl.getTimeToLive.start: " + timeToLive);
        return this.timeToLive;
    }

    /*
     * (non-Javadoc)
     * 
     * @see se.tain.exercise.cachemap.CacheMap#put(java.lang.Object,
     * java.lang.Object)
     */
    @Override
    public ValueType put(KeyType key, ValueType value) {
        logger.trace("CacheMapImpl.put.start: " + key + ", " + value);
        if (key == null) {
            logger.error("CacheMapImpl.getTimeToLive: key is null");
            throw new IllegalArgumentException(WRONG_KEY);
        } else if (value == null) {
            return map.remove(key);
        } else {
            return map.put(new KeyTypeWrapper<KeyType>(key), value);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see se.tain.exercise.cachemap.CacheMap#clearExpired()
     */
    @Override
    public void clearExpired() {
        for (final KeyTypeWrapper<KeyType> key : map.keySet()) {
            if (isExpired(key)) {
                map.remove(key);
            }
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see se.tain.exercise.cachemap.CacheMap#clear()
     */
    @Override
    public void clear() {
        map.clear();
    }

    /*
     * (non-Javadoc)
     * 
     * @see se.tain.exercise.cachemap.CacheMap#containsKey(java.lang.Object)
     */
    @Override
    public boolean containsKey(KeyType key) {
        clearExpired();
        return map.containsKey(new KeyTypeWrapper<KeyType>(key));
    }

    /*
     * (non-Javadoc)
     * 
     * @see se.tain.exercise.cachemap.CacheMap#containsValue(java.lang.Object)
     */
    @Override
    public boolean containsValue(Object value) {
        clearExpired();
        for (final ValueType val : map.values()) {
            if (val.equals(value)) {
                return true;
            }
        }
        return false;
    }

    /*
     * (non-Javadoc)
     * 
     * @see se.tain.exercise.cachemap.CacheMap#get(java.lang.Object)
     */
    @Override
    public ValueType get(KeyType key) {
        clearExpired();
        return map.get(new KeyTypeWrapper<KeyType>(key));
    }

    /*
     * (non-Javadoc)
     * 
     * @see se.tain.exercise.cachemap.CacheMap#isEmpty()
     */
    @Override
    public boolean isEmpty() {
        clearExpired();
        return map.isEmpty();
    }

    /*
     * (non-Javadoc)
     * 
     * @see se.tain.exercise.cachemap.CacheMap#remove(java.lang.Object)
     */
    @Override
    public ValueType remove(KeyType key) {
        clearExpired();
        return map.remove(new KeyTypeWrapper<KeyType>(key));
    }

    /*
     * (non-Javadoc)
     * 
     * @see se.tain.exercise.cachemap.CacheMap#size()
     */
    @Override
    public int size() {
        clearExpired();
        return map.size();
    }

    /**
     * @param key
     * @return
     */
    private boolean isExpired(KeyTypeWrapper<KeyType> key) {
        return (key.getCreated() + timeToLive) < Clock.getTime();
    }

    /**
     */
    private static final class KeyTypeWrapper<KeyType> {

        private final KeyType keyType;
        private final long created = Clock.getTime();

        /**
         * @param keyType
         */
        public KeyTypeWrapper(KeyType keyType) {
            super();
            this.keyType = keyType;
        }

        /**
         * @return the created
         */
        public long getCreated() {
            return created;
        }

        /*
         * (non-Javadoc)
         * 
         * @see java.lang.Object#hashCode()
         */
        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result
                    + ((keyType == null) ? 0 : keyType.hashCode());
            return result;
        }

        /*
         * (non-Javadoc)
         * 
         * @see java.lang.Object#equals(java.lang.Object)
         */
        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            if (obj == null)
                return false;
            if (getClass() != obj.getClass())
                return false;
            KeyTypeWrapper<?> other = (KeyTypeWrapper<?>) obj;
            if (keyType == null) {
                if (other.keyType != null)
                    return false;
            } else if (!keyType.equals(other.keyType))
                return false;
            return true;
        }

        /*
         * (non-Javadoc)
         * 
         * @see java.lang.Object#toString()
         */
        @Override
        public String toString() {
            return "KeyTypeWrapper [keyType=" + keyType + ", created="
                    + created + "]";
        }

    }

}
