package com.ijse.gdse.bookstore.controller;
import com.ijse.gdse.bookstore.dto.CustomerDTO;
import com.ijse.gdse.bookstore.dto.tm.CustomerTM;
import com.ijse.gdse.bookstore.model.CustomerModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class CustomerController implements Initializable {

    @FXML
    private Button btnsave;

    @FXML
    private Button btndelete;

    @FXML
    private Button btnreport;

    @FXML
    private Button btnreset;

    @FXML
    private Button btnupdate;

    @FXML
    private TableColumn<CustomerTM, String> colcustId;

    @FXML
    private TableColumn<CustomerTM, String> colemail;

    @FXML
    private TableColumn<CustomerTM, String> colname;

    @FXML
    private TableColumn<CustomerTM, String> colnic;

    @FXML
    private TableColumn<CustomerTM, String> colphone;

    @FXML
    private Label lblcustId;

    @FXML
    private Label lblemail;

    @FXML
    private Label lblname;

    @FXML
    private Label lblnic;

    @FXML
    private Label lblCustomerId;

    @FXML
    private TableView<CustomerTM> tblcust;



    @FXML
    private TextField txtemail;

    @FXML
    private TextField txtname;

    @FXML
    private TextField txtnic;

    @FXML
    private TextField txtphone;

    @FXML
    void btnsave(ActionEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colcustId.setCellValueFactory(new PropertyValueFactory<CustomerTM, String>("colcustId"));
        colname.setCellValueFactory(new PropertyValueFactory<CustomerTM, String>("colname"));
        colnic.setCellValueFactory(new PropertyValueFactory<CustomerTM, String>("colnic"));
        colemail.setCellValueFactory(new PropertyValueFactory<CustomerTM, String>("colemail"));
        colphone.setCellValueFactory(new PropertyValueFactory<CustomerTM, String>("colphone"));

        try{
            loadNextCustomerId();
        }catch (Exception e){
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR,"Fail to load customer id");
        }
    }

    CustomerModel customerModel = new CustomerModel();
    public  void  loadNextCustomerId()throws Exception{
        String nextCustomerId = customerModel.getNextCustomerId();
        lblCustomerId.setText(nextCustomerId);
    }

    public void btnReportOnAction(ActionEvent actionEvent) {
    }

    public void btnResetOnAction(ActionEvent actionEvent) {
    }

    public void btnDeleteOnAction(ActionEvent actionEvent) {
    }

    public void btnUpdateOnAction(ActionEvent actionEvent) {
    }

    public void btnSaveOnAction(ActionEvent actionEvent) throws Exception {
        String customerId = lblCustomerId.getText();
        String name = txtname.getText();
        String nic = txtnic.getText();
        String email = txtemail.getText();
        String phone = txtphone.getText();

        CustomerDTO customerDTO = new CustomerDTO(
                customerId,
                name,
                nic,
                email,
                phone);

        boolean isSaved =  customerModel.saveCustomer(customerDTO);
        if(isSaved){
            loadNextCustomerId();
            txtname.setText("");
            txtnic.setText("");
            txtemail.setText("");
            txtphone.setText("");
            new Alert(Alert.AlertType.INFORMATION,"Customer saved...!").show();
        }else{
            new Alert(Alert.AlertType.ERROR,"Fail to save customer...!").show();
        }
    }
}
