module group1.project12group1 {
    requires javafx.controls;
    requires javafx.fxml;



    opens Model to javafx.fxml;
    exports Model;
    exports GUI;
    opens GUI to javafx.fxml;
}