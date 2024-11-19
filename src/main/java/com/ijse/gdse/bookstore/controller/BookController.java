package com.ijse.gdse.bookstore.controller;

import com.ijse.gdse.bookstore.dto.BookDTO;
import com.ijse.gdse.bookstore.dto.tm.BookTM;
import com.ijse.gdse.bookstore.dto.tm.CustomerTM;
import com.ijse.gdse.bookstore.model.BookModel;
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

public class BookController implements Initializable {

    @FXML
    private AnchorPane ancBook;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnUpdate;

    @FXML
    private Button btnReset;

    @FXML
    private TableColumn<BookTM, String> colCategory;

    @FXML
    private TableColumn<BookTM, String> colID;

    @FXML
    private TableColumn<BookTM, String> colISBN;

    @FXML
    private TableColumn<BookTM, String> colPrice;

    @FXML
    private TableColumn<BookTM, String> colPublisher;

    @FXML
    private TableColumn<BookTM, String> colQty;

    @FXML
    private TableColumn<BookTM, String> colTitle;

    @FXML
    private TableColumn<BookTM, String> colWriter;

    @FXML
    private TableColumn<BookTM, String> colYear;

    @FXML
    private TableView<BookTM> tblBook;

    @FXML
    private TextField txtCategory;

    @FXML
    private Label lblBook;

    @FXML
    private TextField txtISBN;

    @FXML
    private TextField txtPrice;

    @FXML
    private TextField txtPublisher;

    @FXML
    private TextField txtQty;

    @FXML
    private TextField txtTitle;

    @FXML
    private TextField txtWriter;

    @FXML
    private TextField txtYear;

    private final BookModel bookModel = new BookModel();

    public void initialize(URL url, ResourceBundle resourceBundle) {
        colID.setCellValueFactory(new PropertyValueFactory<>("colID"));
        colISBN.setCellValueFactory(new PropertyValueFactory<>("colISBN"));
        colTitle.setCellValueFactory(new PropertyValueFactory<>("colTitle"));
        colWriter.setCellValueFactory(new PropertyValueFactory<>("colWriter"));
        colCategory.setCellValueFactory(new PropertyValueFactory<>("colCategory"));
        colPublisher.setCellValueFactory(new PropertyValueFactory<>("colPublisher"));
        colYear.setCellValueFactory(new PropertyValueFactory<>("colYear"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("colQty"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("colPrice"));

        try {
            refreshPage();
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Failed to load book data").show();
        }
    }

    private void refreshPage() throws SQLException {
        loadNextBookId();
        loadTableData();

        btnSave.setDisable(false);
        btnUpdate.setDisable(true);
        btnDelete.setDisable(true);


        txtISBN.clear();
        txtTitle.clear();
        txtWriter.clear();
        txtCategory.clear();
        txtPublisher.clear();
        txtYear.clear();
        txtQty.clear();
        txtPrice.clear();
    }

    public void loadNextBookId() throws SQLException {
        String nextBookId = bookModel.getNextBookId();
        lblBook.setText(nextBookId);
    }

    private void loadTableData() throws SQLException {
        ArrayList<BookDTO> bookDTOS = bookModel.getAllBooks();
        ObservableList<BookTM> bookTMS = FXCollections.observableArrayList();

        for (BookDTO bookDTO : bookDTOS) {
            BookTM bookTM = new BookTM(
                    bookDTO.getColID(),
                    bookDTO.getColISBN(),
                    bookDTO.getColTitle(),
                    bookDTO.getColWriter(),
                    bookDTO.getColCategory(),
                    bookDTO.getColPublisher(),
                    bookDTO.getColYear(),
                    bookDTO.getColQty(),
                    bookDTO.getColPrice()
            );
            bookTMS.add(bookTM);
        }

        tblBook.setItems(bookTMS);
    }

    @FXML
    void onClickTable(MouseEvent event) {
        BookTM bookTM = tblBook.getSelectionModel().getSelectedItem();
        if (bookTM != null) {
            lblBook.setText(bookTM.getColID());
            txtISBN.setText(bookTM.getColISBN());
            txtTitle.setText(bookTM.getColTitle());
            txtWriter.setText(bookTM.getColWriter());
            txtCategory.setText(bookTM.getColCategory());
            txtPublisher.setText(bookTM.getColPublisher());
            txtYear.setText(String.valueOf(bookTM.getColYear()));
            txtQty.setText(String.valueOf(bookTM.getColQty()));
            txtPrice.setText(String.valueOf(bookTM.getColPrice()));

            btnSave.setDisable(true);

            btnDelete.setDisable(false);
            btnUpdate.setDisable(false);
        }
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) throws SQLException{
        String bookId = lblBook.getText();
        String isbn = txtISBN.getText();
        String title = txtTitle.getText();
        String writer = txtWriter.getText();
        String category = txtCategory.getText();
        String publisher = txtPublisher.getText();
        int year = Integer.parseInt(txtYear.getText());
        int qty = Integer.parseInt(txtQty.getText());
        double price = Double.parseDouble(txtPrice.getText());

        BookDTO bookDTO = new BookDTO(
                bookId,
                isbn,
                title,
                writer,
                category,
                publisher,
                year,
                qty,
                price
        );

        try {
            boolean isSaved = bookModel.saveBook(bookDTO);
            if (isSaved) {
                new Alert(Alert.AlertType.CONFIRMATION, "Book Saved.").show();
                refreshPage();
            } else {
                new Alert(Alert.AlertType.ERROR, "Book is not saved. Something went wrong.").show();
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Error saving book data.").show();
        }
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) throws SQLException {
        String bookId = lblBook.getText();
        String isbn = txtISBN.getText();
        String title = txtTitle.getText();
        String writer = txtWriter.getText();
        String category = txtCategory.getText();
        String publisher = txtPublisher.getText();
        int year = Integer.parseInt(txtYear.getText());
        int qty = Integer.parseInt(txtQty.getText());
        double price = Double.parseDouble(txtPrice.getText());

        BookDTO bookDTO = new BookDTO(
                bookId,
                isbn,
                title,
                writer,
                category,
                publisher,
                year,
                qty,
                price
        );

        boolean isUpdate = bookModel.updateBook(bookDTO);
        if (isUpdate) {
            new Alert(Alert.AlertType.CONFIRMATION, "Book Update.").show();
            refreshPage();
        } else {
            new Alert(Alert.AlertType.ERROR, "Book is not update. Something went wrong.").show();
        }
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {

    }

    @FXML
    void btnResetOnAction(ActionEvent event) throws SQLException {
        refreshPage();
    }
}
