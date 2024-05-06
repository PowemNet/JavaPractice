
import com.powem.inv.strings.StringArrayLongestCommonPrefix;
import com.powem.inv.strings.WordsReverser;

public class Main {
  public static void main(String[] args) {
    // TEST
    assert StringArrayLongestCommonPrefix.longestCommonPrefix(new String[]{"magnesium", "maginificent", "maggot"}).equals("mag");
    // TEST_END

    // TEST
    assert StringArrayLongestCommonPrefix.longestCommonPrefix(new String[]{"dog", "cow", "car"}).equals("");
    // TEST_END

    // TEST
    assert StringArrayLongestCommonPrefix.longestCommonPrefix(new String[]{""}).equals("");
    // TEST_END

    // TEST
    try {
      StringArrayLongestCommonPrefix.longestCommonPrefix(null);
      assert false;
    } catch (IllegalArgumentException e) {
      assert true;
    }
    // TEST_END
  }
}
