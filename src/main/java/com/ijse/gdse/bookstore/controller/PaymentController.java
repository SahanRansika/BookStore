package com.ijse.gdse.bookstore.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class PaymentController {

    @FXML
    private AnchorPane ancPayment;

    @FXML
    private Button btnBuy;

    @FXML
    private Button btnClose;

    @FXML
    private Button btnPrint;

    @FXML
    private Button btnUpdate;

    @FXML
    private TextField txtAmount;

    @FXML
    private TextField txtCustId;

    @FXML
    private TextField txtDue;

    @FXML
    private TextField txtOrderId;

    @FXML
    private TextField txtPaydate;

    @FXML
    private TextField txtPrice;

    @FXML
    private TextField txtQty;

    @FXML
    void btnBuyOnAction(ActionEvent event) {

    }

    @FXML
    void btnCloseOnAction(ActionEvent event) throws IOException {
// Load the FXML file as a new scene
        AnchorPane newPane = FXMLLoader.load(getClass().getResource("/view/Orders.fxml"));
        Scene newScene = new Scene(newPane);

        // Get the current stage from the event's source (the button) and set the new scene
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.setScene(newScene);
    }

    @FXML
    void btnPrintOnAction(ActionEvent event) {

    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {

    }

}
