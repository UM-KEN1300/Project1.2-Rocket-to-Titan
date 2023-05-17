import code.model.objects.PlanetObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Test class for the PlanetObject class. It specifically tests the accelerationBetween method
 * to ensure proper calculations and handling of edge cases.
 */
public class PlanetObjectTest {
    private PlanetObject planetObject1;
    private PlanetObject planetObject2;
    private PlanetObject planetObject3;

    @BeforeEach
    public void setUp() {
        double[] coordinates1 = {0, 0, 0};
        double[] velocity1 = {0, 0, 0};
        long mass1 = 10;

        double[] coordinates2 = {1, 1, 1};
        double[] velocity2 = {0, 0, 0};
        long mass2 = 20;

        double[] coordinates3 = {0, 0, 0};
        double[] velocity3 = {0, 0, 0};
        long mass3 = 30;

        planetObject1 = new PlanetObject(coordinates1, velocity1);
        planetObject1.setMass(mass1);
        planetObject2 = new PlanetObject(coordinates2, velocity2);
        planetObject2.setMass(mass2);
        planetObject3 = new PlanetObject(coordinates3, velocity3);
        planetObject3.setMass(mass3);
    }

    @Test
    public void testAccelerationBetween() {
        double[] expectedAcceleration = {-0.000667406, -0.000667406, -0.000667406};
        double[] acceleration = planetObject1.accelerationBetween(planetObject2);
        assertArrayEquals(expectedAcceleration, acceleration, 0.000001, "The calculated acceleration is incorrect");
    }

    @Test
    public void testAccelerationBetweenSameLocation() {
        // Expect an IllegalArgumentException when the two celestial bodies are at the same location
        assertThrows(IllegalArgumentException.class, () -> planetObject1.accelerationBetween(planetObject3),
                "Expected IllegalArgumentException when the two celestial bodies are at the same location");
    }
}
