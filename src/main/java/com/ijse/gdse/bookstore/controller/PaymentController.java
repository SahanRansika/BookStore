package com.ijse.gdse.bookstore.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class PaymentController {

    @FXML
    private AnchorPane ancPayment;

    @FXML
    private Button btnBuy;

    @FXML
    private Button btnClose;

    @FXML
    private Button btnReset;

    @FXML
    private TextField txtAmount;

    @FXML
    private Label txtDue;

    @FXML
    private TextField txtTotal;


    @FXML
    void btnBuyOnAction(ActionEvent event) {
        try {
            double total = Double.parseDouble(txtTotal.getText());
            double amount = Double.parseDouble(txtAmount.getText());

            if (amount >= total) {
                txtDue.setText("No Due");
                showAlert("Payment Successful", "Thank you for your purchase!");
            } else {
                double due = total - amount;
                txtDue.setText("Due:           " + String.format("%.2f", due));
                showAlert("Payment Incomplete", "You still owe: " + String.format("%.2f", due));
            }
        } catch (NumberFormatException e) {
            showAlert("Error", "Please enter valid numeric values in the fields.");
        }
    }


    @FXML
    void btnCloseOnAction(ActionEvent event) {
        ancPayment.getScene().getWindow().hide(); // Hides the current window
    }


    @FXML
    void btnResetInAction(ActionEvent event) {
        txtAmount.clear();
        txtTotal.clear();
        txtDue.setText("");
    }


    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
