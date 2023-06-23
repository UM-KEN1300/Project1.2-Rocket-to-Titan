package code.experiments;

import code.model.Model;
import code.model.data.loaders.FileDataLoader;
import code.utils.Time;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class TitanLocation {
    public static void main(String[] args) {
        writeTitanLocations();
    }

    private static void writeTitanLocations() {
        String filepath = "titan_locations.txt";
        try {
            FileWriter writer = new FileWriter(filepath);
            BufferedWriter buffer = new BufferedWriter(writer);

            double timeStep = 50;
            Model.loadData(new FileDataLoader());
            Time nextUpdate = Model.getTime().timeCopy();
            int seconds = 0;
            while (seconds / (60 * 60 * 24) < 366) {
                if (nextUpdate.sameHour(Model.getTime())) {
                    buffer.write("Day: " + seconds / (60 * 60 * 24) + ", Coordinates: " +
                            Model.getPlanetObjects().get("Titan").getCoordinates()[0] + " " +
                            Model.getPlanetObjects().get("Titan").getCoordinates()[1] + " " +
                            Model.getPlanetObjects().get("Titan").getCoordinates()[2] + "\n");
                    nextUpdate.addSeconds(60 * 60 * 24);
                }
                Model.step(timeStep);
                seconds += timeStep;
            }

            buffer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
