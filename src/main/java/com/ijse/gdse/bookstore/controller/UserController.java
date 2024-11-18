package com.ijse.gdse.bookstore.controller;

import com.ijse.gdse.bookstore.dto.UserDTO;
import com.ijse.gdse.bookstore.dto.tm.UserTM;
import com.ijse.gdse.bookstore.model.UserModel;
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
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class UserController implements Initializable {

    @FXML
    private AnchorPane ancUser;

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
    private TableColumn<?, ?> colPosition;

    @FXML
    private TableColumn<?, ?> colPassword;

    @FXML
    private TableView<UserTM> tblUser;

    @FXML
    private TextField txtEmail;

    @FXML
    private Label lblUser;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtPosition;

    @FXML
    private TextField txtPassword;

    private final UserModel userModel = new UserModel();

    public void initialize(URL url, ResourceBundle resourceBundle) {
        colID.setCellValueFactory(new PropertyValueFactory<>("colID"));
        colName.setCellValueFactory(new PropertyValueFactory<>("colName"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("colEmail"));
        colPosition.setCellValueFactory(new PropertyValueFactory<>("colPosition"));
        colPassword.setCellValueFactory(new PropertyValueFactory<>("colPassword"));

        try {
            refreshPage();
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Failed to load user data").show();
        }
    }
    private void refreshPage() throws SQLException {
        loadNextUserId();
        loadTableData();
        btnSave.setDisable(false);
        btnUpdate.setDisable(true);
        btnDelete.setDisable(true);

        txtName.clear();
        txtEmail.clear();
        txtPosition.clear();
        txtPassword.clear();
    }

    public void loadNextUserId() throws SQLException {
        String nextCustomerId = userModel.getNextUserId();
        lblUser.setText(nextCustomerId);
    }

    private void loadTableData() throws SQLException {
        try {
            ArrayList<UserDTO> userDTOS = userModel.getAllUserIds();
            ObservableList<UserTM> userTMS = FXCollections.observableArrayList();

            for (UserDTO userDTO : userDTOS) {
                UserTM userTM = new UserTM(
                        userDTO.getColID(),
                        userDTO.getColName(),
                        userDTO.getColEmail(),
                        userDTO.getColPosition(),
                        userDTO.getColPassword()
                );
                userTMS.add(userTM);
            }

            tblUser.setItems(userTMS);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void onClickTable(MouseEvent event) {
        UserTM userTM = tblUser.getSelectionModel().getSelectedItem();
        if (userTM != null) {
            lblUser.setText(userTM.getColID());
            txtName.setText(userTM.getColName());
            txtEmail.setText(userTM.getColEmail());
            txtPosition.setText(userTM.getColPosition());
            txtPassword.setText(userTM.getColPassword());

            btnSave.setDisable(true);

            btnDelete.setDisable(false);
            btnUpdate.setDisable(false);
        }
    }
    @FXML
    void btnDeleteOnAction(ActionEvent event) {

    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {
        UserDTO userDTO = new UserDTO(
                lblUser.getText(),
                txtName.getText(),
                txtEmail.getText(),
                txtPosition.getText(),
                txtPassword.getText()
        );

        try {
            boolean isSaved = userModel.saveUser(userDTO);
            if (isSaved) {
                new Alert(Alert.AlertType.CONFIRMATION, "User Saved.").show();
                refreshPage();
            } else {
                new Alert(Alert.AlertType.ERROR, "User is not saved. Something went wrong.").show();
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Error saving user data.").show();
        }
    }


    @FXML
    void btnUpdateOnAction(ActionEvent event) {

    }

    @FXML
    void btnResetOnAction(ActionEvent event) throws SQLException {
        refreshPage();
    }

}
