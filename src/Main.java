import com.powem.inv.algos.NetworkTrafficOptimizer;
import java.util.Map;

public class Main {
  public static void main(String[] args) {
    testBasicConnectivity();
    testCapacityLimits();
    testShortestPathRouting();
  }

  // Test basic connectivity and packet routing
  private static void testBasicConnectivity() {
    NetworkTrafficOptimizer optimizer = new NetworkTrafficOptimizer();
    optimizer.addNode("A");
    optimizer.addNode("B");
    optimizer.addLink("A", "B", 100);

    optimizer.routePacket("A", "B", 50);

    //TEST
    Map<String, Integer> load = optimizer.getCurrentLoadOnLinks();
    assert load.get("A->B") == 50;
    //TEST_END

    //TEST
    try {
      optimizer.addNode("");
      assert false;
    } catch (IllegalArgumentException e) {
      assert true;
    }
    //TEST_END

    //TEST
    try {
      optimizer.addNode(null);
      assert false;
    } catch (IllegalArgumentException e) {
      assert true;
    }
    //TEST_END

    //TEST
    try {
      optimizer.addLink("", "B", 100);
      assert false;
    } catch (IllegalArgumentException e) {
      assert true;
    }
    //TEST_END

    //TEST
    try {
      optimizer.addLink("A", null, 100);
      assert false;
    } catch (IllegalArgumentException e) {
      assert true;
    }
    //TEST_END

    //TEST
    try {
      optimizer.addLink("A", "B", -4);
      assert false;
    } catch (IllegalArgumentException e) {
      assert true;
    }
    //TEST_END

    //TEST
    try {
      optimizer.routePacket("", "B", 100);
      assert false;
    } catch (IllegalArgumentException e) {
      assert true;
    }
    //TEST_END

    //TEST
    try {
      optimizer.routePacket("A", null, 100);
      assert false;
    } catch (IllegalArgumentException e) {
      assert true;
    }
    //TEST_END

    //TEST
    try {
      optimizer.routePacket("A", "B", -4);
      assert false;
    } catch (IllegalArgumentException e) {
      assert true;
    }
    //TEST_END
  }

  // Test behavior under capacity limits
  private static void testCapacityLimits() {
    NetworkTrafficOptimizer optimizer = new NetworkTrafficOptimizer();
    optimizer.addNode("A");
    optimizer.addNode("B");
    optimizer.addLink("A", "B", 50);

    optimizer.routePacket("A", "B", 30);
    optimizer.routePacket("A", "B", 30);

    //TEST
    Map<String, Integer> load = optimizer.getCurrentLoadOnLinks();
    assert load.get("A->B") <= 50;
    //TEST_END
  }

  // Test that the shortest path is chosen for routing
  private static void testShortestPathRouting() {
    NetworkTrafficOptimizer optimizer = new NetworkTrafficOptimizer();
    optimizer.addNode("A");
    optimizer.addNode("B");
    optimizer.addNode("C");
    optimizer.addLink("A", "B", 100);
    optimizer.addLink("B", "C", 100);
    optimizer.addLink("A", "C", 50);

    optimizer.routePacket("A", "C", 25);

    //TEST
    Map<String, Integer> load = optimizer.getCurrentLoadOnLinks();
    assert load.get("A->C") == 25 : "Failed: Packet should take the direct shorter route A->C";
    System.out.println("Test Passed: Shortest path routing.");
    //TEST_END
  }
}
