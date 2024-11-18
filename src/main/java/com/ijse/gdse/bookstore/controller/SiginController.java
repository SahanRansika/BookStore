package com.ijse.gdse.bookstore.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class SiginController {

    @FXML
    private AnchorPane ancSigin;

    @FXML
    private Button btnsigin;

    @FXML
    private ImageView imageLogin;

    @FXML
    private PasswordField passwordField;

    @FXML
    private TextField txtunm;

    @FXML
    private TextField txtunm2;

    @FXML
    private TextField txtunm21;

    @FXML
    void btnSiginButtonOnAction(ActionEvent event) throws IOException {
        // Load the FXML file as a new scene
        AnchorPane newPane = FXMLLoader.load(getClass().getResource("/view/Login.fxml"));
        Scene newScene = new Scene(newPane);

        // Get the current stage from the event's source (the button) and set the new scene
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.setScene(newScene);
    }

}
