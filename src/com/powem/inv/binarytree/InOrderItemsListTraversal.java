package com.powem.inv.binarytree;
//original[easy]: https://leetcode.com/problems/binary-tree-inorder-traversal/

// List items of a tree of items, such as this car tree below:
//
//            Car(4)
//     /             \
//    Tesla(2)         Toyota(6)
//   /      \              /        \
//  modelX(1) modelY(3)  Rav4(5)   Prius(7)
// The result should start from bottom and go in order to the top, then back down to the bottom
// right. For this example, it should look like: [modelX, Tesla, modelY, Car, Rav4, Toyota, Prius].
// for simplicty, these string can be represented as ints: [4, 2, 6, 1, 3, 5, 7]
// create a function called listItems which takes an array int array of elements like [4, 2, 6, 1, 3, 5, 7]
// and returns the sorted list from bottom to top, like [1, 2, 3, 4, 5, 6, 7]
// Also create a class named TreeNode to process nodes used.


import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class InOrderItemsListTraversal {

  public static List<Integer> listItems(Integer[] items) {
    if (items == null || items.length == 0) {
      throw new IllegalArgumentException("Items must not be null or empty");
    }

    TreeNode root = createTreeNode(items);
    List<Integer> result = new ArrayList<>();
    Stack<TreeNode> stack = new Stack<>();
    TreeNode current = root;
    while (current != null || !stack.isEmpty()) {
      while (current != null) {
        stack.push(current);
        current = current.left;
      }
      current = stack.pop();
      result.add(current.val);
      current = current.right;
    }
    return result;
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

//public static void tests() {
//  // TEST
//  try {
//    InOrderItemsListTraversal.listItems(null);
//    assert false;
//  } catch (IllegalArgumentException e) {
//    assert e.getMessage().equals("Items must not be null or empty");
//  }
//  // TEST_END
//
//  // TEST
//  try {
//    InOrderItemsListTraversal.listItems(new Integer[]{});
//    assert false;
//  } catch (IllegalArgumentException e) {
//    assert e.getMessage().equals("Items must not be null or empty");
//  }
//  // TEST_END
//
//  // TEST
//  assert InOrderItemsListTraversal.listItems(new Integer[]{1}).equals(List.of(1));
//  // TEST_END
//
//  // TEST
//  List<Integer> result = InOrderItemsListTraversal.listItems(new Integer[]{4, 2, 6, 1, 3, 5, 7});
//  assert result.equals(List.of(1, 2, 3, 4, 5, 6, 7));
//  // TEST_END
//}

