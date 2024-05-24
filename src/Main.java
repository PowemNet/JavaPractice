import com.powem.inv.strings.StringDecoder;
import java.util.List;

public class Main {
  public static void main(String[] args) {
    // TEST
    assert StringDecoder.decode("5#Hello5#World").equals(List.of("Hello", "World"));
    // TEST_END

    // TEST
    assert StringDecoder.decode("1#H5#World").equals(List.of("H", "World"));
    // TEST_END

    // TEST
    try {
      StringDecoder.decode("1#Hello5#World");
      assert false;
    } catch (IllegalArgumentException e) {
      assert true;
    }
    // TEST_END

    // TEST
    try {
      StringDecoder.decode("1Hello5");
      assert false;
    } catch (IllegalArgumentException e) {
      assert true;
    }
    // TEST_END

    // TEST
    try {
      StringDecoder.decode("1#Hello5");
      assert false;
    } catch (IllegalArgumentException e) {
      assert true;
    }
    // TEST_END

    // TEST
    try {
      StringDecoder.decode("1#Hello5");
      assert false;
    } catch (IllegalArgumentException e) {
      assert true;
    }
    // TEST_END

    // TEST
    try {
      StringDecoder.decode("#5Hell");
      assert false;
    } catch (IllegalArgumentException e) {
      assert true;
    }
    // TEST_END

    // TEST
    try {
      StringDecoder.decode(null);
      assert false;
    } catch (IllegalArgumentException e) {
      assert true;
    }
    // TEST_END

    // TEST
    try {
      StringDecoder.decode("");
      assert false;
    } catch (IllegalArgumentException e) {
      assert true;
    }
    // TEST_END
  }
}