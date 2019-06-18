import java.util.HashMap;

public class PolicyLRU implements EvictionPolicy {

    /**
     * represents singular node in a doubly-linked list
     */
    class Node{
        Object key;
        Node prev;
        Node next;
    }

    /**
     * Always attaches node to 'end' of list, before the dummy tail.
     * Least recently used node will be at 'front', after the dummy head.
     * @param node node to add to list
     */
    void addNode(Node node){
        node.next = tail;
        node.prev = tail.prev;

        tail.prev.next = node;
        tail.prev = node;
    }

    /**
     * Deletes specified node.
     * @param node node to delete
     */
    void removeNode(Node node){
        node.prev.next = node.next;
        node.next.prev = node.prev;
    }


    HashMap<Object, Node> map;
    Node head;
    Node tail;
    PolicyLRU() {
        this.map = new HashMap<>();

        this.head = new Node();
        this.tail = new Node();

        head.next = tail;//dummy head and tail;
        tail.prev = head;
    }

    /**
     * Keeps track of our linked list by maintaining proper key positions.
     * Calling this function means a request has been made.
     * @param key key position to be updated
     */
    @Override
    public void track(Object key) {
        if(this.map.containsKey(key)){
            removeNode(this.map.get(key));
        }

        Node newNode = new Node();
        newNode.key = key;//
        addNode(newNode);
        this.map.put(key, newNode);
    }

    /**
     * Finds and evicts least recently used node from linked list.
     * @return returns least recently used key
     */
    @Override
    public Object getNextKeyToEvict() {
        Node toEvict = head.next;
        this.map.remove(toEvict.key);
        removeNode(toEvict);
        return toEvict.key;
    }

}
