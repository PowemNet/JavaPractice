package com.powem.inv.trees;

//Balancing a BST

//A financial software system stores transaction data in a binary search tree (BST)
//    where each node represents a transaction amount. Over time, the BST may
//    become unbalanced due to frequent additions and deletions, leading to
//    inefficient operations. Create a method that re-balances the tree
//    to ensure optimal operation times for searches, insertions, and deletions.
//
// Instructions:
//    Implement a function balanceBST(TreeNode root) that takes the root of a
//    potentially unbalanced BST and returns the root of a balanced BST.
//    Do not create new nodes; you should rearrange the existing nodes.
//    Assume the BST does not contain duplicate values.
// Example:
//    where left node of i =  2i+1
//      and right node of i  = 2i + 2
//    Input:
//    BST (unbalanced): [5, null, 10, null, null, null, 15, null, null, null, null, null, null, null, 20]
//    5
//    \
//      10
//        \
//        15
//          \
//            20
//
//    Output:
//    BST (balanced): [10, 5, 15, null, null, null, 20]
//      10
//    /  \
//   5    15
//            \
//              20
// Constraints: The values in the nodes cannot be negative.


import java.util.ArrayList;
import java.util.List;

public class TransactionsBSTBalancer {

  public class TreeNode {

    public int val;
    public TreeNode left;
    public TreeNode right;

    TreeNode(int x) {
      val = x;
    }
  }

  public TreeNode createTreeNode(int x) {
    if (x < 0) {
      throw new IllegalArgumentException("Node value cannot be negative");
    }
    return new TreeNode(x);
  }

  public TreeNode balanceBST(TreeNode root) {
    if (root == null) {
      return null;
    }
    List<TreeNode> nodes = new ArrayList<>();
    inorderTraversal(root, nodes);
    return buildBalancedBST(nodes, 0, nodes.size() - 1);
  }

  private void inorderTraversal(TreeNode node, List<TreeNode> nodes) {
    if (node != null) {
      inorderTraversal(node.left, nodes);
      nodes.add(node);
      inorderTraversal(node.right, nodes);
    }
  }

  private TreeNode buildBalancedBST(List<TreeNode> nodes, int start, int end) {
    if (start > end) {
      return null;
    }
    int mid = (start + end) / 2;
    TreeNode node = nodes.get(mid);
    node.left = buildBalancedBST(nodes, start, mid - 1);
    node.right = buildBalancedBST(nodes, mid + 1, end);
    return node;
  }
}

//public static void tests() {
//// TEST
//TransactionsBSTBalancer balancer = new TransactionsBSTBalancer();
//TransactionsBSTBalancer.TreeNode root = balancer.createTreeNode(5);
//root.right = balancer.createTreeNode(10);
//root.right.right = balancer.createTreeNode(15);
//root.right.right.right = balancer.createTreeNode(20);
//
//TransactionsBSTBalancer.TreeNode result1 = balancer.balanceBST(root);
//    assert result1.val == 10;
//    // TEST_END
//
//    // TEST
//    assert result1.left.val == 5;
//    // TEST_END
//
//    // TEST
//    assert result1.right.val == 15;
//    // TEST_END
//
//    // TEST
//    assert result1.right.right.val == 20;
//    // TEST_END
//
//    // TEST
//    assert balancer.balanceBST(null) == null;
//    // TEST_END
//
//    // TEST
//    try {
//TransactionsBSTBalancer.TreeNode root2 = balancer.createTreeNode(-5);
//      assert false;
//          } catch (IllegalArgumentException e) {
//    assert true;
//    }
//// TEST_END
//}
