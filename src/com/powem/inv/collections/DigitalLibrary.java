package com.powem.inv.collections;
//Day1: Advanced Use of Collections in Real-World Scenarios
//
//You are developing a system for a digital library that needs to efficiently manage its collection of ebooks and audio books.
//Each book has a unique identifier, a title, and is associated with specific genres.
//
//Create a Java class DigitalLibrary that uses a HashMap to manage books. Implement features to add books, search for books
//by title, and list all books in a genre.
//
//Create a method addBook(String id, String title, List<String> genres) which adds a book to the library
//
//Create method Book findBookById(String id) which returns a book if it exists
//
//Create method List<Book> findBooksByGenre(String genre) which finds book with a given genre

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class DigitalLibrary {
  public static class Book {
    private String id;
    private String title;
    private Set<String> genres;

    public Book(String id, String title, List<String> genres) {
      this.id = id;
      this.title = title;
      this.genres = new HashSet<>(genres);
    }

    public String getTitle() {
      return title;
    }

    public Set<String> getGenres() {
      return genres;
    }
  }

  private Map<String, Book> booksById;
  private Map<String, List<Book>> booksByGenre;

  public DigitalLibrary() {
    this.booksById = new HashMap<>();
    this.booksByGenre = new HashMap<>();
  }

  public void addBook(String id, String title, List<String> genres) {
    if (id == null || id.isEmpty() || title == null || title.isEmpty() || genres == null || genres.isEmpty()) {
      throw new IllegalArgumentException("Book details cannot be null or empty");
    }
    Book newBook = new Book(id, title, genres);
    booksById.put(id, newBook);
    for (String genre : genres) {
      booksByGenre.putIfAbsent(genre, new ArrayList<>());
      booksByGenre.get(genre).add(newBook);
    }
  }

  public Book findBookById(String id) {
    if (id == null || id.isEmpty()) {
      throw new IllegalArgumentException("ID cannot be null or empty");
    }
    return booksById.get(id);
  }

  public List<Book> findBooksByGenre(String genre) {
    if (genre == null || genre.isEmpty()) {
      throw new IllegalArgumentException("Genre cannot be null or empty");
    }
    return booksByGenre.getOrDefault(genre, Collections.emptyList());
  }
}

//import java.util.Arrays;
//
//public class Main {
//  public static void main(String[] args) {
//    DigitalLibrary library = new DigitalLibrary();
//    library.addBook("001", "How to be rich", Arrays.asList("Programming", "Wealth"));
//    library.addBook("002", "Java in Practice", Arrays.asList("Programming", "Wealth", "Concurrency"));
//
//    // TEST
//    assert library.findBookById("001").getTitle().equals("How to be rich");
//    // TEST_END
//
//    // TEST
//    assert library.findBooksByGenre("Wealth").size() == 2;
//    // TEST_END
//
//    // TEST
//    assert library.findBooksByGenre("History").isEmpty();
//    // TEST_END
//
//    // TEST
//    try {
//      library.findBookById("");
//      assert false;
//    } catch (IllegalArgumentException e) {
//      assert true;
//    }
//    // TEST_END
//
//    // TEST
//    try {
//      library.findBooksByGenre(null);
//      assert false;
//    } catch (IllegalArgumentException e) {
//      assert true;
//    }
//    // TEST_END
//  }
//}