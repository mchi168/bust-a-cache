public class Main {

    public static void main(String[] args) {
        EvictionPolicy algoLru = new PolicyLRU();
        Cache<Integer, String> myCache = new Cache<>(2, 2, algoLru);

        myCache.put(0, "Michael");
        myCache.printCache();

        myCache.put(2, "john");
        myCache.printCache();

        myCache.put(4, "kim");
        myCache.printCache();

        System.out.println("we will put another 2, to replace 'john'");
        myCache.put(2,  "bravo");
        myCache.printCache();

        myCache.put(6, "should replace 'kim'");
        System.out.println(myCache.get(6));
        myCache.printCache();

        myCache.put(14142, "apeshit");
        myCache.printCache();

        System.out.println("getting 6; should move 6 to front");
        myCache.get(6);
        System.out.println("this means the follwing 'put' call should remove (14142:'apeshit')");
        myCache.put(42, "computer");
        myCache.printCache();


        System.out.println("Hello World!");
    }
}
