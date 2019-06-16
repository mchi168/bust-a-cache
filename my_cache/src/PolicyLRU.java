import java.util.HashMap;
public class PolicyLRU implements EvictionPolicy {

    private class Node{
        Object key;
        //Object value;
        Node prev;
        Node next;
    }


    //always adds node before dummy tail
    private void addNode(Node node){
        node.next = tail;
        node.prev = tail.prev;

        tail.prev.next = node;
        tail.prev = node;
    }

    private void removeNode(Node node){
        node.prev.next = node.next;
        node.next.prev = node.prev;
    }


    private HashMap<Object, Node> map;
    private Node head;
    private Node tail;

    PolicyLRU() {
        this.map = new HashMap<>();

        this.head = new Node();
        this.tail = new Node();

        head.next = tail;
        tail.prev = head;
    }



    @Override
    public void track(Object key) {
        if(this.map.containsKey(key)){
            removeNode(map.get(key));
        }

        Node newNode = new Node();
        newNode.key = key;//maybe change from field to constructor;;TODO
        addNode(newNode);
        this.map.put(key, newNode);
    }

    @Override
    public Object getNextKeyToEvict() {
        //TODO UNSAFE, what if call first, then will remove tail, runtime error.
        //FIXME !1
        Node toEvict = head.next;
        this.map.remove(toEvict.key);
        removeNode(toEvict);
        return toEvict.key;
    }
}
