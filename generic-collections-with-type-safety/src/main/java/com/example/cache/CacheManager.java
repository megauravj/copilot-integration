package com.example.cache;

import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

/**
 * A thread-safe, generic cache manager with TTL (Time-To-Live) support.
 * Uses ConcurrentHashMap for efficient concurrent access without explicit synchronization.
 *
 * <p>Features:</p>
 * <ul>
 *   <li>Type-safe generic caching with bounded types</li>
 *   <li>Automatic expiration of entries after TTL</li>
 *   <li>Lazy initialization with getOrCompute()</li>
 *   <li>Thread-safe operations for concurrent access</li>
 *   <li>Optional return types for safe value retrieval</li>
 *   <li>Automatic cleanup of expired entries</li>
 *   <li>Cache statistics tracking</li>
 * </ul>
 *
 * @param <K> the type of keys in the cache
 * @param <V> the type of values in the cache
 */
public class CacheManager<K, V> {
    
    private final ConcurrentHashMap<K, CacheEntry<V>> cache = new ConcurrentHashMap<>();
    private final CacheStats stats = new CacheStats();
    
    /**
     * Stores a value in the cache with a specified TTL.
     *
     * @param key the cache key
     * @param value the value to cache
     * @param ttlMillis the time-to-live in milliseconds
     */
    public void put(K key, V value, long ttlMillis) {
        if (ttlMillis < 0) {
            throw new IllegalArgumentException("TTL cannot be negative: " + ttlMillis);
        }
        var entry = new CacheEntry<>(value, ttlMillis);
        cache.put(key, entry);
    }
    
    /**
     * Retrieves a value from the cache if it exists and has not expired.
     *
     * @param key the cache key
     * @return an Optional containing the value if found and not expired
     */
    public Optional<V> get(K key) {
        var entry = cache.get(key);
        
        if (entry == null) {
            stats.recordMiss();
            return Optional.empty();
        }
        
        if (entry.isExpired()) {
            cache.remove(key);
            stats.recordEviction();
            stats.recordMiss();
            return Optional.empty();
        }
        
        stats.recordHit();
        return Optional.of(entry.getValue());
    }
    
    /**
     * Retrieves a value from the cache, computing and storing it if not present or expired.
     *
     * @param key the cache key
     * @param valueProvider a function that computes the value if needed
     * @param ttlMillis the TTL for the computed value in milliseconds
     * @return an Optional containing the cached or computed value
     */
    public Optional<V> getOrCompute(K key, Function<K, V> valueProvider, long ttlMillis) {
        if (ttlMillis < 0) {
            throw new IllegalArgumentException("TTL cannot be negative: " + ttlMillis);
        }
        
        var cachedValue = get(key);
        if (cachedValue.isPresent()) {
            return cachedValue;
        }
        
        try {
            var computedValue = valueProvider.apply(key);
            put(key, computedValue, ttlMillis);
            stats.recordHit();
            return Optional.of(computedValue);
        } catch (Exception e) {
            stats.recordMiss();
            throw new RuntimeException("Failed to compute value for key: " + key, e);
        }
    }
    
    /**
     * Removes all expired entries from the cache.
     *
     * @return the number of entries removed
     */
    public int cleanup() {
        var removedCount = 0;
        
        for (var entry : cache.entrySet()) {
            if (entry.getValue().isExpired()) {
                if (cache.remove(entry.getKey(), entry.getValue())) {
                    stats.recordEviction();
                    removedCount++;
                }
            }
        }
        
        return removedCount;
    }
    
    /**
     * Checks if a key exists in the cache and has not expired.
     *
     * @param key the cache key
     * @return true if the key exists and is not expired
     */
    public boolean containsKey(K key) {
        var entry = cache.get(key);
        if (entry == null) {
            return false;
        }
        
        if (entry.isExpired()) {
            cache.remove(key);
            stats.recordEviction();
            return false;
        }
        
        return true;
    }
    
    /**
     * Gets the current number of entries in the cache.
     *
     * @return the number of entries in the cache
     */
    public int size() {
        return cache.size();
    }
    
    /**
     * Gets the number of non-expired entries in the cache.
     *
     * @return the count of valid entries
     */
    public int sizeNonExpired() {
        return (int) cache.values().stream()
                .filter(entry -> !entry.isExpired())
                .count();
    }
    
    /**
     * Removes all entries from the cache.
     */
    public void clear() {
        cache.clear();
        stats.recordEviction();
    }
    
    /**
     * Gets statistics about cache operations.
     *
     * @return the cache statistics
     */
    public CacheStats getStats() {
        return stats;
    }
    
    /**
     * Gets a string representation of cache statistics.
     *
     * @return statistics as string
     */
    public String getStatsString() {
        return stats.toString();
    }
}
