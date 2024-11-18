package com.ijse.gdse.bookstore.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {

    @FXML
    private AnchorPane ancLogin;

    @FXML
    private Button btnCreate;

    @FXML
    private Button btnLogin;

    @FXML
    private ImageView imgLogin;

    @FXML
    private Label lblB;

    @FXML
    private Label lblT;

    @FXML
    private Label lblW;

    @FXML
    private Label lblelcome;

    @FXML
    private Label lblo;

    @FXML
    private Label lblookStore;

    @FXML
    private TextField txtEmail;

    @FXML
    private PasswordField txtPass;

    @FXML
    void btnCreateOnAction(ActionEvent event) throws IOException {
        // Load the FXML file as a new scene
        AnchorPane newPane = FXMLLoader.load(getClass().getResource("/view/Sigin.fxml"));
        Scene newScene = new Scene(newPane);

        // Get the current stage from the event's source (the button) and set the new scene
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.setScene(newScene);
    }

    @FXML
    void btnLoginOnAction(ActionEvent event) throws IOException{
        // Load the FXML file as a new scene
        AnchorPane newPane = FXMLLoader.load(getClass().getResource("/view/MainLayout.fxml"));
        Scene newScene = new Scene(newPane);

        // Get the current stage from the event's source (the button) and set the new scene
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.setMaximized(true);
        currentStage.setScene(newScene);
    }

}
