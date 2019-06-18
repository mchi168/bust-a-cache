import org.junit.Test;

import static org.junit.Assert.*;

public class BlockTest {
    EvictionPolicy lru = new PolicyLRU();
    Block blockLRU = new Block(2, lru);

    EvictionPolicy mru = new PolicyMRU();
    Block blockMRU = new Block(2, mru);

    @Test
    public void getEmpty() {
        assertNull(blockLRU.get(0));
    }

    @Test
    public void putThenGet(){
        blockLRU.put(0, "apple");
        assertEquals("apple", blockLRU.get(0));
    }

    @Test
    public void putMoreThanMaxSize() {
        blockLRU.put(0, "The");
        blockLRU.put(2, "Trade");
        blockLRU.put(4, "Desk");

        assertNull(blockLRU.get(0));
        assertEquals("Trade", blockLRU.get(2));
        assertEquals("Desk", blockLRU.get(4));
    }

    @Test
    public void putReplace(){
        blockLRU.put(0, "The");
        blockLRU.put(2, "Trade");
        blockLRU.put(0, "Desk");


        assertNotEquals("The", blockLRU.get(0));
        assertEquals("Desk", blockLRU.get(0));
    }

    @Test
    public void testGetAccessThenEvict_LRU(){
        blockLRU.put(0, "The");
        blockLRU.put(2, "Trade");

        blockLRU.get(0); //access
        blockLRU.put(4, "banana"); //this should replace <2:Trade> since we recently accessed <0:The>

        assertNull(blockLRU.get(2));
        assertEquals("The", blockLRU.get(0));
        assertEquals("banana", blockLRU.get(4));
    }

    @Test
    public void testGetAccessThenEvict_MRU(){
        blockMRU.put(0, "The");
        blockMRU.put(2, "Trade");

        blockMRU.get(0);//access
        blockMRU.put(4, "banana"); //MRU replace <0:The> since MRU

        assertNull(blockMRU.get(0));
    }
}