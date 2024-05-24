package com.powem.inv.algos;
//Efficient Management of a Product Inventory System
//Objective:
//Learn to use arrays and binary search techniques in Java to manage and optimize searches
// within a product inventory system, handling large datasets with efficiency.
//
//Scenario:
//You are developing a system to manage a retail store's inventory. Each product in the inventory
// has a unique product ID, a name, a category, a price, and a stock count. The system must
// allow quick retrieval of products by name and efficient sorting and searching based on price or stock levels.
//
//Challenge:
//Create the ProductInventorySystem class using a combination of Java's ArrayList and binary search
// algorithm to manage the product list. Implement features to add products, search products by name
// using binary search, and sort products by price or stock.
//
//Detailed Method Requirements:
//
//addProduct(String productId, String name, String category, double price, int stock): Adds a product to the inventory.
//
//Product findProductByName(String name): Efficiently finds a product by its name using binary search.
//
//List<Product> sortProductsByPrice(): Returns a list of products sorted by price.
//
//List<Product> sortProductsByStock(): Returns a list of products sorted by stock level.


import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ProductInventorySystem {
  public static class Product implements Comparable<Product> {
    private String productId;
    private String name;
    private String category;
    private double price;
    private int stock;

    public Product(String productId, String name, String category, double price, int stock) {
      if ((productId == null || productId.isEmpty()) && price == 0 && stock == 0) {
        this.name = name;
      } else {
        if (productId == null || productId.isEmpty()) {
          throw new IllegalArgumentException("Product ID cannot be null or empty.");
        }
        if (name == null || name.isEmpty()) {
          throw new IllegalArgumentException("Name cannot be null or empty.");
        }
        if (category == null || category.isEmpty()) {
          throw new IllegalArgumentException("Category cannot be null or empty.");
        }
        if (price < 0) {
          throw new IllegalArgumentException("Price cannot be negative.");
        }
        if (stock < 0) {
          throw new IllegalArgumentException("Stock cannot be negative.");
        }
        this.productId = productId;
        this.name = name;
        this.category = category;
        this.price = price;
        this.stock = stock;
      }
    }

    @Override
    public int compareTo(Product other) {
      return this.name.compareTo(other.name);
    }

    public String getName() {
      return name;
    }

    public double getPrice() {
      return price;
    }

    public int getStock() {
      return stock;
    }
  }

  private List<Product> products;

  public ProductInventorySystem() {
    this.products = new ArrayList<>();
  }

  public void addProduct(String productId, String name, String category, double price, int stock) {
    products.add(new Product(productId, name, category, price, stock));
    products.sort(Comparator.comparing(Product::getName));
  }

  public Product findProductByName(String name) {
    Comparator<Product> nameComparator = Comparator.comparing(Product::getName);
    int index = Collections.binarySearch(products, null, (prod, dummy) -> nameComparator.compare(prod, new Product("", name, "", 0, 0)));
    if (index >= 0) {
      return products.get(index);
    }
    throw new IllegalArgumentException("Product with specified name not found");
  }


  public List<Product> sortProductsByPrice() {
    List<Product> sortedList = new ArrayList<>(products);
    sortedList.sort(Comparator.comparingDouble(Product::getPrice));
    return sortedList;
  }

  public List<Product> sortProductsByStock() {
    List<Product> sortedList = new ArrayList<>(products);
    sortedList.sort(Comparator.comparingInt(Product::getStock));
    return sortedList;
  }
}


//public class Main {
//  public static void main(String[] args) {
//    ProductInventorySystem library = new ProductInventorySystem();
//
//    //TEST
//    library.addProduct("001", "How to be rich", "Business", 25.99, 50);
//
//    try {
//      library.addProduct("", "Java in Practice", "Programming", 15.99, 30);
//      assert false;
//    } catch (IllegalArgumentException e) {
//      assert true;
//    }
//    //TEST_END
//
//    //TEST
//    try {
//      library.addProduct("002", "Java in Practice", "Programming", -15.99, 30);
//      assert false;
//    } catch (IllegalArgumentException e) {
//      assert true;
//    }
//    //TEST_END
//
//    //TEST
//    ProductInventorySystem.Product result = library.findProductByName("How to be rich");
//    assert result.getName().equals("How to be rich");
//    //TEST_END
//
//    //TEST
//    try {
//      library.findProductByName("Non Existent");
//      assert false;
//    } catch (IllegalArgumentException e) {
//      assert true;
//    }
//    //TEST_END
//
//    //TEST
//    library.addProduct("003", "C++ Demystified", "Programming", 29.99, 15);
//    var sortedProducts = library.sortProductsByPrice();
//    assert sortedProducts.get(0).getPrice() <= sortedProducts.get(1).getPrice();
//    //TEST_END
//
//    //TEST
//    var sortedProducts2 = library.sortProductsByStock();
//    assert sortedProducts2.get(0).getStock() <= sortedProducts2.get(1).getStock();
//    //TEST_END
//  }
//}
