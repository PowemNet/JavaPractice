package com.powem.inv.algos;
//Image Steganography process for decoding and encoding messages
//Create a Java class called "ImageSteganography" that implements basic image steganography
// techniques for hiding text messages within an image. The class should have the following methods:
//
//encodeMessage(String message, BufferedImage image): Encodes the given text message into the least
// significant bits of the image pixels. The method should modify the input image to store the encoded message.
//
//decodeMessage(BufferedImage image): Decodes the hidden message from the least significant bits of the
// image pixels. The method should return the decoded text message as a string.


import java.awt.image.BufferedImage;

public class ImageSteganography {
    private static final int BITS_PER_BYTE = 8;

    public void encodeMessage(String message, BufferedImage image) {
        validateMessage(message);
        validateImage(image);

        int width = image.getWidth();
        int height = image.getHeight();
        int messageLength = message.length();

        if (messageLength * BITS_PER_BYTE > width * height * 3) {
            throw new IllegalArgumentException("Message too large to encode in the image");
        }

        int[] pixels = image.getRGB(0, 0, width, height, null, 0, width);

        int bitIndex = 0;
        for (int i = 0; i < messageLength; i++) {
            char c = message.charAt(i);
            for (int j = 0; j < BITS_PER_BYTE; j++) {
                int pixelIndex = bitIndex / 3;
                int component = bitIndex % 3;
                int pixel = pixels[pixelIndex];
                int bit = (c >> j) & 1;

                if (component == 0) {
                    pixel = (pixel & 0xFFFFFFFE) | bit;
                } else if (component == 1) {
                    pixel = (pixel & 0xFFFFFFFD) | (bit << 1);
                } else {
                    pixel = (pixel & 0xFFFFFFFB) | (bit << 2);
                }

                pixels[pixelIndex] = pixel;
                bitIndex++;
            }
        }

        pixels[bitIndex / 3] = (pixels[bitIndex / 3] & 0xFFFFFF00);
        image.setRGB(0, 0, width, height, pixels, 0, width);
    }

    public String decodeMessage(BufferedImage image) {
        validateImage(image);

        int width = image.getWidth();
        int height = image.getHeight();
        int[] pixels = image.getRGB(0, 0, width, height, null, 0, width);

        StringBuilder message = new StringBuilder();
        int bitIndex = 0;
        while (true) {
            int charCode = 0;
            for (int i = 0; i < BITS_PER_BYTE; i++) {
                int pixelIndex = bitIndex / 3;
                int component = bitIndex % 3;
                int pixel = pixels[pixelIndex];
                int bit = 0;

                if (component == 0) {
                    bit = pixel & 1;
                } else if (component == 1) {
                    bit = (pixel >> 1) & 1;
                } else {
                    bit = (pixel >> 2) & 1;
                }

                charCode |= bit << i;
                bitIndex++;

                if (bitIndex / 3 >= width * height) {
                    break;
                }
            }

            if (charCode == 0) {
                break;
            }
            message.append((char) charCode);
        }

        return message.toString();
    }

    private void validateMessage(String message) {
        if (message == null || message.isEmpty()) {
            throw new IllegalArgumentException("Message cannot be null or empty");
        }
    }

    private void validateImage(BufferedImage image) {
        if (image == null) {
            throw new IllegalArgumentException("Image cannot be null");
        }
    }
}

