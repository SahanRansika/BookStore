package com.ijse.gdse.bookstore.controller;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainLayoutController implements Initializable {

        @FXML
        private AnchorPane ancMain;

        @FXML
        private AnchorPane ancPage;

        @FXML
        private ImageView btnBook;

        @FXML
        private ImageView btnCategory;

        @FXML
        private ImageView btnCustomer;

        @FXML
        private ImageView btnDashBoard;

        @FXML
        private ImageView btnEmployee;

        @FXML
        private ImageView btnLogOut;

        @FXML
        private ImageView btnOrder;

        @FXML
        private ImageView btnReturn;

        @FXML
        private ImageView btnUser;


        @Override
        public void initialize(URL url, ResourceBundle resourceBundle) {
                navigateTo("/view/DashBoard.fxml");
        }


        public void navigateTo(String fxmlPath) {
                try {
                        ancPage.getChildren().clear();
                        AnchorPane load = FXMLLoader.load(getClass().getResource(fxmlPath));

                        load.prefWidthProperty().bind(ancPage.widthProperty());
                        load.prefHeightProperty().bind(ancPage.heightProperty());

                        ancPage.getChildren().add(load);
                } catch (IOException e) {
                        e.printStackTrace();
                        new Alert(Alert.AlertType.ERROR, "Fail to load page!").show();
                }
        }

        public void btnDashBoardOnAction(MouseEvent mouseEvent) {
                navigateTo("/view/DashBoard.fxml");
        }

        public void btnCustomerOnAction(MouseEvent mouseEvent) {
                navigateTo("/view/Customer.fxml");
        }

        public void btnBookOnAction(MouseEvent mouseEvent) {navigateTo("/view/Book.fxml");}

        public void btnCategoryOnAction(MouseEvent mouseEvent) {
                navigateTo("/view/Category.fxml");
        }


        public void btnOrderOnAction(MouseEvent mouseEvent) {
                navigateTo("/view/Orders.fxml");
        }

        public void btnReturnOnAction(MouseEvent mouseEvent) {navigateTo("/view/Return.fxml");}


        public void btnEmployeeOnAction(MouseEvent mouseEvent) {navigateTo("/view/Employee.fxml");}


        public void btnLogOutOnAction(MouseEvent mouseEvent) {System.exit(0);}

        public void btnUserOnAction(MouseEvent mouseEvent) {navigateTo("/view/User.fxml");}
}