
import java.util.ArrayList;
/**
 * N-way Set-Associative Cache
 * <p>
 * Cache represented by ArrayList of Blocks, each block containing n-lines.
 * Takes in EvictionPolicy that manages items to discard when cache fills.
 * </p>
 * @author Michael Chi
 */
class Cache<K, V>{

    private int nSet;
    private ArrayList<Block> cache;

    /**
     * Initialize cache with based on user defined eviction policy.
     * <p>
     * nSet and nWay must be positive. policy cannot be null. policy MUST be String name of
     * an implemented EvictionPolicy interface.
     * </p>
     * @param nSet number of blocks in cache
     * @param nWay number of lines per block
     * @param evictionPolicyString String name of implemented EvictionPolicy
     */
    Cache(int nSet, int nWay, String evictionPolicyString) {

        if(nSet < 1){
            throw new IllegalArgumentException("number of sets cannot be 0 or negative, pass positive int");
        }
        if(nWay < 1){
            throw new IllegalArgumentException("number of ways cannot be 0 or negative, pass positive");
        }
        if(evictionPolicyString == null){
            throw new IllegalArgumentException("policy cannot be null, pass instance of EvictionPolicy");
        }

        this.nSet = nSet;
        this.cache = new ArrayList<>(nSet);

        try {
            Class clazz = Class.forName(evictionPolicyString);
            for( int i = 0; i < nSet; i += 1 ){
                this.cache.add( new Block(nWay, (EvictionPolicy)clazz.newInstance()) );
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    /**
     * Find's correct block depending upon hashed key
     * @param key key to be calculated to proper index
     * @return index to access correct block
     */
    private int findBlock(K key){
        return Math.abs( key.hashCode() ) % this.nSet;
    }

    /**
     * Returns the value to which the specified key is mapped,
     * or null if this map contains no mapping for the key.
     * @param key key whose associated value is to be returned
     * @return value associated with key
     */
    Object get(K key){
        return cache.get( findBlock(key) ).get(key);
    }

    /**
     * Associates the specified value with the specified key in this map.
     * If the map previously contained a mapping for the key, the old value is replaced.
     * @param key key with which the specified value is to be associated
     * @param value value to be associated with the specified key
     * @return the previous value associated with key, or null if there was no mapping for key.
     */
    Object put(K key, V value){
        return cache.get(findBlock(key)).put(key, value);
    }

}
