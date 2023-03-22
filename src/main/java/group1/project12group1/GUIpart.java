package group1.project12group1;
import helperFunction.HelperFunctions;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Sphere;
import javafx.stage.Screen;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static group1.project12group1.PlanetObject.helperFunctions;


public class GUIpart extends Application
{
    public static PlanetObject Sun = new PlanetObject(0, 0, 0, 0, 0, 0, 1.99e30);
    public static PlanetObject Mercury = new PlanetObject(7.83e6, 4.49e7, 2.87e6, -5.75e1, 1.15e1, 6.22e0, 3.3e23);
    public static PlanetObject Venus = new PlanetObject(-2.82e7, 1.04e8, 3.01e6, -3.4e1, -8.97e0, 1.84e0, 4.87e24);
    public static PlanetObject Earth = new PlanetObject(-1.48e8, -2.78e7, 3.37e4, 5.05e0, -2.94e1, 1.71e-3, 5.97e24);
    public static PlanetObject Moon = new PlanetObject(-1.48e8, -2.75e7, 7.02e4, 4.34e0, -3.0e1, -1.16e2, 7.35e22);
    public static PlanetObject Mars = new PlanetObject(-1.59e8, 1.89e8, 7.87e6, -1.77e1, -1.35e1, 1.52e-1, 6.42e23);
    public static PlanetObject Jupiter = new PlanetObject(6.93e8, 2.59e8, -1.66e7, -4.71e0, 1.29e1, 5.22e-2, 1.90e27);
    public static PlanetObject Saturn = new PlanetObject(1.25e9, -7.60e8, -3.67e7, 4.47e0, 8.24e0, -3.21e1, 5.68e26);
    public PlanetObject Titan = new PlanetObject(1.25e9, -7.61e8, -3.63e7, 9, 1.11e1, -2.25e0, 1.35e23);
    public static PlanetObject Neptune = new PlanetObject(4.45e9, -3.98e8, -9.45e7, 4.48e-1, 5.45e0, -1.23e1, 1.02e26);
    public static PlanetObject Uranus = new PlanetObject(1.96e9, 2.19e9, -1.72e7, -5.13e0, 4.22e0, 8.21e-2, 8.68e25);
    private static ArrayList<PlanetObject> listOfPlanets = new ArrayList<>();
    static double offsetWidth;
    static double offsetHeight;
    final double WIDTH = Screen.getPrimary().getBounds().getWidth();
    final double HEIGHT = Screen.getPrimary().getBounds().getHeight();
        static final int SCALE = 5_000_000;

        public GUIpart()
        {
            listOfPlanets.add(Sun);
            Sun.setColor(Color.YELLOW);
            listOfPlanets.add(Mercury);
            Mercury.setColor(Color.ORANGE);
            listOfPlanets.add(Venus);
            Venus.setColor(Color.RED);
            listOfPlanets.add(Earth);
            Earth.setColor(Color.LIGHTBLUE);
            listOfPlanets.add(Moon);
            Moon.setColor(Color.GREY);
            listOfPlanets.add(Mars);
            Mars.setColor(Color.GREEN);
            listOfPlanets.add(Jupiter);
            Jupiter.setColor(Color.PINK);
            listOfPlanets.add(Saturn);
            listOfPlanets.add(Neptune);
            Neptune.setColor(Color.DARKBLUE);
            listOfPlanets.add(Uranus);
            Uranus.setColor(Color.PURPLE);
            offsetWidth  =WIDTH/2;
            offsetHeight  =HEIGHT/2;
        }


        @Override
        public void start(Stage stage) throws IOException {
            System.out.println(WIDTH+"    "+HEIGHT);

            Group root = new Group();



            HelperFunctions helperFunctions = new HelperFunctions();
            Sphere planet=new Sphere();
            //set cordinates
            for (int i = 0; i <listOfPlanets.size() ; i++)
            {
                planet=listOfPlanets.get(i).getCircle();
             double cordX=listOfPlanets.get(i).getX();
             cordX=cordX/SCALE;
             cordX=cordX+offsetWidth;

             double cordY=listOfPlanets.get(i).getY();
             cordY=cordY/SCALE;
             cordY=cordY+offsetHeight;
             listOfPlanets.get(i).setX2D(cordX);
             listOfPlanets.get(i).setY2D(cordY);
             planet.setTranslateX(cordX);
             planet.setTranslateY(cordY);
                PhongMaterial material = new PhongMaterial();
                material.setDiffuseColor(listOfPlanets.get(i).getColor());
                material.setSpecularColor(listOfPlanets.get(i).getColor());
            planet.setMaterial(material);

             root.getChildren().add(planet);
            }



           orbit();





            Scene scene = new Scene(root, WIDTH, HEIGHT);
            stage.setTitle("Visualization");
            stage.setScene(scene);
            stage.show();
        }

    private static AnimationTimer orbt_timer;
     static void orbit(){

        orbt_timer=new AnimationTimer() {
            @Override
            public void handle(long now)
            {
                long lastUpdate = 0;
                if (now - lastUpdate >= 1_000_000_000)
                {
                    lastUpdate = now;
                    int times=36000;

                    for (int i = 0; i <times ; i++)
                    {


                        for (int j = 1; j < listOfPlanets.size(); j++)
                        {

                            double[] acc = new double[3];
                            for (int k = 0; k < listOfPlanets.size(); k++)
                            {
                                if (k != j)
                                {
//                                    if(j<k)
//                                    {
                                        acc = helperFunctions.addition(acc, listOfPlanets.get(j).getForce(listOfPlanets.get(k)));
//                                    }
//                                    else
//                                    {
                                       // acc = helperFunctions.addition(acc, listOfPlanets.get(j).getAcceleration(listOfPlanets.get(k)));
                                   // }

                                }

                            }
                            listOfPlanets.get(j).setPrivousPosition(listOfPlanets.get(j).getPositionalVector());
                            listOfPlanets.get(j).updatePositionVelocityWithForce(acc, 0.1);
                        }
                    }

                    for (int i = 1; i <listOfPlanets.size() ; i++)
                    {

                        PlanetObject venus = listOfPlanets.get(i);
                        double cordX = listOfPlanets.get(i).getPositionalVector()[2];
                        cordX = cordX / SCALE;
                        cordX = cordX + offsetWidth;
                        venus.getCircle().translateXProperty().set(cordX);


                        double cordY = listOfPlanets.get(i).getPositionalVector()[1];
                        cordY = cordY / SCALE;
                        cordY = cordY + offsetHeight;
                        venus.getCircle().translateYProperty().set(cordX);

                    }


                }
            }


        };orbt_timer.start();
    }



    public static void main(String[] args)
    {
        launch();
    }


}
