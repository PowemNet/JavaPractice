package com.powem.inv.binarytree;
//StockSpan
//original[medium]: https://leetcode.com/problems/binary-search-tree-iterator/description/

// Create an Iterator class called BSTInOrderIteratorWithQueue which can be used to iterate over a Binary search tree(BST).
//Use the flatten technique to represent the BST in a flat structure which can easily be iterated over, such as a Queue.
// The class should have methods next() and hasNext(). next() returns the next value in the BST
// if there's any, else it returns an index out of bounds exception.
// hasNext() returns true if the tree still has an item in the next iteration.
// The approach should be to first flatten the BST so that it can be represented in an array
// which can easily be iterated over with a pointer.
//for example, if the class BSTInOrderIterator is initiated with an Integer array: [8, 3, 11, null, null, 9, 10] which
//represents a BST using TreeNodes,
//then next() calls would yield 3, then 8, then 9, then 11, then 10.


import java.util.LinkedList;
import java.util.Queue;

public class BSTInOrderIteratorWithQueue {

  Queue<Integer> nodes;

  public BSTInOrderIteratorWithQueue(Integer[] items) {
    TreeNode root = createTreeNode(items);
    this.nodes = new LinkedList<>();
    this.flatten(root);
  }

  private void flatten(TreeNode root) {
    if (root == null) {
      return;
    }

    this.flatten(root.left);
    this.nodes.add(root.val);
    this.flatten(root.right);
  }

  public int next() {
    if (this.nodes.isEmpty()) {
      throw new IllegalArgumentException("Queue is empty");
    }
    return this.nodes.poll();
  }

  public boolean hasNext() {
    return !this.nodes.isEmpty();
  }

  private static TreeNode createTreeNode(Integer[] elements) {
    if (elements == null || elements[0] == null || elements.length == 0) {
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

//class TreeNode {
//
//  int val;
//  TreeNode left;
//  TreeNode right;
//
//  public TreeNode(int val) {
//    this.val = val;
//    this.left = null;
//    this.right = null;
//  }
//}

//public static void tests() {
//// TEST
//BSTInOrderIteratorWithQueue bSTInOrderIteratorWithQueue = new BSTInOrderIteratorWithQueue(null);
//    assert !bSTInOrderIteratorWithQueue.hasNext();
//// TEST_END
//
//// TEST
//BSTInOrderIteratorWithQueue bSTInOrderIteratorWithQueue2 = new BSTInOrderIteratorWithQueue(new Integer[]{null});
//    assert !bSTInOrderIteratorWithQueue2.hasNext();
//// TEST_END
//
//// TEST
//BSTInOrderIteratorWithQueue bSTInOrderIteratorWithQueue3 = new BSTInOrderIteratorWithQueue(new Integer[]{8, 3, 11, null, null, 9, 10});
//    assert bSTInOrderIteratorWithQueue3.next() == 3;
//    assert bSTInOrderIteratorWithQueue3.next() == 8;
//    assert bSTInOrderIteratorWithQueue3.hasNext();
//    assert bSTInOrderIteratorWithQueue3.next() == 9;
//    assert bSTInOrderIteratorWithQueue3.hasNext();
//    assert bSTInOrderIteratorWithQueue3.next() == 11;
//    assert bSTInOrderIteratorWithQueue3.hasNext();
//    assert bSTInOrderIteratorWithQueue3.next() == 10;
//    assert !bSTInOrderIteratorWithQueue3.hasNext();
//    try {
//        bSTInOrderIteratorWithQueue3.next();
//      assert false;
//          } catch (IllegalArgumentException e) {
//    assert e.getMessage() == "Queue is empty";
//    }
//// TEST_END
//}

