package code.model;

import code.algorithms.functions.AccelerationFunction;
import code.algorithms.solvers.RungeKutta;
import code.algorithms.solvers.Solver;
import code.model.data.loaders.DataLoader;
import code.model.objects.PlanetObject;
import code.model.objects.Probe;
import code.utils.Time;

import java.util.*;
import java.util.function.BiFunction;

/**
 * Class representing the objects in the simulation. It holds the PlanetObject objects and probes and
 * is responsible for loading their properties.
 * <p>
 * It is a singleton to ensure that there is only one instance of every modeled object.</p>
 * <p>
 * It loads masses and radii of planets from xlsx files in resources.
 * It loads the initial coordinates and velocities depending on the data loader.</p>
 */
public class Model {
    private Map<String, PlanetObject> planetObjects;
    private List<Probe> probes;
    private final Time TIME;
    private static final AccelerationFunction ACCELERATION_FUNCTION = new AccelerationFunction();
    private final Solver SOLVER;


    private Model() {
        TIME = new Time(2023, 4, 1);
        SOLVER = new RungeKutta();
    }


    /**
     * Subclass holding the instance of the model.
     * Allows use of the singleton design without calling the instance each time.
     */
    private static final class InstanceHolder {
        private static final Model INSTANCE = new Model();
    }

    /**
     * Used inside this class to refer to the instance holding the objects.
     *
     * @return the Model instance
     */
    public static Model getInstance() {
        return InstanceHolder.INSTANCE;
    }

    /**
     * A call of this method performs a step in the chosen solver which calculates the next position of all bodies in the system.
     * It does so with the help of the mathematical solver chosen in the constructor.
     * The time step is chosen based on the getTimeStep method.
     */
    public static void step() {
        if (getProbes().size() > 0)
            getProbes().get(0).BoosterMECH(Model.getTime());
        double[] currentState = flattenState(getAllObjects());
        double[] nextState = getInstance().SOLVER.solve(getDynamicsFunction(), currentState, 0, getTimeStep());
        updateObjectsState(nextState);
        getTime().addSeconds(getTimeStep());
    }

    /**
     * Adds a Probe object to the model
     *
     * @param probe the Probe object to be added to the model
     */
    public static void addProbe(Probe probe) {
        getInstance().probes.add(probe);
    }

    /**
     * Takes in a one dimensional array of doubles whose values represent the states of bodies in the system and the spacecraft.
     * This is useful for reading the result of a solver step and applying the calculation to the model.
     *
     * @param systemState array of doubles representing the states of objects of the model
     */
    private static void updateObjectsState(double[] systemState) {
        ArrayList<PlanetObject> allObjects = getAllObjects();
        if (systemState.length != allObjects.size() * 6) {
            throw new IllegalArgumentException("Size of system state array does not match number of PlanetObjects.");
        }
        for (int i = 1; i < allObjects.size(); i++) {
            double[] newPosition = Arrays.copyOfRange(systemState, i * 3, i * 3 + 3);
            double[] newVelocity = Arrays.copyOfRange(systemState, (allObjects.size() + i) * 3, (allObjects.size() + i) * 3 + 3);
            allObjects.get(i).setCoordinates(newPosition);
            allObjects.get(i).setVelocity(newVelocity);
        }
    }

    /**
     * Takes all PlanetObjects and Probes in the Model and puts the values of their coordinates and velocities into
     * a one dimensional state array. This allows for easily passing all states to solvers.
     *
     * @param allObjects a list of all objects in the model
     * @return an array of doubles representing states of all objects
     */
    private static double[] flattenState(List<PlanetObject> allObjects) {
        double[] systemState = new double[allObjects.size() * 6];
        for (int i = 0; i < allObjects.size(); i++) {
            double[] position = allObjects.get(i).getCoordinates();
            double[] velocity = allObjects.get(i).getVelocity();

            System.arraycopy(position, 0, systemState, i * 3, 3);
            System.arraycopy(velocity, 0, systemState, (allObjects.size() + i) * 3, 3);
        }
        return systemState;
    }

    /**
     * The BiFunction representing the dynamics function for the solar system.
     */
    private BiFunction<Double, double[], double[]> dynamicsFunction() {
        return (time, systemState) -> {
            int n = getAllObjects().size();
            double[] dydt = new double[n * 6];
            updateObjectsState(systemState);
            for (int i = 0; i < n; i++) {
                System.arraycopy(getAllObjects().get(i).getVelocity(), 0, dydt, i * 3, 3);
                System.arraycopy(ACCELERATION_FUNCTION.calculate(i, getAllObjects()), 0, dydt, (n + i) * 3, 3);
            }
            return dydt;
        };
    }

    /**
     * Loads the information about initial positions and velocities of the celestial bodies to the model.
     *
     * @param dataLoader depending on the implementation of the interface passed in this argument
     *                   data from different sources can be chosen.
     */
    public static void loadData(DataLoader dataLoader) {
        getInstance().planetObjects = new HashMap<>();
        getInstance().probes = new ArrayList<>();
        dataLoader.load(getInstance().planetObjects);
    }


    // GETTERS AND SETTERS

    /**
     * @return a Map where the name of each celestial body starting with a capital letter is the key and
     * the PlanetObject corresponding to it is the value
     */
    public static Map<String, PlanetObject> getPlanetObjects() {
        return getInstance().planetObjects;
    }

    /**
     * Returns an ArrayList of PlanetObjects for when they need to be accessed in this particular order.
     *
     * @return an ArrayList of PlanetObjects in the model
     */
    public static ArrayList<PlanetObject> getPlanetObjectsArrayList() {
        ArrayList<PlanetObject> planets = new ArrayList<>();
        planets.add(Model.getPlanetObjects().get("Sun"));
        planets.add(Model.getPlanetObjects().get("Mercury"));
        planets.add(Model.getPlanetObjects().get("Venus"));
        planets.add(Model.getPlanetObjects().get("Earth"));
        planets.add(Model.getPlanetObjects().get("Moon"));
        planets.add(Model.getPlanetObjects().get("Mars"));
        planets.add(Model.getPlanetObjects().get("Jupiter"));
        planets.add(Model.getPlanetObjects().get("Saturn"));
        planets.add(Model.getPlanetObjects().get("Titan"));
        planets.add(Model.getPlanetObjects().get("Neptune"));
        planets.add(Model.getPlanetObjects().get("Uranus"));
        return planets;
    }

    /**
     * @return an ArrayList containing all objects in the model
     */
    public static ArrayList<PlanetObject> getAllObjects() {
        ArrayList<PlanetObject> allObjects = getPlanetObjectsArrayList();
        allObjects.addAll(getProbes());
        return allObjects;
    }

    /**
     * @return a List of Probe objects in the model
     */
    public static List<Probe> getProbes() {
        return getInstance().probes;
    }


    /**
     * Getter for the dynamic function.
     *
     * @return the BiFunction representing the dynamics function for the solar system.
     */
    private static BiFunction<Double, double[], double[]> getDynamicsFunction() {
        return getInstance().dynamicsFunction();
    }

    /**
     * Getter for time of the simulation.
     *
     * @return an object representing the current time of the model.
     */
    public static Time getTime() {
        return getInstance().TIME;
    }

    /**
     * Getter for the time step. The returned value depends on the state of the model.
     *
     * @return a double representing the time step to be passed to
     */
    public static double getTimeStep() {
        if (getProbes().get(0).getDistanceToTitan() < 100_000)
            return 0.5;
        if (getProbes().get(0).getDistanceToTitan() < 1_000_000)
            return 2;
        return 10;
    }
}
