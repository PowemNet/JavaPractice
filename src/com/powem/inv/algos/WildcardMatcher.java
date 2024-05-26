package com.powem.inv.algos;

import java.util.Deque;
import java.util.LinkedList;

//        Problem: Wildcard String Matching
//        Problem Statement:
//        Create a function to match a string against a pattern that includes wildcards. Specifically, the wildcard
//        character '*' can match any sequence of characters (including an empty sequence), and the wildcard
//        character '?' can match any single character.
//
//        Develop a function that checks if a string matches a pattern given these wildcard rules. The function
//        should handle multiple consecutive wildcards efficiently and should match the entire string to the pattern.
////
//        The pattern can include the characters '?' and '*', where:
//        '?' matches any single character.
//        '*' matches any sequence of characters (including an empty sequence).
//        The matching should cover the entire input string, not just parts of it.
//
//        Implement the isMatch function to determine if the string matches the given pattern under the specified
//        wildcard rules.
//
//        Function Signature Suggestion:
//        public class WildcardMatcher {
//          public static boolean isMatch(String s, String p) {
//          }
//        }


public class WildcardMatcher {
    public static boolean isMatch(String s, String p) {
        int sLen = s.length(), pLen = p.length();
        int sIdx = 0, pIdx = 0, starIdx = -1, sTmpIdx = -1;

        while (sIdx < sLen) {
            if (pIdx < pLen && (p.charAt(pIdx) == '?' || p.charAt(pIdx) == s.charAt(sIdx))) {
                ++sIdx;
                ++pIdx;
            }
            else if (pIdx < pLen && p.charAt(pIdx) == '*') {
                starIdx = pIdx;
                sTmpIdx = sIdx;
                ++pIdx;
            }
            else if (starIdx != -1) {
                pIdx = starIdx + 1;
                sIdx = sTmpIdx + 1;
                sTmpIdx = sIdx;
            }
            else return false;
        }

        while (pIdx < pLen && p.charAt(pIdx) == '*') {
            ++pIdx;
        }

        return pIdx == pLen;
    }
}


//import com.powem.inv.algos.WildcardMatcher;
//
//public class Main {
//    public static void main(String[] args) {
//        //TEST
//        assert WildcardMatcher.isMatch("abefcdgiescdfimde", "ab*cd?i*de");
//        //TEST END
//
//        //TEST
//        assert WildcardMatcher.isMatch("aaaa", "***a");
//        //TEST END
//
//        //TEST
//        assert !WildcardMatcher.isMatch("abcd", "abc*c?d") : "Test failed: 'abcd' should not match 'abc*c?d'";
//        //TEST END
//    }
//}

