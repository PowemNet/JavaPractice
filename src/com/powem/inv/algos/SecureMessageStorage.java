package com.powem.inv.algos;
//Day 9: Secure Message Storage System
//Objective:
//Develop a system to store and retrieve encrypted messages using hash tables to ensure quick
// access and maintain confidentiality.
//
//Create a secure storage system for messages where each message is
// stored in an encrypted format. The system should provide functionalities to add messages,
// retrieve them, and delete them securely, all while ensuring that the operations are performed efficiently.
//
//Implement the SecureMessageStorage class using a combination of HashMap for storage and
// the Caesar Cipher encryption mechanism for encrypting the messages before
// storing them. The system should also support decryption for message retrieval.
//
//Detailed Method Requirements:
//
//addMessage(String id, String message): Encrypts and stores the message associated with a unique ID.
//
//String getMessage(String id): Decrypts and retrieves the message associated with the given ID.
//
//boolean deleteMessage(String id): Deletes the message associated with the given ID.


import java.util.HashMap;
import java.util.Map;

public class SecureMessageStorage {
  private Map<String, String> messages;
  private final int shift = 4;

  public SecureMessageStorage() {
    messages = new HashMap<>();
  }

  public void addMessage(String id, String message) {
    if (id == null || id.isEmpty()) {
      throw new IllegalArgumentException("ID cannot be null or empty.");
    }
    if (message == null) {
      throw new IllegalArgumentException("Message cannot be null.");
    }
    messages.put(id, encrypt(message));
  }

  public String getMessage(String id) {
    if (id == null || id.isEmpty()) {
      throw new IllegalArgumentException("ID cannot be null or empty.");
    }
    String encryptedMessage = messages.get(id);
    if (encryptedMessage == null) {
      throw new IllegalArgumentException("Message not found for given ID.");
    }
    return decrypt(encryptedMessage);
  }

  public boolean deleteMessage(String id) {
    if (id == null || id.isEmpty()) {
      throw new IllegalArgumentException("ID cannot be null or empty.");
    }
    if (messages.containsKey(id)) {
      messages.remove(id);
      return true;
    }
    return false;
  }

  private String encrypt(String message) {
    return shift(message, shift);
  }

  private String decrypt(String message) {
    return shift(message, -shift);
  }

  private String shift(String text, int shift) {
    shift = (shift % 26 + 26) % 26;
    StringBuilder encrypted = new StringBuilder();
    for (char character : text.toCharArray()) {
      if (character >= 'a' && character <= 'z') {
        character = (char) ((character - 'a' + shift) % 26 + 'a');
      } else if (character >= 'A' && character <= 'Z') {
        character = (char) ((character - 'A' + shift) % 26 + 'A');
      }
      encrypted.append(character);
    }
    return encrypted.toString();
  }

}

//public class Main {
//  public static void main(String[] args) {
//    SecureMessageStorage storage = new SecureMessageStorage();
//
//    //TEST
//    storage.addMessage("001", "Hello World");
//    String message = storage.getMessage("001");
//    assert "Hello World".equals(message);
//    //TEST_END
//
//    //TEST
//    try {
//      storage.addMessage(null, "Test Message");
//      assert false;
//    } catch (IllegalArgumentException e) {
//      assert true;
//    }
//    //TEST_END
//
//    //TEST
//    try {
//      storage.addMessage("", "Test Message");
//      assert false;
//    } catch (IllegalArgumentException e) {
//      assert true;
//    }
//    //TEST_END
//
//    //TEST
//    try {
//      storage.addMessage("002", null);
//      assert false;
//    } catch (IllegalArgumentException e) {
//      assert true;
//    }
//    //TEST_END
//
//    //TEST
//    try {
//      storage.getMessage("003");
//      assert false;
//    } catch (IllegalArgumentException e) {
//      assert true;
//    }
//    //TEST_END
//
//    //TEST
//    storage.addMessage("004", "Temporary Message");
//    assert storage.deleteMessage("004");
//    //TEST_END
//
//    //TEST
//    assert !storage.deleteMessage("999");
//    //TEST_END
//
//    //TEST
//    try {
//      storage.deleteMessage("");
//      assert false;
//    } catch (IllegalArgumentException e) {
//      assert true;
//    }
//    //TEST_END
//
//    //TEST
//    try {
//      storage.deleteMessage(null);
//      assert false;
//    } catch (IllegalArgumentException e) {
//      assert true;
//    }
//    //TEST_END
//  }
//}
