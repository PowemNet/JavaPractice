package com.powem.inv.algos;

import java.util.LinkedHashMap;
import java.util.Map;

//    Problem: Least Recently Used (LRU) Cache
//    Problem Statement:
//    Design a Least Recently Used (LRU) cache system which provides fast access to its entries.
//    The LRU cache should be able to hold a set number of items, and when it reaches its capacity, it should discard
//    the least recently used item before adding a new item.
//
//    Develop a class that manages a cache with operations to "get" and "put" items based on key-value pairs.
//    The cache should efficiently maintain the most recently accessed items up to a given capacity.
//
//    get(key): Retrieve the value associated with the key if the key exists in the cache, otherwise return -1.
//    put(key, value): Insert or replace the value by the key. If the cache is full, remove the least recently
//    used item first.
//
//    Implement the LRUCache using appropriate data structures to ensure efficient access, insertion, and deletion of items.
//
//    Function Signature:
//    public class LRUCache {
//    public LRUCache(int capacity) {
//    }
//      public int get(int key) {
//      }
//      public void put(int key, int value) {
//      }
//}
//


public class LRUCache {
    private final int capacity;
    private final Map<Integer, Integer> cache;

    public LRUCache(int capacity) {
        this.capacity = capacity;
        this.cache = new LinkedHashMap<>(capacity, 0.75f, true) {
            protected boolean removeEldestEntry(Map.Entry<Integer, Integer> eldest) {
                return size() > LRUCache.this.capacity;
            }
        };
    }

    public int get(int key) {
        return cache.getOrDefault(key, -1);
    }

    public void put(int key, int value) {
        cache.put(key, value);
    }
}


//public class Main {
//    public static void main(String[] args) {
//        LRUCache cache = new LRUCache(3);
//        cache.put(1, 1);
//        cache.put(2, 2);
//        cache.put(3, 3);
//        cache.get(1);
//        cache.put(4, 4);
//
//        //TEST
//        assert cache.get(2) == -1;
//        //TEST_END
//
//        //TEST
//        assert cache.get(1) != -1;
//        //TEST_END
//
//        //TEST
//        assert cache.get(3) != -1;
//        //TEST_END
//
//        //TEST
//        assert cache.get(4) != -1;
//        //TEST_END
//    }
//}

