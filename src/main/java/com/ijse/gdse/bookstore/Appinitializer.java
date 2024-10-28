package com.ijse.gdse.bookstore;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class Appinitializer extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {

        Parent load = FXMLLoader.load(getClass().getResource("/view/Login.fxml"));
        Scene scene = new Scene(load);
        Image image = new Image(getClass().getResourceAsStream("/images/book4.png" ));
        stage.getIcons().add(image);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("BookStore");
        stage.show();
    }
}