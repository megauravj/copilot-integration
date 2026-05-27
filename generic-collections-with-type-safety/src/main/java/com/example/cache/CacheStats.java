package com.example.cache;

import java.util.concurrent.atomic.AtomicLong;

/**
 * Statistics tracker for cache operations.
 * Tracks hits, misses, and evictions.
 */
public class CacheStats {
    
    private final AtomicLong hits = new AtomicLong(0);
    private final AtomicLong misses = new AtomicLong(0);
    private final AtomicLong evictions = new AtomicLong(0);
    
    /**
     * Records a cache hit.
     */
    public void recordHit() {
        hits.incrementAndGet();
    }
    
    /**
     * Records a cache miss.
     */
    public void recordMiss() {
        misses.incrementAndGet();
    }
    
    /**
     * Records an eviction.
     */
    public void recordEviction() {
        evictions.incrementAndGet();
    }
    
    /**
     * Gets the total number of cache hits.
     *
     * @return the number of hits
     */
    public long getHits() {
        return hits.get();
    }
    
    /**
     * Gets the total number of cache misses.
     *
     * @return the number of misses
     */
    public long getMisses() {
        return misses.get();
    }
    
    /**
     * Gets the total number of evictions.
     *
     * @return the number of evictions
     */
    public long getEvictions() {
        return evictions.get();
    }
    
    /**
     * Gets the total number of cache operations.
     *
     * @return total hits + misses
     */
    public long getTotalOperations() {
        return hits.get() + misses.get();
    }
    
    /**
     * Calculates the cache hit ratio.
     *
     * @return the hit ratio (0.0 to 1.0), or 0.0 if no operations
     */
    public double getHitRatio() {
        var total = getTotalOperations();
        return total == 0 ? 0.0 : (double) hits.get() / total;
    }
    
    /**
     * Calculates the hit rate as a percentage.
     *
     * @return the hit rate (0 to 100), or 0 if no operations
     */
    public double getHitRatePercentage() {
        return getHitRatio() * 100;
    }
    
    /**
     * Resets all statistics.
     */
    public void reset() {
        hits.set(0);
        misses.set(0);
        evictions.set(0);
    }
    
    @Override
    public String toString() {
        return "CacheStats{" +
                "hits=" + hits.get() +
                ", misses=" + misses.get() +
                ", evictions=" + evictions.get() +
                ", hitRatio=" + String.format("%.2f", getHitRatio()) +
                '}';
    }
}
