package graphics.overlay;

import javafx.scene.Group;
import javafx.scene.layout.AnchorPane;

public class OverlayAnchorPane {
    private final AnchorPane anchorPane;
    private Group root;


    public OverlayAnchorPane() {
        anchorPane = new AnchorPane();
        root = new Group();
    }


    public AnchorPane getAnchorPane() {
        return anchorPane;
    }

    private void initializeOverlay() {

    }
}
