package com.powem.inv.binarytree;
//StockSpan
//original[medium]: https://leetcode.com/problems/binary-search-tree-iterator/description/

// Create an Iterator class called BSTInOrderIterator which can be used to iterate over a Binary search tree(BST).
// The class should have methods next() and hasNext(). next() returns the next value in the BST
// if there's any, else it returns an index out of bounds exception.
// hasNext() returns true if the tree still has an item in the next iteration.
// The approach should be to first flatten the BST so that it can be represented in an array
// which can easily be iterated over with a pointer.
//for example, if the class BSTInOrderIterator is initiated with an Integer array: [8, 3, 11, null, null, 9, 10] which
//represents a BST using TreeNodes,
//then next() calls would yield 3, then 8, then 9, then 11, then 10.


import java.util.ArrayList;

public class BSTInOrderIterator {

  ArrayList<Integer> nodesSorted;
  int index;

  public BSTInOrderIterator(Integer[] items) {
    TreeNode root = createTreeNode(items);
    this.nodesSorted = new ArrayList<Integer>();
    this.index = -1;
    this.flatten(root);
  }

  private void flatten(TreeNode root) {
    if (root == null) {
      return;
    }

    this.flatten(root.left);
    this.nodesSorted.add(root.val);
    this.flatten(root.right);
  }

  public int next() {
    return this.nodesSorted.get(++this.index);
  }

  public boolean hasNext() {
    return this.index + 1 < this.nodesSorted.size();
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
// TEST
//BSTInOrderIterator bSTInOrderIterator1 = new BSTInOrderIterator(null);
//    assert !bSTInOrderIterator1.hasNext();
//// TEST_END
//
////     TEST
//BSTInOrderIterator bSTInOrderIterator2 = new BSTInOrderIterator(new Integer[]{null});
//    assert !bSTInOrderIterator2.hasNext();
//// TEST_END
//
//// TEST
//BSTInOrderIterator bSTInOrderIterator3 = new BSTInOrderIterator(new Integer[]{8, 3, 11, null, null, 9, 10});
//    assert bSTInOrderIterator3.next() == 3;
//    assert bSTInOrderIterator3.next() == 8;
//    assert bSTInOrderIterator3.hasNext();
//    assert bSTInOrderIterator3.next() == 9;
//    assert bSTInOrderIterator3.hasNext();
//    assert bSTInOrderIterator3.next() == 11;
//    assert bSTInOrderIterator3.hasNext();
//    assert bSTInOrderIterator3.next() == 10;
//    assert !bSTInOrderIterator3.hasNext();
//    try {
//        bSTInOrderIterator3.next();
//      assert false;
//          } catch (IndexOutOfBoundsException e) {
//    assert e.getMessage().contains("Index 5 out of bounds for length 5");
//    }
//        // TEST_END
//        }
//}

