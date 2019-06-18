
import java.util.HashMap;
/**
 * This object represents each set, or block in a set-associative cache.
 * Each block contains n-lines of storage locations.
 * Block defers to EvictionPolicy to manage key-movement and correct key to discard
 * @author Michael Chi
 */
class Block {

    private HashMap<Object, Object> store;
    private EvictionPolicy policy;
    private int nWay;

    /**
     * Initialize the block, has-a hashmap as storage for key and value.
     * Load factor of 1 since we are not resizing.
     * Defers to policy to understand correct key movement and evicts.
     * @param nWay number of lines in block
     * @param policy eviction policy cache uses
     */
    Block(int nWay, EvictionPolicy policy){
        this.nWay = nWay;
        int LOAD_FACTOR = 1;
        this.store = new HashMap<>(nWay, LOAD_FACTOR);

        this.policy = policy;
    }

    /**
     * Gets value for associated key, null if doesn't exist.
     * <p>Tells policy object to keep track of last key accessed.</p>
     * @param key the key whose associated value is to be returned
     * @return value associated with key
     */
    Object get(Object key){
        if(this.store.containsKey(key)){
            this.policy.track(key);
            return this.store.get(key);
        }
        else{
            return null;
        }
    }

    /**
     * Maps key and associated value to block.
     * <p>
     * Tells policy object to keep track of last key accessed.
     * Asks policy object for correct key to evict.
     * </p>
     * @param key key with which the specified value is to be associated
     * @param value value to be associated with the specified key
     * @return previous value associated with key if key already existed, or null if no mapping existed
     */
    Object put(Object key, Object value){
        if(this.store.containsKey(key)){
            this.policy.track(key);
            return this.store.replace(key, value);
        }
        else{
            //if max capacity;
            if(this.store.size() == this.nWay){
                Object keyToEvict = this.policy.getNextKeyToEvict();
                this.store.remove(keyToEvict);
            }
            this.policy.track(key);
            this.store.put(key, value);
            return null;
        }
    }

}
