package code.graphics;

import code.algorithms.trajectory.TargetBoost;
import code.graphics.landing.LandingScene;
import code.landing.module.HardcodedValues;
import code.model.Model;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.util.Timer;

/**
 * Main graphic class which sets up the Scene and runs the engine which updates all 3D representations of
 * objects in the model in real-time.
 */
public class Visualizer extends Application {
    public static final int SCALE = 50; // don't change this
    private final double WIDTH = Screen.getPrimary().getBounds().getWidth();
    private final double HEIGHT = Screen.getPrimary().getBounds().getHeight();
    private static SolarScene solarScene;
    private final int SMOOTHNESS = 200;
    private Timer timer;
    private static int count;
    private LandingScene landingScene;
    private Stage STAGE;
    private boolean changedScene;
    HardcodedValues spaceCraft;
    boolean stop;

    @Override
    public void start(Stage stage) {
         spaceCraft=new HardcodedValues(200001,300000,  0,0,0);
        STAGE = stage;
        STAGE.setTitle("Mission to Titan");
        STAGE.show();
        stop=false;
        changedScene = false;

        solarScene = new SolarScene(WIDTH, HEIGHT);
        landingScene = new LandingScene(WIDTH, HEIGHT);
        STAGE.setScene(solarScene);

        timer = new Timer();
        count = 0;
        STAGE.setOnCloseRequest(e -> {
            timer.cancel();
            Platform.exit();
            System.exit(0);
        });
        Task<Void> sleeper = new Task<>() {
            @Override
            protected Void call() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ignored) {
                }
                return null;
            }
        };
        sleeper.setOnSucceeded(event -> calculation());
        new Thread(sleeper).start();
    }


    private void calculation() {

        timer.schedule(
                new java.util.TimerTask() {
                    @Override
                    public void run() {
//                        if (Model.getProbes().get(0).getDistanceToTitan() < 600000000) {
                            if (!changedScene) {
                                Platform.runLater(() -> {

                                    STAGE.setScene(landingScene);
                                });
                                changedScene = true;
                            }


                                if(!stop)
                                {
                                    spaceCraft.controllerX();
                                    //spaceCraft.controller();
                                    spaceCraft.print();
                                    stop = spaceCraft.isFinished();
                                    Platform.runLater(() -> {
                                        landingScene.moveSpaceship(spaceCraft.getXPosition(), spaceCraft.getYPosition(), spaceCraft.getRotationAngle());
                                    });

                                }


//                        }
//
//                        if (Model.getTime().laterOrEqual(Model.getProbes().get(0).getTimeOfNextBoost()))
//                            new TargetBoost(Model.getPlanetObjects().get("Titan").getCoordinates());
//
//                        for (int i = 0; i < SMOOTHNESS; i++)
//                            Model.step();
//
//                        Platform.runLater(() -> {
//                            solarScene.update(Model.getTime());
//                        });
//
//                        count++;
//                        if (count % 25 == 0)
//                            Platform.runLater(solarScene::addTrail);
                    }
                }, 0, 10);
    }

    public static void main(String[] args) {
        launch();
    }
}
