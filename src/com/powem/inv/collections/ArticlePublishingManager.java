package com.powem.inv.collections;
//Day 5: Article Publishing Queue Management
//Objective:
//Learn to effectively use the PriorityQueue for managing and scheduling articles based on their publication deadlines.
//
//You are developing a system for an online magazine that schedules articles for publication. Each article is
// associated with a title, an author, a unique identifier, and a scheduled publication date. The system needs
// to prioritize articles that are scheduled to be published sooner.
//
//Create a Java class ArticlePublishingManager that uses a PriorityQueue to manage and prioritize articles for
// publication. Implement methods to schedule articles, update publication dates, and retrieve the next article
// due for publication.
//
//Detailed Method Requirements:
//
//scheduleArticle(String id, String title, String author, Date publishDate): Adds an article to the system with
// a specified publication date.

//updatePublicationDate(String id, Date newPublishDate): Updates an existing article's publication date.

//getNextArticle(): Retrieves and removes the article with the earliest publication date from the queue,
// indicating it's next to be published.

import java.util.Date;
import java.util.PriorityQueue;

public class ArticlePublishingManager {
  public static class Article implements Comparable<Article> {
    private String id;
    private String title;
    private String author;
    private Date publishDate;

    public Article(String id, String title, String author, Date publishDate) {
      if (id == null || id.isEmpty()) {
        throw new IllegalArgumentException("Article ID cannot be null or empty.");
      }
      if (title == null || title.isEmpty()) {
        throw new IllegalArgumentException("Article title cannot be null or empty.");
      }
      if (author == null || author.isEmpty()) {
        throw new IllegalArgumentException("Author cannot be null or empty.");
      }
      if (publishDate == null) {
        throw new IllegalArgumentException("Publish date cannot be null.");
      }
      if (publishDate.before(new Date())) {
        throw new IllegalArgumentException("Publish date cannot be in the past.");
      }
      this.id = id;
      this.title = title;
      this.author = author;
      this.publishDate = publishDate;
    }

    @Override
    public int compareTo(Article other) {
      return this.publishDate.compareTo(other.publishDate);
    }

    public String getId() {
      return this.id;
    }
  }

  private PriorityQueue<Article> articles;

  public ArticlePublishingManager() {
    this.articles = new PriorityQueue<>();
  }

  public void scheduleArticle(String id, String title, String author, Date publishDate) {
    articles.add(new Article(id, title, author, publishDate));
  }

  public boolean updatePublicationDate(String id, Date newPublishDate) {
    for (Article article : articles) {
      if (article.id.equals(id)) {
        articles.remove(article);
        article.publishDate = newPublishDate;
        articles.add(article);
        return true;
      }
    }
    return false;
  }

  public Article getNextArticle() {
    return articles.poll();
  }
}


//import java.util.Date;
//
//public class Main {
//  public static void main(String[] args) {
//    ArticlePublishingManager manager = new ArticlePublishingManager();
//    manager.scheduleArticle("001", "The Future of AI", "Power Mukisa", new Date(System.currentTimeMillis() + 86400000));
//    manager.scheduleArticle("002", "Business Analytics", "Alice Mukisa", new Date(System.currentTimeMillis() + 43200000));
//
//    // TEST
//    ArticlePublishingManager.Article nextArticle = manager.getNextArticle();
//    assert nextArticle.getId().equals("002");
//    // TEST_END
//
//    // TEST
//    boolean updateSuccess = manager.updatePublicationDate("001", new Date(System.currentTimeMillis() + 3600000));
//    assert updateSuccess;
//    // TEST_END
//
//    // TEST
//    nextArticle = manager.getNextArticle();
//    assert nextArticle.getId().equals("001");
//    // TEST_END
//
//    // TEST
//    boolean falseUpdate = manager.updatePublicationDate("003", new Date());
//    assert !falseUpdate;
//    // TEST_END
//
//    // TEST
//    try {
//      manager.scheduleArticle("", "", "Author", new Date());
//      assert false;
//    } catch (IllegalArgumentException e) {
//      assert true;
//    }
//    // TEST_END
//
//    // TEST
//    try {
//      manager.scheduleArticle("004", "Past Article", "Historian", new Date(System.currentTimeMillis() - 10000));
//      assert false;
//    } catch (IllegalArgumentException e) {
//      assert true;
//    }
//    // TEST_END
//
//    // TEST
//    while (manager.getNextArticle() != null)
//      ;
//    assert manager.getNextArticle() == null;
//    // TEST_END
//  }
//}
