package com.powem.inv.binarytree;
//PathSum
//original[easy]: https://leetcode.com/problems/path-sum/description/

//An array of transaction amounts is represented as a binary tree.
//create a function called pathWithSumExists which returns true if
// there's a path in the tree which when summed up, which produces the sum in question.
// For example for array items = [400, 200, 600, 100, 300, 500, 700], and sum = 700,
// the function should return true because
//there a exist a path whose sum is 700. The path is 400 + 200 + 100
//
//Representing the tree from the input array:
//Parent-Child Relationship:
//    For any element at index i in the array (with i starting at 0 for the root):
//    The left child of the element is found at index 2i + 1 (if it exists).
//    The right child of the element is found at index 2i + 2 (if it exists).
//
//Node Placement in Tree:
//    The first element of the array becomes the root of the tree.
//    Subsequent elements are placed into the tree level by level, ensuring that each level of the tree is filled from left to right before moving to the next level.
//
//
//Example Illustration:
//    Given the array [400, 200, 600, 100, 300, 500, 700]:
//
//    The element at index 0 (400) is the root of the tree.
//    The element at index 1 (200) is the left child of the root (since 2*0 + 1 = 1).
//    The element at index 2 (600) is the right child of the root (since 2*0 + 2 = 2).
//    Similarly, the element at index 3 (100) is the left child of the node 200 (since 2*1 + 1 = 3), and so on for the rest of the elements.


import java.util.LinkedList;

public class AmountSumPath {

  public static boolean pathWithSumExists(Integer[] items, int sum) {
    if (items == null || items.length == 0) {
      throw new IllegalArgumentException("Items must not be null or empty");
    }

    TreeNode root = createTreeNode(items);

    LinkedList<TreeNode> nodeStack = new LinkedList<>();
    LinkedList<Integer> sumStack = new LinkedList<>();
    nodeStack.add(root);
    sumStack.add(sum - root.val);

    TreeNode node;
    int currSum;
    while (!nodeStack.isEmpty()) {
      node = nodeStack.pollLast();
      currSum = sumStack.pollLast();
      if ((node.right == null) && (node.left == null) && (currSum == 0)) {
        return true;
      }

      if (node.right != null) {
        nodeStack.add(node.right);
        sumStack.add(currSum - node.right.val);
      }
      if (node.left != null) {
        nodeStack.add(node.left);
        sumStack.add(currSum - node.left.val);
      }
    }
    return false;
  }


  private static TreeNode createTreeNode(Integer[] elements) {
    if (elements.length == 0) {
      return null;
    }
    TreeNode root = new TreeNode(elements[0]);
    TreeNode[] nodes = new TreeNode[elements.length];
    nodes[0] = root;
    for (int i = 1; i < elements.length; i++) {
      if (elements[i] == null) {
        continue;
      }
      TreeNode node = new TreeNode(elements[i]);
      nodes[i] = node;
      if (i % 2 == 1) {
        nodes[(i - 1) / 2].left = node;
      } else {
        nodes[(i - 2) / 2].right = node;
      }
    }
    return root;
  }
}

class TreeNode {

  int val;
  TreeNode left;
  TreeNode right;

  public TreeNode(int val) {
    this.val = val;
    this.left = null;
    this.right = null;
  }
}

//public static void tests() {
//// TEST
//    try {
//        AmountSumPath.pathWithSumExists(null, 1);
//      assert false;
//          } catch (IllegalArgumentException e) {
//    assert true;
//    }
//    // TEST_END
//
//    // TEST
//    try {
//    AmountSumPath.pathWithSumExists(new Integer[]{}, 1);
//    assert false;
//    } catch (IllegalArgumentException e) {
//    assert true;
//    }
//    // TEST_END
//
//    // TEST
//    assert AmountSumPath.pathWithSumExists(new Integer[]{100}, 100);
//    // TEST_END
//
//    // TEST
//    assert AmountSumPath.pathWithSumExists(new Integer[]{4, 2, 6, 1, 3, 5}, 7);
//    // TEST_END
//
//    // TEST
//    assert AmountSumPath.pathWithSumExists(new Integer[]{400, 200, 600, 100, 300, 500, 700}, 700);
//// TEST_END
//}