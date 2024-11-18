package com.ijse.gdse.bookstore.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.sql.Date;

public class ReturnController {

    @FXML
    private AnchorPane ancReturn;

    @FXML
    private Button btnPlaceReturn;

    @FXML
    private ComboBox<?> cmbBookId;

    @FXML
    private ComboBox<?> cmbCustomerId;

    @FXML
    private ComboBox<?> cmbDueDay;

    @FXML
    private ComboBox<?> cmbDueMonth;

    @FXML
    private ComboBox<?> cmbDueYear;

    @FXML
    private ComboBox<?> cmbIssueDay;

    @FXML
    private ComboBox<?> cmbIssueMonth;

    @FXML
    private ComboBox<?> cmbIssueYear;

    @FXML
    private TableColumn<?, ?> colBookID;

    @FXML
    private TableColumn<?, ?> colDueDate;

    @FXML
    private TableColumn<?, ?> colID;

    @FXML
    private TableColumn<?, ?> colIssueDate;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private Label lblBookName;

    @FXML
    private Label lblCustomerName;

    @FXML
    private Label lblReturnId;

    @FXML
    private TableView<?> tblReturn;

    @FXML
    private DatePicker IssueDate;

    @FXML
    void btnAddToReturnOnAction(ActionEvent event) {
        Date IssueDatePicke = Date.valueOf(IssueDate.getValue());

    }

    @FXML
    void btnPlaceReturnOnAction(ActionEvent event) {

    }

    @FXML
    void cmbBookOnAction(ActionEvent event) {

    }

    @FXML
    void cmbCustomerOnAction(ActionEvent event) {

    }

    @FXML
    void cmbDueDayOnAction(ActionEvent event) {

    }

    @FXML
    void cmbDueMonthOnAction(ActionEvent event) {

    }

    @FXML
    void cmbDueYearOnAction(ActionEvent event) {

    }

    @FXML
    void cmbIssueDayOnAction(ActionEvent event) {

    }

    @FXML
    void cmbIssueMonthOnAction(ActionEvent event) {

    }

    @FXML
    void cmbIssueYearOnAction(ActionEvent event) {

    }

    @FXML
    void onClickTable(MouseEvent event) {

    }

}
