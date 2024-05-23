import com.powem.inv.metaprep.ProductInventorySystem;

public class Main {
  public static void main(String[] args) {
    ProductInventorySystem library = new ProductInventorySystem();

    //TEST
    library.addProduct("001", "How to be rich", "Business", 25.99, 50);

    try {
      library.addProduct("", "Java in Practice", "Programming", 15.99, 30);
      assert false;
    } catch (IllegalArgumentException e) {
      assert true;
    }
    //TEST_END

    //TEST
    try {
      library.addProduct("002", "Java in Practice", "Programming", -15.99, 30);
      assert false;
    } catch (IllegalArgumentException e) {
      assert true;
    }
    //TEST_END

    //TEST
    ProductInventorySystem.Product result = library.findProductByName("How to be rich");
    assert result.getName().equals("How to be rich");
    //TEST_END

    //TEST
    try {
      library.findProductByName("Non Existent");
      assert false;
    } catch (IllegalArgumentException e) {
      assert true;
    }
    //TEST_END

    //TEST
    library.addProduct("003", "C++ Demystified", "Programming", 29.99, 15);
    var sortedProducts = library.sortProductsByPrice();
    assert sortedProducts.get(0).getPrice() <= sortedProducts.get(1).getPrice();
    //TEST_END

    //TEST
    var sortedProducts2 = library.sortProductsByStock();
    assert sortedProducts2.get(0).getStock() <= sortedProducts2.get(1).getStock();
    //TEST_END
  }
}
