import com.powem.inv.algos.ResourceDistributor;

public class Main {
  public static void main(String[] args) {
    ResourceDistributor distributor = new ResourceDistributor();

    //TEST
    distributor.addNode(1, 100, 80);
    distributor.addNode(2, 30, 50);
    distributor.addEdge(1, 2);
    distributor.distributeResources();

    assert distributor.getNodeResourceLevels().get(1) >= 80;
    //TEST_END

    //TEST
    try {
      distributor.addEdge(1, -2);
      assert false;
    } catch (IllegalArgumentException e) {
      assert true;
    }
    //TEST_END

    //TEST
    try {
      distributor.addEdge(1, 0);
      assert false;
    } catch (IllegalArgumentException e) {
      assert true;
    }
    //TEST_END

    //TEST
    assert distributor.getNodeResourceLevels().get(2) >= 50;
    //TEST_END

    //TEST
    distributor.addNode(3, 60, 60);
    distributor.addEdge(2, 3);
    distributor.distributeResources();
    assert distributor.getNodeResourceLevels().get(3) == 60;
    //TEST_END

    //TEST
    distributor.addNode(4, 40, 30);
    distributor.addNode(5, 50, 50);
    distributor.addEdge(4, 5);
    distributor.distributeResources();

    assert distributor.getNodeResourceLevels().get(4) >= 30;
    //TEST_END

    //TEST
    assert distributor.getNodeResourceLevels().get(5) == 50;
    //TEST_END

    //TEST
    try {
      distributor.addNode(6, -10, 20);
      assert false;
    } catch (IllegalArgumentException e) {
      assert true;
    }
    //TEST_END

    //TEST
    try {
      distributor.addNode(0, 10, 20);
      assert false;
    } catch (IllegalArgumentException e) {
      assert true;
    }
    //TEST_END

    //TEST
    try {
      distributor.addNode(1, 1, 0);
      assert false;
    } catch (IllegalArgumentException e) {
      assert true;
    }
    //TEST_END

    //TEST: Check distribution after adding edges between multiple nodes
    distributor.addNode(7, 25, 25);
    distributor.addEdge(5, 7);
    distributor.distributeResources();
    assert distributor.getNodeResourceLevels().get(7) == 25;
    //TEST_END
  }
}
