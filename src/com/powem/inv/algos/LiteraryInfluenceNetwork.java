package com.powem.inv.algos;
//Real-Time Literary Influence Network
//Problem:
//Develop a Java class to create and manage a real-time literary influence network, which maps how different authors have influenced each other over time. This network should dynamically update as new influence connections are discovered and should allow users to query the influence path between any two authors.
//
//    Requirements:
//Class Name: LiteraryInfluenceNetwork
//Method Signatures:
//public void addAuthor(String authorId, String name)
//public void addInfluence(String influencerId, String influencedId, int year)
//public List<String> getInfluencePath(String startAuthorId, String endAuthorId)
//Functionality:
//addAuthor: Adds an author to the network.
//addInfluence: Records an influence from one author to another, along with the year it was established.
//getInfluencePath: Finds and returns the shortest path of influence from one author to another, if it exists.


import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

public class LiteraryInfluenceNetwork {
    private Map<String, Author> authors;
    private Map<String, Map<String, Integer>> influenceGraph;

    public LiteraryInfluenceNetwork() {
        this.authors = new HashMap<>();
        this.influenceGraph = new HashMap<>();
    }

    public Map<String, Author> getAuthors() {
        return this.authors;
    }

    public Map<String, Map<String, Integer>> getInfluenceGraph() {
        return this.influenceGraph;
    }

    public void addAuthor(String authorId, String name) {
        if (authorId == null || authorId.isEmpty() || name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Author ID and name cannot be null or empty");
        }
        authors.put(authorId, new Author(authorId, name));
    }

    public void addInfluence(String influencerId, String influencedId, int year) {
        if (!authors.containsKey(influencerId) || !authors.containsKey(influencedId)) {
            throw new IllegalArgumentException("Both authors must exist in the network");
        }
        if (year < 0) {
            throw new IllegalArgumentException("Year must be a positive integer");
        }
        influenceGraph.putIfAbsent(influencerId, new HashMap<>());
        influenceGraph.get(influencerId).put(influencedId, year);
    }

    public List<String> getInfluencePath(String startAuthorId, String endAuthorId) {
        if (!authors.containsKey(startAuthorId) || !authors.containsKey(endAuthorId)) {
            throw new IllegalArgumentException("Both authors must exist in the network");
        }
        // Use BFS to find the shortest path
        Queue<String> queue = new LinkedList<>();
        Map<String, String> parentMap = new HashMap<>();
        queue.offer(startAuthorId);
        parentMap.put(startAuthorId, null);

        while (!queue.isEmpty()) {
            String currentAuthor = queue.poll();
            if (currentAuthor.equals(endAuthorId)) {
                return buildPath(parentMap, endAuthorId);
            }
            for (String neighbor : influenceGraph.getOrDefault(currentAuthor, Collections.emptyMap()).keySet()) {
                if (!parentMap.containsKey(neighbor)) {
                    queue.offer(neighbor);
                    parentMap.put(neighbor, currentAuthor);
                }
            }
        }
        return Collections.emptyList();  // No path found
    }

    private List<String> buildPath(Map<String, String> parentMap, String endAuthorId) {
        LinkedList<String> path = new LinkedList<>();
        for (String at = endAuthorId; at != null; at = parentMap.get(at)) {
            path.addFirst(authors.get(at).name);
        }
        return path;
    }

    public static class Author {
        String authorId;
        String name;

        Author(String authorId, String name) {
            this.authorId = authorId;
            this.name = name;
        }

        public String getName() {
            return this.name;
        }
    }
}

//import com.powem.inv.algos.LiteraryInfluenceNetwork;
//import java.util.List;
//
//public class Main {
//    public static void main(String[] args) {
//        testAuthorAddition();
//        testInfluenceRecording();
//        testInfluencePathFinding();
//        testInputValidation();
//    }
//
//    private static void testAuthorAddition() {
//        LiteraryInfluenceNetwork network = new LiteraryInfluenceNetwork();
//        network.addAuthor("1", "George Orwell");
//        network.addAuthor("2", "Aldous Huxley");
//
//        //TEST
//        assert network.getAuthors().containsKey("1") : "Test Failed: George Orwell should be in the network.";
//        assert "George Orwell".equals(network.getAuthors().get("1").getName()) : "Test Failed: Author name should match.";
//        System.out.println("Test Passed: Author addition verified successfully.");
//        //TEST_END
//    }
//
//    private static void testInfluenceRecording() {
//        LiteraryInfluenceNetwork network = new LiteraryInfluenceNetwork();
//        network.addAuthor("1", "George Orwell");
//        network.addAuthor("2", "Aldous Huxley");
//        network.addInfluence("1", "2", 1949);
//
//        //TEST
//        assert network.getInfluenceGraph().containsKey("1") && network.getInfluenceGraph().get("1").containsKey("2") : "Test Failed: Influence should be recorded.";
//        assert network.getInfluenceGraph().get("1").get("2") == 1949 : "Test Failed: Influence year should be 1949.";
//        System.out.println("Test Passed: Influence recording verified successfully.");
//        //TEST_END
//    }
//
//    private static void testInfluencePathFinding() {
//        LiteraryInfluenceNetwork network = new LiteraryInfluenceNetwork();
//        network.addAuthor("1", "George Orwell");
//        network.addAuthor("2", "Aldous Huxley");
//        network.addAuthor("3", "Ray Bradbury");
//        network.addInfluence("1", "2", 1949);
//        network.addInfluence("2", "3", 1953);
//
//        //TEST
//        List<String> path = network.getInfluencePath("1", "3");
//        assert path.size() == 3 && "George Orwell".equals(path.get(0)) && "Ray Bradbury".equals(path.get(2)) : "Test Failed: Influence path should include Orwell to Bradbury.";
//        System.out.println("Test Passed: Influence path finding verified successfully.");
//        //TEST_END
//    }
//
//    private static void testInputValidation() {
//        LiteraryInfluenceNetwork network = new LiteraryInfluenceNetwork();
//
//        //TEST
//        try {
//            network.addAuthor(null, "George Orwell");
//            assert false : "Test Failed: Adding an author with null ID should throw exception.";
//        } catch (IllegalArgumentException e) {
//            System.out.println("Test Passed: Null author ID input validation succeeded.");
//        }
//        //TEST_END
//
//        //TEST
//        try {
//            network.addInfluence("1", "999", 1949);  // 999 does not exist
//            assert false : "Test Failed: Adding influence with non-existent author should throw exception.";
//        } catch (IllegalArgumentException e) {
//            System.out.println("Test Passed: Non-existent author influence input validation succeeded.");
//        }
//        //TEST_END
//
//        //TEST
//        try {
//            network.getInfluencePath("1", ""); // Empty end author ID
//            assert false : "Test Failed: Getting influence path with empty end author ID should throw exception.";
//        } catch (IllegalArgumentException e) {
//            System.out.println("Test Passed: Empty end author ID path finding input validation succeeded.");
//        }
//        //TEST_END
//    }
//}






