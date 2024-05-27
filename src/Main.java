import com.powem.inv.algos.ImageSteganography;
import java.awt.image.BufferedImage;

public class Main {
    public static void main(String[] args) {
        ImageSteganography steganography = new ImageSteganography();

        // TEST
        BufferedImage image = createSampleImage(100, 100);
        String message = "Hello, World!";
        steganography.encodeMessage(message, image);
        String decodedMessage = steganography.decodeMessage(image);
        assert decodedMessage.equals(message);
        // TEST_END

        // TEST
        BufferedImage image2 = createSampleImage(200, 200);
        String longMessage = "This is a long message that will be encoded in the image.";
        steganography.encodeMessage(longMessage, image2);
        String decodedLongMessage = steganography.decodeMessage(image2);
        assert decodedLongMessage.equals(longMessage);
        // TEST_END

        // TEST
        try {
            steganography.encodeMessage(null, image);
            assert false;
        } catch (IllegalArgumentException e) {
            assert true;
        }
        // TEST_END

        // TEST
        try {
            steganography.encodeMessage(message, null);
            assert false;
        } catch (IllegalArgumentException e) {
            assert true;
        }
        // TEST_END

        // TEST
        try {
            steganography.decodeMessage(null);
            assert false;
        } catch (IllegalArgumentException e) {
            assert true;
        }
        // TEST_END

        // TEST
        BufferedImage smallImage = createSampleImage(10, 10);
        try {
            steganography.encodeMessage(longMessage, smallImage);
            assert false;
        } catch (IllegalArgumentException e) {
            assert true;
        }
        // TEST_END
    }

    private static BufferedImage createSampleImage(int width, int height) {
        return new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
    }
}