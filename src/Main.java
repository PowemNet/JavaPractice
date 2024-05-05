
import com.powem.inv.arrays.ArrayRotatorForShifts;
import com.powem.inv.arrays.MissingNumberFinder;
import com.powem.inv.dynamicprogramming.NumbersInSeries;
import com.powem.inv.hashtables.TransfersFrequencyCount;
import com.powem.inv.linkedlist.DuplicateEventRemoval;
import com.powem.inv.linkedlist.EventPrioritySort;
import com.powem.inv.linkedlist.TransactionsMergeSortedList;
import com.powem.inv.stacks.MaximumNestingDepth;
import com.powem.inv.strings.Palindrome;
import com.powem.inv.trees.TransactionsBSTBalancer;
import java.util.HashMap;
import java.util.List;

public class Main {
  public static void main(String[] args) {
    // TEST
    assert NumbersInSeries.findNextValueInSequence(0) == 0;
    // TEST_END

    // TEST
    assert NumbersInSeries.findNextValueInSequence(1) == 1;
    // TEST_END

    // TEST
    assert NumbersInSeries.findNextValueInSequence(2) == 1;
    // TEST_END

    // TEST
    assert NumbersInSeries.findNextValueInSequence(3) == 2;
    // TEST_END

    // TEST
    assert NumbersInSeries.findNextValueInSequence(4) == 3;
    // TEST_END

    // TEST
    try {
      NumbersInSeries.findNextValueInSequence(-18);
      assert false;
    } catch (IllegalArgumentException e) {
      assert true;
    }
    // TEST_END
  }
}
