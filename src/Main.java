
import com.powem.inv.trees.TransactionsBSTBalancer;

public class Main {
  public static void main(String[] args) {
    // TEST
    TransactionsBSTBalancer balancer = new TransactionsBSTBalancer();
    TransactionsBSTBalancer.TreeNode root = balancer.createTreeNode(5);
    root.right = balancer.createTreeNode(10);
    root.right.right = balancer.createTreeNode(15);
    root.right.right.right = balancer.createTreeNode(20);

    TransactionsBSTBalancer.TreeNode result1 = balancer.balanceBST(root);
    assert result1.val == 10;
    // TEST_END

    // TEST
    assert result1.left.val == 5;
    // TEST_END

    // TEST
    assert result1.right.val == 15;
    // TEST_END

    // TEST
    assert result1.right.right.val == 20;
    // TEST_END

    // TEST
    assert balancer.balanceBST(null) == null;
    // TEST_END

    // TEST
    try {
      TransactionsBSTBalancer.TreeNode root2 = balancer.createTreeNode(-5);
      assert false;
    } catch (IllegalArgumentException e) {
      assert true;
    }
    // TEST_END
  }
}
