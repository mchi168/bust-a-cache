public class PolicyMRU extends PolicyLRU {

    @Override
    public Object getNextKeyToEvict() {
        Node toEvict = tail.prev;
        this.map.remove(toEvict.key);
        removeNode(toEvict);
        return toEvict.key;
    }

}
