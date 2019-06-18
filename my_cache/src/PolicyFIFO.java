import java.util.LinkedHashMap;
/**
 * The cache evicts the first block accessed first without any regard to
 * how often or how many times it was accessed before.
 * @author The_Client
 */
public class PolicyFIFO implements EvictionPolicy{

    private LinkedHashMap<Object, Object> map = new LinkedHashMap<>();

    @Override
    public void track(Object key) {
        //if map DOES NOT contain key, add. else do nothing
        if(!map.containsKey(key)){
            map.put(key, null);
        }
    }

    @Override
    public Object getNextKeyToEvict() {
        Object keyToReturn = map.keySet().iterator().next();
        map.remove(keyToReturn);
        return keyToReturn;
    }

}
