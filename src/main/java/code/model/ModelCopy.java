package code.model;

import code.algorithms.functions.AccelerationFunction;
import code.algorithms.solvers.RungeKutta;
import code.algorithms.solvers.Solver;
import code.model.objects.PlanetObject;
import code.model.objects.Probe;
import code.utils.Time;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.BiFunction;


public class ModelCopy {
    private List<PlanetObject> planetObjects;
    private List<Probe> probes;
    private final Solver solver;
    private static final AccelerationFunction ACCELERATION_FUNCTION = new AccelerationFunction();
    private final Time TIME;


    public ModelCopy() {
        TIME = new Time(2023, 4, 1);
        planetObjects = new ArrayList<>();
        for (PlanetObject planetObject : Model.getPlanetObjectsArrayList())
            planetObjects.add(planetObjectCopy(planetObject));
        probes = new ArrayList<>();
        solver = new RungeKutta();
    }


    public List<Probe> getProbes() {
        return probes;
    }

    private PlanetObject planetObjectCopy(PlanetObject planetObject) {
        PlanetObject newObject = new PlanetObject(planetObject.getCoordinates(), planetObject.getVelocity());
        newObject.setMass(planetObject.getMass());
        newObject.setRadius(planetObject.getRadius());
        return newObject;
    }

    private void updateObjectsState(double[] systemState) {
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

    public ArrayList<PlanetObject> getAllObjects() {
        ArrayList<PlanetObject> allObjects = getPlanetObjectsArrayList();
        allObjects.addAll(getProbes());
        return allObjects;
    }

    public void step(double timeStep) {
        if (getProbes().size() > 0)
            getProbes().get(0).BoosterMECH(getTime());
        double[] currentState = flattenState(getAllObjects());
        double[] nextState = solver.solve(getDynamicsFunction(), currentState, 0, timeStep);
        updateObjectsState(nextState);
        getTime().addSeconds(timeStep);
    }

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

    private BiFunction<Double, double[], double[]> getDynamicsFunction() {
        return dynamicsFunction();
    }

    private double[] flattenState(List<PlanetObject> allObjects) {
        double[] systemState = new double[allObjects.size() * 6];
        for (int i = 0; i < allObjects.size(); i++) {
            double[] position = allObjects.get(i).getCoordinates();
            double[] velocity = allObjects.get(i).getVelocity();

            System.arraycopy(position, 0, systemState, i * 3, 3);
            System.arraycopy(velocity, 0, systemState, (allObjects.size() + i) * 3, 3);
        }
        return systemState;
    }

    public void addProbe(Probe probe) {
        probes.add(probe);
    }

    public Time getTime() {
        return TIME;
    }
}
