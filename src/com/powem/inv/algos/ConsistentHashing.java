package com.powem.inv.algos;

import java.util.TreeMap;

//    Problem: Implementing Consistent Hashing
//    Problem Statement:
//    Develop a Java class to simulate a consistent hashing mechanism, which is commonly used in distributed caching
//    systems to achieve uniform data distribution and minimal rehashing when servers are added or removed.
//
//    Create a class that manages a consistent hash ring. Implement methods to add servers (nodes), remove servers,
//    and map keys to servers efficiently.
//
//
//    The hash ring should consider virtual nodes for each server to ensure uniform distribution.
//    The class should handle lookups for where keys should be stored based on the hash.
//    Challenge:
//    Efficiently manage the hash ring, ensuring minimal disruption (movement of keys between servers) when nodes are
//    added or removed, and ensuring uniform distribution across nodes.
//
//    Function Signature Suggestion:

//    public class ConsistentHashing {
//        public void addNode(String node, int virtualNodes) {
//        }

//        public void removeNode(String node) {
//        }

//        public String getNodeForKey(String key) {
//       }
//    }

public class ConsistentHashing {
    private TreeMap<Integer, String> hashRing;
    private int VIRTUAL_NODES;

    public ConsistentHashing(int virtualNodes) {
        this.hashRing = new TreeMap<>();
        this.VIRTUAL_NODES = virtualNodes;
    }

    public void addNode(String node) {
        for (int i = 0; i < VIRTUAL_NODES; i++) {
            int hash = hash(node + "#" + i);
            hashRing.put(hash, node);
        }
    }

    public void removeNode(String node) {
        for (int i = 0; i < VIRTUAL_NODES; i++) {
            int hash = hash(node + "#" + i);
            hashRing.remove(hash);
        }
    }

    public String getNodeForKey(String key) {
        int hash = hash(key);
        if (hashRing.ceilingEntry(hash) != null) {
            return hashRing.ceilingEntry(hash).getValue();
        }
        return hashRing.firstEntry().getValue();
    }

    private int hash(String key) {
        return key.hashCode() & 0xfffffff;
    }
}

//import com.powem.inv.algos.ConsistentHashing;
//
//public class Main {
//    public static void main(String[] args) {
//        int virtualNodes = 10;
//        ConsistentHashing ch = new ConsistentHashing(virtualNodes);
//        ch.addNode("Server1");
//        ch.addNode("Server2");
//        ch.addNode("Server3");
//
//        //TEST
//        String nodeForKey = ch.getNodeForKey("someKey123");
//        assert nodeForKey.equals("Server1") || nodeForKey.equals("Server2") || nodeForKey.equals("Server3");
//        //TEST_END
//
//        //TEST
//        ch.removeNode("Server2");
//        nodeForKey = ch.getNodeForKey("someKey123");
//        assert !nodeForKey.equals("Server2");
//        //TEST_END
//    }
//}
