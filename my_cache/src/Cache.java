import java.util.ArrayList;

public class Cache<K, V> {

    private int nSet;
    private int nWay;
    private EvictionPolicy policy;
    private ArrayList<Block> cache;

    Cache(int nSet, int nWay, EvictionPolicy policy){
        this.nSet = nSet;
        this.nWay = nWay;
        this.policy = policy;

        this.cache = new ArrayList<>(nSet);
        for( int i = 0; i < nSet; i += 1 ){
            this.cache.add( new Block(nWay, policy) );
        }
    }

    //returns index of correct block
    private int findBlock(K key){
        int blockIndex = Math.abs(key.hashCode()) % this.nSet;;
        return blockIndex;
    }

    //return associated value
    Object get(K key){
        return cache.get(findBlock(key)).get(key);//TODO test this
    }

    //If an existing key is passed then the previous value gets returned.
    // If a new pair is passed, then NULL is returned.
    Object put(K key, V value){
        //TODO test this
        return cache.get(findBlock(key)).put(key, value);
    }

    void printCache(){
        for(int i = 0; i < cache.size(); i += 1){
            System.out.print("block " + i + ":");
            cache.get(i).printBlock();
            System.out.print(" ");
        }
        System.out.println("");
        System.out.println("-------");
    }
}
