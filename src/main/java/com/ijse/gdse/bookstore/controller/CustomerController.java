package com.ijse.gdse.bookstore.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import com.ijse.gdse.bookstore.dto.CustomerDTO;
import com.ijse.gdse.bookstore.dto.tm.CustomerTM;
import com.ijse.gdse.bookstore.model.CustomerModel;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class CustomerController implements Initializable {

    @FXML
    private AnchorPane ancCustomer;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnUpdate;

    @FXML
    private Button btnReset;

    @FXML
    private TableColumn<?, ?> colEmail;

    @FXML
    private TableColumn<?, ?> colID;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private TableColumn<?, ?> colPhone;

    @FXML
    private TableView<CustomerTM> tblCustomer;

    @FXML
    private TextField txtEmail;

    @FXML
    private Label lblCustomer;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtPhone;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // set table column to cell factory value
        colID.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colPhone.setCellValueFactory(new PropertyValueFactory<>("phone"));


        // inside initialize method
        try {
            refreshPage();
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Fail to load customer id").show();
        }
    }

    private void refreshPage() throws SQLException {
        loadNextCustomerId();
        loadTableData();

        btnSave.setDisable(false);
        btnUpdate.setDisable(true);
        btnDelete.setDisable(true);

        txtName.clear();
        txtEmail.clear();
        txtPhone.clear();
    }

    public void loadNextCustomerId() throws SQLException {
//        customerModel.helloCustomerModel();
        String nextCustomerId = customerModel.getNextCustomerId();
        lblCustomer.setText(nextCustomerId);
    }

    private final CustomerModel customerModel = new CustomerModel();

    private void loadTableData() throws SQLException {
            ArrayList<CustomerDTO> customerDTOS = customerModel.getAllCustomers();
            ObservableList<CustomerTM> customerTMS = FXCollections.observableArrayList();

            for (CustomerDTO customerDTO : customerDTOS) {
                CustomerTM customerTM = new CustomerTM(
                        customerDTO.getColID(),
                        customerDTO.getColName(),
                        customerDTO.getColEmail(),
                        customerDTO.getColPhone()
                );
                customerTMS.add(customerTM);
            }

            tblCustomer.setItems(customerTMS);
    }

    @FXML
    void onClickTable(MouseEvent event) {
        CustomerTM customerTM = tblCustomer.getSelectionModel().getSelectedItem();
        if (customerTM != null) {
            lblCustomer.setText(customerTM.getCustomerId());
            txtName.setText(customerTM.getName());
            txtEmail.setText(customerTM.getEmail());
            txtPhone.setText(customerTM.getPhone());

            btnSave.setDisable(true);

            btnDelete.setDisable(false);
            btnUpdate.setDisable(false);
        }
    }
    @FXML
    public void btnSaveOnAction(ActionEvent event) throws SQLException {
        String customerId = lblCustomer.getText();
        String name = txtName.getText();
        String email = txtEmail.getText();
        String phone = txtPhone.getText();

//        [A-Za-z ]+
//        (1)
//        Pattern namePattern = Pattern.compile("^[A-Za-z ]+$");
//        boolean isValidName = namePattern.matcher(name).matches();
//        System.out.println("method 1 : "+isValidName);

//        (2)
//        System.out.println("method 2 : "+name.matches("^[A-Za-z ]+$"));

        txtName.setStyle(txtName.getStyle() + ";-fx-border-color: #7367F0;");
        txtEmail.setStyle(txtEmail.getStyle() + ";-fx-border-color: #7367F0;");
        txtPhone.setStyle(txtPhone.getStyle() + ";-fx-border-color: #7367F0;");

        String namePattern = "^[A-Za-z ]+$";
        String emailPattern = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
        String phonePattern = "^(\\d+)||((\\d+\\.)(\\d){2})$";

        boolean isValidName = name.matches(namePattern);
        boolean isValidEmail = email.matches(emailPattern);
        boolean isValidPhone = phone.matches(phonePattern);

        if (!isValidName) {
            System.out.println(txtName.getStyle());
            txtName.setStyle(txtName.getStyle() + ";-fx-border-color: red;");
            System.out.println("Invalid name.............");
//            return;
        }

        if (!isValidEmail) {
            txtEmail.setStyle(txtEmail.getStyle() + ";-fx-border-color: red;");
        }

        if (!isValidPhone) {
            txtPhone.setStyle(txtPhone.getStyle() + ";-fx-border-color: red;");
        }


        if (isValidName && isValidEmail && isValidPhone) {
            CustomerDTO customerDTO = new CustomerDTO(
                    customerId,
                    name,
                    email,
                    phone
            );

            boolean isSaved = customerModel.saveCustomer(customerDTO);
            if (isSaved) {
                refreshPage();
                new Alert(Alert.AlertType.INFORMATION, "Customer saved...!").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Fail to save customer...!").show();
            }
        }
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) throws SQLException {

    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) throws SQLException {
        CustomerDTO customerDTO = new CustomerDTO(
        lblCustomer.getText(),
        txtName.getText(),
        txtEmail.getText(),
        txtPhone.getText()
        );

        try {
                boolean isUpdate = customerModel.updateCustomer(customerDTO);
                if (isUpdate) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Customer Update.").show();
                    refreshPage();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Customer is not update. Something went wrong.").show();
                }
            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR, "Error updating customer data.").show();
        }
    }
    @FXML
    void btnResetOnAction(ActionEvent event) throws SQLException {
        refreshPage();
    }
}






