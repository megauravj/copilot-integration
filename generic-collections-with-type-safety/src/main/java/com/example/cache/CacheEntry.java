package com.example.cache;

/**
 * Internal class representing a single cache entry with TTL support.
 * Stores both the cached value and its expiration timestamp.
 *
 * @param <V> the type of value stored in the cache
 */
public class CacheEntry<V> {
    
    private final V value;
    private final long expirationTime;
    
    /**
     * Creates a new cache entry with a TTL.
     *
     * @param value the value to cache
     * @param ttlMillis the time-to-live in milliseconds
     */
    public CacheEntry(V value, long ttlMillis) {
        this.value = value;
        this.expirationTime = System.currentTimeMillis() + ttlMillis;
    }
    
    /**
     * Gets the cached value.
     *
     * @return the cached value
     */
    public V getValue() {
        return value;
    }
    
    /**
     * Checks if this entry has expired based on current system time.
     *
     * @return true if the entry has expired, false otherwise
     */
    public boolean isExpired() {
        return System.currentTimeMillis() > expirationTime;
    }
    
    /**
     * Gets the expiration time as a timestamp.
     *
     * @return the expiration time in milliseconds since epoch
     */
    public long getExpirationTime() {
        return expirationTime;
    }
    
    /**
     * Gets the remaining TTL for this entry.
     *
     * @return the remaining time in milliseconds, or 0 if expired
     */
    public long getRemainingTtl() {
        long remaining = expirationTime - System.currentTimeMillis();
        return Math.max(0, remaining);
    }
}
