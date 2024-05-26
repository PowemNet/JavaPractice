package com.powem.inv.algos;
//Distributed Document Indexer
//**Problem**
//Design a Java system to manage and index documents distributed across multiple servers.
// The system should handle insertion, deletion, and quick retrieval of documents based on
// content queries. It should efficiently distribute the indexing load and ensure that searches
// are quick, even as the volume of documents grows.
//
//**Requirements**
//Class Name: DistributedDocumentIndexer

//**Method Signatures**
//public void addDocument(String serverId, String documentId, String content)

//public void removeDocument(String serverId, String documentId)

//public List<String> searchDocuments(String query)

//**Functionality**
//addDocument: Adds a document to a specified server with its content indexed.

//removeDocument: Removes a document from the specified server and updates the index.

//searchDocuments: Returns a list of document IDs that match the content query, searching across all
//servers.


import java.util.*;

public class DistributedDocumentIndexer {
    private Map<String, Map<String, String>> servers; // serverId -> (documentId -> content)
    private Map<String, Set<String>> index; // keyword -> set of documentIds

    public DistributedDocumentIndexer() {
        this.servers = new HashMap<>();
        this.index = new HashMap<>();
    }

    public Map<String, Map<String, String>> getServers() {
        return this.servers;
    }

    public Map<String, Set<String>> getIndex() {
        return this.index;
    }

    public void addDocument(String serverId, String documentId, String content) {
        if (serverId == null || serverId.isEmpty() || documentId == null || documentId.isEmpty() || content == null) {
            throw new IllegalArgumentException("Invalid input");
        }
        servers.putIfAbsent(serverId, new HashMap<>());
        servers.get(serverId).put(documentId, content);
        indexContent(documentId, content);
    }

    private void indexContent(String documentId, String content) {
        Arrays.stream(content.split("\\s+")).forEach(keyword -> {
            index.putIfAbsent(keyword.toLowerCase(), new HashSet<>());
            index.get(keyword.toLowerCase()).add(documentId);
        });
    }

    public void removeDocument(String serverId, String documentId) {
        if (serverId == null || !servers.containsKey(serverId) || !servers.get(serverId).containsKey(documentId)) {
            throw new IllegalArgumentException("Document or server not found");
        }
        String content = servers.get(serverId).remove(documentId);
        deindexContent(documentId, content);
    }

    private void deindexContent(String documentId, String content) {
        Arrays.stream(content.split("\\s+")).forEach(keyword -> {
            Set<String> docs = index.get(keyword.toLowerCase());
            if (docs != null) {
                docs.remove(documentId);
                if (docs.isEmpty()) {
                    index.remove(keyword.toLowerCase());
                }
            }
        });
    }

    public List<String> searchDocuments(String query) {
        if (query == null || query.isEmpty()) {
            throw new IllegalArgumentException("Query cannot be empty");
        }
        Set<String> results = new HashSet<>();
        Arrays.stream(query.split("\\s+")).forEach(
            keyword -> {
                if (index.containsKey(keyword.toLowerCase())) {
                    results.addAll(index.get(keyword.toLowerCase()));
                }
            }
        );
        return new ArrayList<>(results);
    }
}

//import com.powem.inv.algos.ClimateAnomalyDetector;
//import com.powem.inv.algos.DistributedDocumentIndexer;
//import java.util.List;
//
//public class Main {
//    public static void main(String[] args) {
//        testDocumentAdditionAndRemoval();
//        testDocumentSearch();
//        testInputValidation();
//    }
//
//    private static void testDocumentAdditionAndRemoval() {
//        DistributedDocumentIndexer indexer = new DistributedDocumentIndexer();
//        indexer.addDocument("Server1", "Doc1", "Java is great");
//        indexer.addDocument("Server1", "Doc2", "Distributed systems are scalable");
//
//        //TEST
//        assert indexer.getServers().containsKey("Server1") : "Test Failed: Server should contain documents.";
//        assert indexer.getServers().get("Server1").size() == 2 : "Test Failed: Server1 should have two documents.";
//        System.out.println("Test Passed: Documents added successfully.");
//
//        indexer.removeDocument("Server1", "Doc1");
//        assert !indexer.getIndex().containsKey("java") || !indexer.getIndex().get("java")
//            .contains("Doc1") : "Test Failed: Document Doc1 should be deindexed.";
//        System.out.println("Test Passed: Document removal and deindexing successful.");
//        //TEST_END
//    }
//
//    private static void testDocumentSearch() {
//        DistributedDocumentIndexer indexer = new DistributedDocumentIndexer();
//        indexer.addDocument("Server1", "Doc1", "Java and Python");
//        indexer.addDocument("Server1", "Doc2", "Python and Scala");
//
//        //TEST
//        List<String> searchResults = indexer.searchDocuments("Python");
//        assert searchResults.contains("Doc1") && searchResults.contains(
//            "Doc2") : "Test Failed: Search should return both documents containing 'Python'.";
//        System.out.println("Test Passed: Search functionality verified successfully.");
//        //TEST_END
//    }
//
//    private static void testInputValidation() {
//        DistributedDocumentIndexer indexer = new DistributedDocumentIndexer();
//
//        //TEST
//        try {
//            indexer.addDocument(null, "Doc1", "Test content");
//            assert false : "Test Failed: Adding a document with null serverId should throw exception.";
//        } catch (IllegalArgumentException e) {
//            System.out.println("Test Passed: Null serverId input validation succeeded.");
//        }
//        //TEST_END
//
//        //TEST
//        try {
//            indexer.addDocument("Server1", "", "Test content");
//            assert false : "Test Failed: Adding a document with empty documentId should throw exception.";
//        } catch (IllegalArgumentException e) {
//            System.out.println("Test Passed: Empty documentId input validation succeeded.");
//        }
//        //TEST_END
//
//        //TEST
//        try {
//            indexer.removeDocument("Server1", "Doc3");
//            assert false : "Test Failed: Removing non-existent document should throw exception.";
//        } catch (IllegalArgumentException e) {
//            System.out.println("Test Passed: Removing non-existent document validation succeeded.");
//        }
//        //TEST_END
//
//        //TEST
//        try {
//            indexer.searchDocuments("");
//            assert false : "Test Failed: Searching with empty query should throw exception.";
//        } catch (IllegalArgumentException e) {
//            System.out.println("Test Passed: Empty query search input validation succeeded.");
//        }
//        //TEST_END
//    }
//}

