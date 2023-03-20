package group1.project12group1;


public class SolarSystem{

    public static void main(String[] args)
    {
        //todo fix the variables they were flipped

        PlanetObject Sun = new PlanetObject(0, 0, 0, 1.99e30, 0, 0, 0);
        PlanetObject Mercury = new PlanetObject(7.83e6, 4.49e7, 2.87e6, 3.3e23, -5.75e1, 1.15e1, 6.22e0);
        PlanetObject Venus = new PlanetObject(-2.82e7, 1.04e8, 3.01e6, 4.87e24, -3.4e1, -8.97e0, 1.84e0);
        PlanetObject Earth = new PlanetObject(-1.48e8, -2.78e7, 3.37e4, 5.97e24, 5.05e0, -2.94e1, 1.71e-3);




        PlanetObject testEarth=new PlanetObject(0,0,0,5.97e24);
        PlanetObject testPerson= new PlanetObject(6371000,0,0,70);
        System.out.println(testEarth.getForce(testPerson)[0]);



    } 
}
