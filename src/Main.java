
import com.powem.inv.strings.StringToInteger;

public class Main {
  public static void main(String[] args) {
    // TEST
    assert StringToInteger.extractIntegerFromUserInput("   ") == 0;
    // TEST_END

    // TEST
    assert StringToInteger.extractIntegerFromUserInput(null) == 0;
    // TEST_END

    // TEST
    assert StringToInteger.extractIntegerFromUserInput("   -23") == -23;
    // TEST_END

    // TEST
    assert StringToInteger.extractIntegerFromUserInput("some text 2") == 0;
    // TEST_END

    // TEST
    assert StringToInteger.extractIntegerFromUserInput("2 some text") == 2;
    // TEST_END

    // TEST
    assert StringToInteger.extractIntegerFromUserInput("2 some text 2") == 2;
    // TEST_END

    // TEST
    assert StringToInteger.extractIntegerFromUserInput("-891283472332") == -2147483648;
    // TEST_END
  }
}
