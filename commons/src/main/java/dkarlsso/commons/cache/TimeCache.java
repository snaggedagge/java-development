package dkarlsso.commons.cache;

import org.joda.time.DateTime;

import java.util.*;

public class TimeCache {

    private final static int DEFAULT_TIME_MINUTES = 15;

    private final Map<String,Object> cache = new HashMap<>();
    private final Map<String,DateTime> timeCache = new HashMap<>();


    public void put(final String key, final Object value) {
        put(key, value, DEFAULT_TIME_MINUTES);
    }

    public void put(final String key, final Object value, final int minutesUntilExpiring) {
        cache.put(key,value);

        final DateTime expiringTime = new DateTime().plusMinutes(minutesUntilExpiring);
        timeCache.put(key,expiringTime);
    }

    public <T> T get( final String key) {
        return (T)cache.get(key);

/*
        final Object object = cache.get(key);

        if(classType.isInstance(object)) {

        }

        throw new TimeCacheException("Wrong class type");
 */
    }

    public boolean isValid(final String key) {
        if(cache.containsKey(key)) {
            final Date now = new Date();
            final DateTime expiringTime = timeCache.get(key);
            return expiringTime.toDate().getTime() > now.getTime();
        }
        return false;
    }




}
