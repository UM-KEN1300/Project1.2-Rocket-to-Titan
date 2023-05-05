package helperFunction;

import Model.PlanetObject;

/**
 * HelperFunctions class contains utility methods for operations
 * related to distance calculation, vector addition, subtraction,
 * and string-to-vector conversion.
 */
public class HelperFunctions {

    public HelperFunctions() {
    }

    /**
     * Calculate the distance between two PlanetObjects.
     *
     * @param one The first PlanetObject.
     * @param two The second PlanetObject.
     * @return The distance between the two PlanetObjects.
     */
    public static double getDistanceBetween(PlanetObject one, PlanetObject two) {
        return Math.sqrt(
                Math.pow((two.getPositionalVector()[0] - one.getPositionalVector()[0]), 2)
                        + Math.pow((two.getPositionalVector()[1] - one.getPositionalVector()[1]), 2)
                        + Math.pow((two.getPositionalVector()[2] - one.getPositionalVector()[2]), 2)
        );
    }

    /**
     * Calculate the distance between two positional vectors.
     *
     * @param one The first positional vector.
     * @param two The second positional vector.
     * @return The distance between the two vectors.
     */
    public static double getDistanceBetweenWithVectors(double[] one, double[] two) {
        return Math.sqrt(
                Math.pow((two[0] - one[0]), 2)
                        + Math.pow((two[1] - one[1]), 2)
                        + Math.pow((two[2] - one[2]), 2)
        );
    }

    /**
     * Subtract two vectors.
     *
     * @param vectorOne The first vector.
     * @param vectorTwo The second vector.
     * @return The resulting vector after subtraction.
     */
    public static double[] subtract(double[] vectorOne, double[] vectorTwo) {
        double[] returnable = new double[3];
        returnable[0] = vectorOne[0] - vectorTwo[0];
        returnable[1] = vectorOne[1] - vectorTwo[1];
        returnable[2] = vectorOne[2] - vectorTwo[2];
        return returnable;
    }

    /**
     * Add two vectors.
     *
     * @param vectorOne The first vector.
     * @param vectorTwo The second vector.
     * @return The resulting vector after addition.
     */
    public static double[] addition(double[] vectorOne, double[] vectorTwo) {
        double[] returnable = new double[3];
        returnable[0] = vectorOne[0] + vectorTwo[0];
        returnable[1] = vectorOne[1] + vectorTwo[1];
        returnable[2] = vectorOne[2] + vectorTwo[2];
        return returnable;
    }

    /**
     * Calculate the magnitude of a vector.
     *
     * @param vector The input vector.
     * @return The magnitude of the vector.
     */
    public double getVectorMagnitude(double[] vector) {
        return Math.sqrt(vector[0] * vector[0] + vector[1] * vector[1] + vector[2] * vector[2]);
    }

    /**
     * Convert a string representation of a vector to a double array.
     *
     * @param input The input string.
     * @return The vector as a double array.
     */
    public static double[] stringToVector(String input) {
        input = input.replaceAll("[XVYZ=]", "");
        String[] str = input.trim().split("\\s+");

        double[] returnable = new double[3];
        int counter = 0;
        for (String s : str) {
            if (!s.isEmpty()) {
                returnable[counter] = Double.parseDouble(s);
                counter++;
            }
        }

        return returnable;
    }
}
