
interface EvictionPolicy {

    void track(Object key);

    Object getNextKeyToEvict();

}