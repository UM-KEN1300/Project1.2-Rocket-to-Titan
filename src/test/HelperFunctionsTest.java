import helperFunction.HelperFunctions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Tests methods in helperFunction.HelperFunctions
 */
class HelperFunctionsTest {
    @Test
    void testGetDistanceBetweenWithVectors() {
        double[] one = new double[]{0, 0, 0};
        double[] two = new double[]{3, 4, 0};

        double distance = HelperFunctions.getDistanceBetweenWithVectors(one, two);
        assertEquals(5, distance, "The distance between the two vectors should be 5");
    }

    @Test
    void testGetDistanceBetweenWithVectors_EdgeCase() {
        double[] one = new double[]{Double.MAX_VALUE, 0, 0};
        double[] two = new double[]{-Double.MAX_VALUE, 0, 0};

        double distance = HelperFunctions.getDistanceBetweenWithVectors(one, two);
        assertEquals(Double.POSITIVE_INFINITY, distance, "The distance between the two vectors should be infinite");
    }

    @Test
    void testAddition() {
        double[] vectorOne = new double[]{5, 7, 9};
        double[] vectorTwo = new double[]{2, 3, 4};

        double[] result = HelperFunctions.addition(vectorOne, vectorTwo);
        assertArrayEquals(new double[]{7, 10, 13}, result, "The resulting vector after addition should be {7, 10, 13}");
    }

    @Test
    void testStringToVector() {
        String input = "X = 1.0 Y = 2.0 Z = 3.0";
        double[] result = HelperFunctions.stringToVector(input);
        assertArrayEquals(new double[]{1, 2, 3}, result, "The resulting vector should be {1, 2, 3}");
    }

    @Test
    void testStringToVector_EdgeCase() {
        String input = "X = " + Double.MAX_VALUE + " Y = " + (-Double.MAX_VALUE) + " Z = 0";
        double[] result = HelperFunctions.stringToVector(input);
        assertArrayEquals(new double[]{Double.MAX_VALUE, -Double.MAX_VALUE, 0}, result, "The resulting vector should be {Double.MAX_VALUE, -Double.MAX_VALUE, 0}");
    }
}
