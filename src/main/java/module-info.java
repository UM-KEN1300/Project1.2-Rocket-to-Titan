module group1.project12group1 {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.apache.poi.poi;
    requires org.apache.poi.ooxml;

    opens new_graphics.graphics to javafx.fxml;
    opens Model to javafx.fxml;

    exports helperFunction;
    exports new_graphics.graphics.solar_system.controllers;
    exports Model;
    exports GUIold;
    opens GUIold to javafx.fxml;
    exports new_graphics.model_from_file;
    exports new_graphics.graphics;
    exports new_graphics.graphics.overlay;
    opens new_graphics.graphics.overlay to javafx.fxml;
    exports new_graphics.graphics.solar_system;
    opens new_graphics.graphics.solar_system to javafx.fxml;
    exports new_graphics.model_from_file.objects;
    opens new_graphics.graphics.solar_system.controllers to javafx.fxml;
    exports new_graphics.graphics.overlay.controllers;
    opens new_graphics.graphics.overlay.controllers to javafx.fxml;
}
