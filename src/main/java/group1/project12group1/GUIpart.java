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
    public static PlanetObject Sun = new PlanetObject(0, 0, 0, 0, 0, 0, 1.9885e30);
    public static PlanetObject Mercury = new PlanetObject(7833268.439, 44885949.37, 2867693.200,  -57.5, 11.520, 6.216,3.3e23);
    public static PlanetObject Venus = new PlanetObject(-28216773.942, 103994008.541, 3012326.642,  -34.023, -8.965, 1.840,4.87e24);
    public static PlanetObject Earth = new PlanetObject(-148186906.893, -27823158.571, 33746.898, 5.05, -29.392, 0.0017-3,5.97e24);
    public static PlanetObject Moon = new PlanetObject(-148458048.395, -27524868.184, 70233.649, 4.340, -30.048, -0.011, 7.35e22);
    public static PlanetObject Mars = new PlanetObject(-159116303.422, 189235671.561, 7870476.085, -17.695, -13.463, 0.152, 6.42e23);
    public static PlanetObject Jupiter = new PlanetObject(692722875.928, 258560760.813, -16570817.710, -4.714, 12.855, 0.052, 1.90e27);
    public static PlanetObject Saturn = new PlanetObject(1253801723.954, -760453007.81, -36697431.156, 4.467, 8.239, -0.320, 5.68e26);
    public static PlanetObject Titan = new PlanetObject(1254501624.959, -761340299.067, 36309613.837, 8.995, 11.108, -2.251, 1.35e23);
    public static PlanetObject Neptune = new PlanetObject(4454487339.094, -397895128.763, -94464151.342, 0.447,5.446, -0.122, 1.02e26);
    public static PlanetObject Uranus = new PlanetObject(1958732435.993, 2191808553.218, -17235283.832, -5.127, 4.220, 0.082, 8.68e25);
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
