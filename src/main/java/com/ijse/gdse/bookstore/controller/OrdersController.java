package com.ijse.gdse.bookstore.controller;

import com.ijse.gdse.bookstore.dto.BookDTO;
import com.ijse.gdse.bookstore.dto.CustomerDTO;
import com.ijse.gdse.bookstore.dto.OrderDTO;
import com.ijse.gdse.bookstore.dto.OrderDetailsDTO;
import com.ijse.gdse.bookstore.dto.tm.CartTM;
import com.ijse.gdse.bookstore.model.BookModel;
import com.ijse.gdse.bookstore.model.CustomerModel;
import com.ijse.gdse.bookstore.model.OrdersModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class OrdersController implements Initializable {

    @FXML
    private AnchorPane ancOrder;

    @FXML
    private ComboBox<String> cmbBookId;

    @FXML
    private ComboBox<String> cmbCustomerId;

    @FXML
    private TableColumn<?, ?> colAction;

    @FXML
    private TableColumn<CartTM, String> colBookId;

    @FXML
    private TableColumn<CartTM, String> colName;

    @FXML
    private TableColumn<CartTM, String> colPrice;

    @FXML
    private TableColumn<CartTM, String> colQuantity;

    @FXML
    private TableColumn<CartTM, String> colTotal;

    @FXML
    private Label lblBookName;

    @FXML
    private Label lblBookPrice;

    @FXML
    private Label lblBookQty;

    @FXML
    private Label lblCustomerName;

    @FXML
    private Label lblOrderId;

    @FXML
    private Label orderDate;

    @FXML
    private TableView<CartTM> tblCart;

    @FXML
    private TextField txtAddToCartQty;

    private final OrdersModel ordersModel = new OrdersModel();
    private final CustomerModel customerModel = new CustomerModel();
    private final BookModel bookModel = new BookModel();

    // Observable list to manage cart book in TableView
    private final ObservableList<CartTM> cartTMS = FXCollections.observableArrayList();

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
        colBookId.setCellValueFactory(new PropertyValueFactory<>("BookId"));
        colName.setCellValueFactory(new PropertyValueFactory<>("BookName"));
        colQuantity.setCellValueFactory(new PropertyValueFactory<>("cartQuantity"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("total"));
        colAction.setCellValueFactory(new PropertyValueFactory<>("removeBtn"));

        // Bind the cart book observable list to the TableView
        tblCart.setItems(cartTMS);
    }

    private void refreshPage() throws SQLException {
        // Get the next order ID and set it to the label
        lblOrderId.setText(ordersModel.getNextOrderId());

        // Set the current date to the order date label
//        orderDate.setText(String.valueOf(LocalDate.now()));
        orderDate.setText(LocalDate.now().toString());

        // Load customer and book IDs into ComboBoxes
        loadCustomerIds();
        loadBookIds();

//        ComboBox on action set
//        cmbCustomerId.setOnAction(e->{
//            String selectedCusId = cmbCustomerId.getSelectionModel().getSelectedItem();
//            System.out.println(selectedCusId);
//        });

        // Clear selected customer, item, and their associated labels
        cmbCustomerId.getSelectionModel().clearSelection();
        cmbBookId.getSelectionModel().clearSelection();
        lblBookName.setText("");
        lblBookQty.setText("");
        lblBookPrice.setText("");
        txtAddToCartQty.setText("");
        lblCustomerName.setText("");

        // Clear the cart observable list
        cartTMS.clear();

        // Refresh the table to reflect changes
        tblCart.refresh();
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
    void btnAddToCartOnAction(ActionEvent event) {
        String selectedBookId = cmbBookId.getValue();

        // If no item is selected, show an error alert and return
        if (selectedBookId == null) {
            new Alert(Alert.AlertType.ERROR, "Please select book..!").show();
            return; // Exit the method if no item is selected.
        }

        String cartQtyString = txtAddToCartQty.getText();

        String qtyPattern = "^[0-9]+$";

//        String pricePattern = "^(\\d+)||((\\d+\\.)(\\d){2})$";
//        right :- 500.00. 500.65, 500,
//        wrong :- 787.8, 6777.9999

        if (!cartQtyString.matches(qtyPattern)){
            new Alert(Alert.AlertType.ERROR, "Please enter valid quantity..!").show();
            return;
        }

        String bookName = lblBookName.getText();
        int cartQty = Integer.parseInt(cartQtyString);
        int qtyOnHand = Integer.parseInt(lblBookQty.getText());

        // Check if there are enough book in stock; if not, show an error alert and return
        if (qtyOnHand < cartQty) {
            new Alert(Alert.AlertType.ERROR, "Not enough book..!").show();
            return; // Exit the method if there's insufficient stock.
        }

        // Clear the text field for adding quantity after retrieving the input value.
        txtAddToCartQty.setText("");

        double unitPrice = Double.parseDouble(lblBookPrice.getText());
        double total = unitPrice * cartQty;

        // Loop through each book in cart's observable list.
        for (CartTM cartTM : cartTMS) {

            // Check if the book is already in the cart
            if (cartTM.getBookId().equals(selectedBookId)) {
                // Update the existing CartTM object in the cart's observable list with the new quantity and total.
                int newQty = cartTM.getCartQuantity() + cartQty;
                cartTM.setCartQuantity(newQty); // Add the new quantity to the existing quantity in the cart.
                cartTM.setTotal(unitPrice * newQty); // Recalculate the total price based on the updated quantity

                // Refresh the table to display the updated information.
                tblCart.refresh();
                return; // Exit the method as the cart item has been updated.
            }
        }


        // Create a "Remove" button for the item to allow it to be removed from the cart later.
        Button btn = new Button("Remove");

        // If the item does not already exist in the cart, create a new CartTM object to represent it.
        CartTM newCartTM = new CartTM(
                selectedBookId,
                bookName,
                cartQty,
                unitPrice,
                total,
                btn
        );

        // Set an action for the "Remove" button, which removes the item from the cart when clicked.
        btn.setOnAction(actionEvent -> {

            // Remove the item from the cart's observable list (cartTMS).
            cartTMS.remove(newCartTM);

            // Refresh the table to reflect the removal of the item.
            tblCart.refresh();
        });

        // Add the newly created CartTM object to the cart's observable list.
        cartTMS.add(newCartTM);
    }

    @FXML
    void btnPlaceOrderOnAction(ActionEvent event) throws SQLException {
        if (tblCart.getItems().isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Please add books to cart..!").show();
            return;
        }
        if (cmbCustomerId.getSelectionModel().isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Please select customer for place order..!").show();
            return;
        }

        String orderId = lblOrderId.getText();
        java.sql.Date dateOfOrder = Date.valueOf(orderDate.getText());
        String customerId = cmbCustomerId.getValue();

        // List to hold order details
        ArrayList<OrderDetailsDTO> orderDetailsDTOS = new ArrayList<>();

        // Collect data for each book in the cart and add to order details array
        for (CartTM cartTM : cartTMS) {

            // Create order details for each cart book
            OrderDetailsDTO orderDetailsDTO = new OrderDetailsDTO(
                    orderId,
                    cartTM.getBookId(),
                    cartTM.getCartQuantity(),
                    cartTM.getUnitPrice()
            );

            // Add to order details array
            orderDetailsDTOS.add(orderDetailsDTO);
        }

        // Create an OrderDTO with relevant order data
        OrderDTO orderDTO = new OrderDTO(
                orderId,
                customerId,
                dateOfOrder,
                orderDetailsDTOS
        );

        boolean isSaved = ordersModel.saveOrder(orderDTO);

        if (isSaved) {
            new Alert(Alert.AlertType.INFORMATION, "Order saved..!").show();

            // Reset the page after placing the order
            refreshPage();
        } else {
            new Alert(Alert.AlertType.ERROR, "Order fail..!").show();
        }
    }

    @FXML
    void btnResetOnAction(ActionEvent event) throws SQLException{
        refreshPage();
    }

    @FXML
    void cmbCustomerOnAction(ActionEvent event) throws SQLException {
        String selectedCustomerId = cmbCustomerId.getSelectionModel().getSelectedItem();
        CustomerDTO customerDTO = customerModel.findById(selectedCustomerId);

        // If customer found (customerDTO not null)
        if (customerDTO != null) {

            // FIll customer related labels
            lblCustomerName.setText(customerDTO.getColName());
        }
    }

    /**
     * This method is called when an book is selected from the item ComboBox.
     * It retrieves and displays the book's details based on the selected ID.
     */
    @FXML
    void cmbBookOnAction(ActionEvent event) throws SQLException{
        String selectedBookId = cmbBookId.getSelectionModel().getSelectedItem();
        BookDTO bookDTO = bookModel.findById(selectedBookId);

        // If item found (itemDTO not null)
        if (bookDTO != null) {

            // FIll item related labels
            lblBookName.setText(bookDTO.getColTitle());
            lblBookQty.setText(String.valueOf(bookDTO.getColQty()));
            lblBookPrice.setText(String.valueOf(bookDTO.getColPrice()));
        }
    }
}
