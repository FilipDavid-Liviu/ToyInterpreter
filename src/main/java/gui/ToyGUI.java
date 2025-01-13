package gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class ToyGUI extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(ToyGUI.class.getResource("menu.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Image icon = new Image("file:src/main/icon.png");
        stage.getIcons().add(icon);

        scene.getStylesheets().add("file:src/main/styles.css");
        stage.setTitle("Menu");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}