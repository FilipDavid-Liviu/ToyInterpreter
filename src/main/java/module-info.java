module com.example.toylanguage {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;


    opens com.example.toylanguage to javafx.fxml;
    exports com.example.toylanguage;
    exports model.adt;
    opens model.adt to javafx.fxml;
}