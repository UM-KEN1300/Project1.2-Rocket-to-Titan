module code {
    requires org.apache.poi.poi;
    requires org.apache.poi.ooxml;
    requires javafx.graphics;

    exports code.graphics;
    exports code.model;
    exports code.model.objects;
    exports code.utils;
    exports code.algorithms;
}
