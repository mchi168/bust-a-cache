import java.util.HashMap;

class Block {

    private HashMap<Object, Object> store;
    private EvictionPolicy policy;
    private int nWay;

    Block(int nWay, EvictionPolicy policy){
        this.nWay = nWay;
        int LOAD_FACTOR = 1;
        this.store = new HashMap<>(nWay, LOAD_FACTOR);

        this.policy = policy;
    }

    Object get(Object key){
        if(this.store.containsKey(key)){
            this.policy.track(key);
            return store.get(key);
        }
        else{
            return null;//TODO test
        }
    }

    //If an existing key is passed then the previous value gets returned.
    // If a new pair is passed, then NULL is returned.
    Object put(Object key, Object value){
        if(store.containsKey(key)){
            this.policy.track(key);
            return this.store.replace(key, value);
        }
        else{
            //if max capacity;
            if(store.size() == this.nWay){
                Object keyToEvict = this.policy.getNextKeyToEvict();
                System.out.println("we are evicting:" +keyToEvict);
                this.store.remove(keyToEvict);
            }
            this.policy.track(key);
            this.store.put(key, value);
            return null;
        }
        //TODO test
    }


    void printBlock(){
        System.out.print(this.store.toString());
    }
}
