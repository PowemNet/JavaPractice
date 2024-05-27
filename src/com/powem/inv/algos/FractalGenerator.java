package com.powem.inv.algos;
//Chaos Game
//Create a Java class called "FractalGenerator" that generates fractal patterns using the Chaos
// Game algorithm. The class should have the following methods:
//
//generateSierpinskiTriangle(int numPoints): Generates a Sierpinski Triangle fractal pattern with the specified
// number of points. The method should return a 2D array of doubles representing the x and y coordinates of the
// points in the fractal.

//generateBarnsleyFern(int numPoints): Generates a Barnsley Fern fractal pattern with the specified number of
// points. The method should return a 2D array of doubles representing the x and y coordinates of the points in
// the fractal.


import java.util.Random;

public class FractalGenerator {
    private static final double[][] SIERPINSKI_TRIANGLE_TRANSFORMS = {
        {0.5, 0.0, 0.0, 0.5, 0.0, 0.0},
        {0.5, 0.0, 0.0, 0.5, 0.5, 0.0},
        {0.5, 0.0, 0.0, 0.5, 0.25, 0.5}
    };

    private static final double[][] BARNSLEY_FERN_TRANSFORMS = {
        {0.85, 0.04, -0.04, 0.85, 0.0, 1.6},
        {-0.15, 0.28, 0.26, 0.24, 0.0, 0.44},
        {0.2, -0.26, 0.23, 0.22, 0.0, 1.6},
        {0.0, 0.0, 0.0, 0.16, 0.0, 0.0}
    };

    public double[][] generateSierpinskiTriangle(int numPoints) {
        validateNumPoints(numPoints);
        return generateFractal(numPoints, SIERPINSKI_TRIANGLE_TRANSFORMS);
    }

    public double[][] generateBarnsleyFern(int numPoints) {
        validateNumPoints(numPoints);
        return generateFractal(numPoints, BARNSLEY_FERN_TRANSFORMS);
    }

    private double[][] generateFractal(int numPoints, double[][] transforms) {
        double[][] points = new double[numPoints][2];
        double x = 0.0;
        double y = 0.0;

        Random random = new Random();
        for (int i = 0; i < numPoints; i++) {
            int transformIndex = getRandomTransformIndex(transforms, random);
            double[] transform = transforms[transformIndex];

            double nextX = transform[0] * x + transform[1] * y + transform[4];
            double nextY = transform[2] * x + transform[3] * y + transform[5];

            x = nextX;
            y = nextY;

            points[i][0] = x;
            points[i][1] = y;
        }

        return points;
    }

    private int getRandomTransformIndex(double[][] transforms, Random random) {
        double randomValue = random.nextDouble();
        double cumulativeProbability = 0.0;

        for (int i = 0; i < transforms.length; i++) {
            cumulativeProbability += getTransformProbability(i);
            if (randomValue <= cumulativeProbability) {
                return i;
            }
        }

        return transforms.length - 1;
    }

    private double getTransformProbability(int index) {
        return switch (index) {
            case 0 -> 0.33;
            case 1 -> 0.33;
            case 2 -> 0.34;
            case 3 -> 0.01;
            default -> throw new IllegalArgumentException("Invalid transform index");
        };
    }

    private void validateNumPoints(int numPoints) {
        if (numPoints <= 0) {
            throw new IllegalArgumentException("Number of points must be positive");
        }
    }
}
//TESTS --------
//
//import com.powem.inv.algos.FractalGenerator;
//
//public class Main {
//    public static void main(String[] args) {
//        FractalGenerator fractalGenerator = new FractalGenerator();
//
//        // TEST
//        int numPoints = 100000;
//        double[][] sierpinskiTriangle = fractalGenerator.generateSierpinskiTriangle(numPoints);
//        assert sierpinskiTriangle.length == numPoints;
//        // TEST_END
//
//        // TEST
//        assert sierpinskiTriangle[0].length == 2;
//        // TEST_END
//
//        // TEST
//        double[][] barnsleyFern = fractalGenerator.generateBarnsleyFern(numPoints);
//        assert barnsleyFern.length == numPoints;
//        // TEST_END
//
//        // TEST
//        assert barnsleyFern[0].length == 2;
//        // TEST_END
//
//        // TEST
//        try {
//            fractalGenerator.generateSierpinskiTriangle(0);
//            assert false;
//        } catch (IllegalArgumentException e) {
//            assert true;
//        }
//        // TEST_END
//
//        // TEST
//        try {
//            fractalGenerator.generateBarnsleyFern(-1);
//            assert false;
//        } catch (IllegalArgumentException e) {
//            assert true;
//        }
//        // TEST_END
//    }
//}

