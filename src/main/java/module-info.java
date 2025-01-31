module toylanguage {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;


    opens gui to javafx.fxml;
    exports gui;
    exports model.adt;
    opens model.adt to javafx.fxml;
    exports gui.helpers;
    opens gui.helpers to javafx.fxml;
}