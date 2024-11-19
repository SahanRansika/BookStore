package com.ijse.gdse.bookstore.controller;

import com.ijse.gdse.bookstore.dto.*;
import com.ijse.gdse.bookstore.dto.tm.CartTM;
import com.ijse.gdse.bookstore.dto.tm.ReturnTM;
import com.ijse.gdse.bookstore.model.BookModel;
import com.ijse.gdse.bookstore.model.CustomerModel;
import com.ijse.gdse.bookstore.model.ReturnModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ReturnController implements Initializable {

    @FXML
    private AnchorPane ancReturn;

    @FXML
    private Button btnPlaceReturn;

    @FXML
    private ComboBox<String> cmbBookId;

    @FXML
    private ComboBox<String> cmbCustomerId;


    @FXML
    private TableColumn<ReturnTM, String> colBookID;

    @FXML
    private TableColumn<ReturnTM, String> colDueDate;

    @FXML
    private TableColumn<ReturnTM, String> colID;

    @FXML
    private TableColumn<ReturnTM, String> colIssueDate;

    @FXML
    private TableColumn<ReturnTM, String> colName;

    @FXML
    private Label lblBookName;

    @FXML
    private Label lblCustomerName;

    @FXML
    private Label lblReturnId;

    @FXML
    private TableView<ReturnTM> tblReturn;

    @FXML
    private DatePicker IssueDate;

    @FXML
    private DatePicker DueDate;

    private ObservableList<ReturnTM> returnTableTMS = FXCollections.observableArrayList();


    private final ReturnModel returnModel = new ReturnModel();
    private final CustomerModel customerModel = new CustomerModel();
    private final BookModel bookModel = new BookModel();

    // Observable list to manage cart book in TableView
    private final ObservableList<ReturnTM> returnTMS = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)  {
        setCellValues();

        // Load data and initialize the page
        try {
            refreshPage();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Fail to load data..!").show();
        }
    }
    private void setCellValues() {
        // Set up the table columns with property values from CartTM class
        colBookID.setCellValueFactory(new PropertyValueFactory<>("BookID"));
        colName.setCellValueFactory(new PropertyValueFactory<>("BookName"));
        colDueDate.setCellValueFactory(new PropertyValueFactory<>("DueDate"));
        colIssueDate.setCellValueFactory(new PropertyValueFactory<>("IssueDate"));
        colID.setCellValueFactory(new PropertyValueFactory<>("ID"));

        // Bind the cart book observable list to the TableView
        tblReturn.setItems(returnTMS);
    }


    private void refreshPage() throws SQLException {
        lblReturnId.setText(returnModel.getNextReturnId());
        loadBookIds();
        loadCustomerIds();

        cmbCustomerId.getSelectionModel().clearSelection();
        cmbBookId.getSelectionModel().clearSelection();
        lblBookName.setText("");
        lblCustomerName.setText("");

        // Clear the cart observable list
        returnTMS.clear();

        // Refresh the table to reflect changes
        tblReturn.refresh();
    }

    private void loadBookIds() throws SQLException {
        ArrayList<String> bookIds = bookModel.getAllBookIds();
        ObservableList<String> observableList = FXCollections.observableArrayList();
        observableList.addAll(bookIds);
        cmbBookId.setItems(observableList);
    }

    /**
     * Load all customer IDs into the customer ComboBox.
     */
    private void loadCustomerIds() throws SQLException {
        ArrayList<String> customerIds = customerModel.getAllCustomerIds();
        ObservableList<String> observableList = FXCollections.observableArrayList();
        observableList.addAll(customerIds);
        cmbCustomerId.setItems(observableList);
    }

    @FXML
    void btnAddToReturnOnAction(ActionEvent event) {
        String selectedBookId = cmbBookId.getValue();
//        String customerId = cmbCustomerId.getValue();
        String returnId = lblReturnId.getText();
        String bookName = lblBookName.getText();
        Date issueDate = Date.valueOf(IssueDate.getValue());
        Date dueDate = Date.valueOf(DueDate.getValue());

        ReturnTM returnTM = new ReturnTM(
                returnId,
                selectedBookId,
                bookName,
                issueDate,
                dueDate
        );
        returnTableTMS.add(returnTM);

        tblReturn.setItems(returnTableTMS);

        if (selectedBookId == null) {
            new Alert(Alert.AlertType.ERROR, "Please select book..!").show();
            return;
        }


        Date IssueDatePicket = Date.valueOf(IssueDate.getValue());
        Date DueDatePicket = Date.valueOf(DueDate.getValue());

        tblReturn.refresh();

    }


    @FXML
    void btnResetOnAction(ActionEvent event) throws SQLException{
        refreshPage();
    }

    @FXML
    void btnPlaceReturnOnAction(ActionEvent event) {
        if (tblReturn.getItems().isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Please add books to cart..!").show();
            return;
        }
        if (cmbCustomerId.getSelectionModel().isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Please select customer for place return..!").show();
            return;
        }
        ArrayList<String> BookIds = new ArrayList<>();
        for (ReturnTM returnTM : returnTableTMS){
            String bookId = returnTM.getBookID();
            BookIds.add(bookId);
        }

        // List to hold order details
//        ArrayList<ReturnDTO> returnDTOS = new ArrayList<>();

        // Collect data for each book in the cart and add to order details array
//        for (ReturnTM returnTM : returnTMS) {
//
//
//        }
//        returnModel.returnBook(BookIds);

    }

    @FXML
    void cmbBookOnAction(ActionEvent event) throws SQLException{
        String selectedBookId = cmbBookId.getSelectionModel().getSelectedItem();
        BookDTO bookDTO = bookModel.findById(selectedBookId);

        // If book found (bookDTO not null)
        if (bookDTO != null) {

            // FIll item related labels
            lblBookName.setText(bookDTO.getColTitle());
        }
    }

    @FXML
    void cmbCustomerOnAction(ActionEvent event) throws SQLException{
        String selectedCustomerId = cmbCustomerId.getSelectionModel().getSelectedItem();
        CustomerDTO customerDTO = customerModel.findById(selectedCustomerId);

        // If customer found (customerDTO not null)
        if (customerDTO != null) {

            // FIll customer related labels
            lblCustomerName.setText(customerDTO.getColName());
        }
    }

    @FXML
    void onClickTable(MouseEvent event) {

    }

}
