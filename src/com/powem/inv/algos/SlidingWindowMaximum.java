package com.powem.inv.algos;

import java.util.Deque;
import java.util.LinkedList;

//    Problem: Sliding Window Maximum
//    Problem Statement:
//    Implement a solution that finds the maximum value in each sliding window of a given size as it moves from left
//    to right through an array. You should only return the maximums for the windows that are fully within the bounds
//    of the array.
//
//    Create a class or function that efficiently calculates and returns a list of the maximum values in each sliding window across the provided array.
//
//    Input will be an array of integers and a window size k.
//    The window should slide one element at a time from the start of the array to the end.
//    For each position of the window, capture the maximum value within that window.
//    Challenge:
//    Implement this functionality in a way that optimizes for performance, ideally achieving a linear time complexity relative to the size of the input array.
//
//    Function Signature Suggestion:
//    public class SlidingWindowMaximum {
//      public static int[] maxSlidingWindow(int[] nums, int k) {
//      }
//   }


public class SlidingWindowMaximum {
    public static int[] maxSlidingWindow(int[] nums, int k) {
        if (nums == null || nums.length < k || k <= 0) {
            return new int[0];
        }

        Deque<Integer> deque = new LinkedList<>();
        int[] maxElements = new int[nums.length - k + 1];

        for (int i = 0; i < nums.length; i++) {
            while (!deque.isEmpty() && deque.peek() < i - k + 1) {
                deque.poll();
            }

            while (!deque.isEmpty() && nums[deque.peekLast()] < nums[i]) {
                deque.pollLast();
            }

            deque.offer(i);

            if (i >= k - 1) {
                maxElements[i - k + 1] = nums[deque.peek()];
            }
        }

        return maxElements;
    }
}

//import com.powem.inv.algos.SlidingWindowMaximum;
//
//import java.util.Arrays;

//public class Main {
//    public static void main(String[] args) {
//        int[] nums = {1, 3, -1, -3, 5, 3, 6, 7};
//        int k = 3;
//        int[] expected = {3, 3, 5, 5, 6, 7};
//
//        int[] result = SlidingWindowMaximum.maxSlidingWindow(nums, k);
//
//        //TEST
//        assert Arrays.equals(result, expected);
//        //TEST_END
//    }
//}
