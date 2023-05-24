package code.graphics.overlay;

import code.model.objects.Probe;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;

import java.math.RoundingMode;
import java.text.DecimalFormat;

public class OverlayPane extends AnchorPane {
    private final ProbeStatsLabel DISTANCE_LABEL;
    private final DecimalFormat DF;
    private final Probe PROBE;
    private final Font font = Font.font("Verdana", FontWeight.BOLD, 15);


    public OverlayPane(Probe probe) {
        super();
        PROBE = probe;
        DISTANCE_LABEL = new ProbeStatsLabel();
        getChildren().add(new GuideLabel());
        getChildren().add(DISTANCE_LABEL);
        getChildren().add(new DateLabel());

        DF = new DecimalFormat("#.###");
        DF.setRoundingMode(RoundingMode.HALF_UP);
    }


    public void update() {
        DISTANCE_LABEL.setText("Distance to Titan: " + DF.format(PROBE.getDistanceToTitan()) + " km");
    }

    private class GuideLabel extends Label {
        GuideLabel() {
            super("""
                    Probe - [1]
                    Sun - [2]
                    Titan - [3]
                    Earth - [4]
                    Saturn - [5]
                    """);
            setFont(font);
            setStyle("-fx-line-spacing: 5;");
            setTextFill(Color.WHITE);
            setTextAlignment(TextAlignment.RIGHT);
            setBottomAnchor(this, 15d);
            setRightAnchor(this, 15d);
        }
    }

    private class ProbeStatsLabel extends Label {
        ProbeStatsLabel() {
            super();
            setFont(font);
            setTextFill(Color.WHITE);
            setTopAnchor(this, 10d);
            setLeftAnchor(this, 10d);
        }
    }

    private class DateLabel extends Label {
        DateLabel() {
            super("Date: ");
            setFont(font);
            setTextFill(Color.WHITE);
            setTopAnchor(this, 10d);
            setRightAnchor(this, 10d);
        }
    }
}
