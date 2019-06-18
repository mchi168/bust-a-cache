/**
 * Keeps track of keys to evict
 * @author Michael CHi
 */
interface EvictionPolicy {

    /**
     * will keep track of keys in specified ordering.
     * @param key key to track
     */
    void track(Object key);

    /**
     * Will return the correct key to discard from cache
     * @return key to evict
     */
    Object getNextKeyToEvict();

}