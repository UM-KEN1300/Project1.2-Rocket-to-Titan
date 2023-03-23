module group1.project12group1 {
    requires javafx.controls;
    requires javafx.fxml;



    opens group1.project12group1 to javafx.fxml;
    exports group1.project12group1;
}