
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

package com.powem.inv.algos;

import java.nio.ByteBuffer;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeMap;

public class ConsistentHashing {
    private final TreeMap<Integer, String> hashRing;
    private int VIRTUAL_NODES;

    public ConsistentHashing(int virtualNodes) {
        this.hashRing = new TreeMap<>();
        this.VIRTUAL_NODES = virtualNodes;
    }

    public void addNode(String node) {
        if (node == null || node.isEmpty()) {
            throw new IllegalArgumentException("Node cannot be null or empty");
        }
        for (int i = 0; i < VIRTUAL_NODES; i++) {
            int hash = hash(node + "#" + i);
            hashRing.put(hash, node);
        }
    }

    public void removeNode(String node) {
        if (node == null || node.isEmpty()) {
            throw new IllegalArgumentException("Node cannot be null or empty");
        }
        for (int i = 0; i < VIRTUAL_NODES; i++) {
            int hash = hash(node + "#" + i);
            hashRing.remove(hash);
        }
    }

    public String getNodeForKey(String key) {
        if (key == null || key.isEmpty()) {
            throw new IllegalArgumentException("Key cannot be null or empty");
        }
        int hash = hash(key);
        if (hashRing.ceilingEntry(hash) != null) {
            return hashRing.ceilingEntry(hash).getValue();
        }
        return hashRing.firstEntry().getValue();
    }

    public void setVirtualNodes(int virtualNodes) {
        if (virtualNodes <= 0) {
            throw new IllegalArgumentException("Number of virtual nodes must be positive");
        }
        this.VIRTUAL_NODES = virtualNodes;
        redistributeNodes();
    }

    private void redistributeNodes() {
        List<String> nodes = new ArrayList<>(new HashSet<>(hashRing.values()));
        hashRing.clear();
        for (String node : nodes) {
            addNode(node);
        }
    }

    private int hash(String key) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] digest = md.digest(key.getBytes());
            return ByteBuffer.wrap(digest).getInt() & 0xfffffff;
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-256 algorithm not found", e);
        }
    }

    public Set<String> getAllNodes() {
        return new HashSet<>(hashRing.values());
    }
}

//TESTS
//import com.powem.inv.algos.ConsistentHashing;
//
//import java.util.Set;
//public class Main {
//    public static void main(String[] args) {
//        int virtualNodes = 10;
//        ConsistentHashing ch = new ConsistentHashing(virtualNodes);
//        ch.addNode("Server1");
//        ch.addNode("Server2");
//        ch.addNode("Server3");
//
//        String nodeForKey = ch.getNodeForKey("someKey123");
//
//        //TEST
//        assert nodeForKey.equals("Server1") || nodeForKey.equals("Server2") || nodeForKey.equals("Server3");
//        //TEST END
//
//        ch.removeNode("Server2");
//        nodeForKey = ch.getNodeForKey("someKey123");
//
//        //TEST
//        assert !nodeForKey.equals("Server2");
//        //TEST END
//
//        ch.setVirtualNodes(20);
//        ch.addNode("Server4");
//        nodeForKey = ch.getNodeForKey("someKey123");
//
//        //TEST
//        assert nodeForKey.equals("Server1") || nodeForKey.equals("Server3") || nodeForKey.equals("Server4");
//        //TEST END
//
//        Set<String> allNodes = ch.getAllNodes();
//        //TEST
//        assert allNodes.size() == 3;
//        //TEST END
//
//        //TEST
//        assert allNodes.contains("Server1");
//        //TEST END
//
//        //TEST
//        assert allNodes.contains("Server3");
//        //TEST END
//
//        //TEST
//        assert allNodes.contains("Server4");
//        //TEST END
//
//        try {
//            ch.addNode(null);
//
//            //TEST
//            assert false;
//            //TEST END
//
//        } catch (IllegalArgumentException e) {
//            //TEST
//            assert true;
//            //TEST END
//        }
//
//        try {
//            ch.getNodeForKey("");
//
//            //TEST
//            assert false;
//            //TEST END
//        } catch (IllegalArgumentException e) {
//            //TEST
//            assert true;
//            //TEST END
//        }
//
//        try {
//            ch.setVirtualNodes(-5);
//
//            //TEST
//            assert false;
//            //TEST
//
//        } catch (IllegalArgumentException e) {
//            //TEST
//            assert true;
//            //TEST END
//        }
//    }
//}
