# README

######n-way set-associative cache

## Usage

NOTE: third param is String that represents implemented EvictionPolicy interface.

If your EvictionPolicy class is called 'Apple'
Supply argument as "Apple".

Java must 'know' about the class. Must exist somewhere in classpath; within context of application.

Example: 
```java
Cache<Integer, String> my_class = new Cache(4, 2, "PolicyLRU");

myCache.put(42, "foobar");
myCache.get(42); //returns "foobar"
```

