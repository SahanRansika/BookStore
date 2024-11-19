package com.ijse.gdse.bookstore.model;

import com.ijse.gdse.bookstore.db.DBConnection;
import com.ijse.gdse.bookstore.dto.BookDTO;
import com.ijse.gdse.bookstore.dto.OrderDetailsDTO;
import com.ijse.gdse.bookstore.util.CrudUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class BookModel {

    public String getNextBookId() throws SQLException {
        ResultSet rst = null;
        rst = CrudUtil.execute("select book_id from book order by book_id desc limit 1");

        if (rst.next()) {
            String lastId = rst.getString(1); // Last book ID
            String substring = lastId.substring(1); // Extract the numeric part
            int i = Integer.parseInt(substring); // Convert the numeric part to integer
            int newIdIndex = i + 1; // Increment the number by 1
            return String.format("B%03d", newIdIndex); // Return the new book ID in format Cnnn
        }
        return "B001"; // Return the default book ID if no data is found
    }

    public ArrayList<String> getAllBookIds() throws SQLException {
        // Execute SQL query to get all book IDs
        ResultSet rst = CrudUtil.execute("select book_id from book");

        // Create an ArrayList to store the item IDs
        ArrayList<String> bookIds = new ArrayList<>();

        // Iterate through the result set and add each book ID to the list
        while (rst.next()) {
            bookIds.add(rst.getString(1));
        }

        // Return the list of item IDs
        return bookIds;
    }

    public ArrayList<BookDTO> getAllBooks() throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT * FROM book");
        ArrayList<BookDTO> bookDTOS = new ArrayList<>();

        while (rst.next()) {
            BookDTO bookDTO = new BookDTO(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getString(5),
                    rst.getString(6),
                    rst.getInt(7),
                    rst.getInt(8),
                    rst.getDouble(9)
            );
            bookDTOS.add(bookDTO);
        }
        return bookDTOS;
    }

    public boolean saveBook(BookDTO bookDTO) throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.getInstance().getConnection();
        String sql = "INSERT INTO book VALUES (?,?,?,?,?,?,?,?,?)";
        PreparedStatement pst = connection.prepareStatement(sql);

        pst.setObject(1, bookDTO.getColID());
        pst.setObject(2, bookDTO.getColISBN());
        pst.setObject(3, bookDTO.getColTitle());
        pst.setObject(4, bookDTO.getColWriter());
        pst.setObject(5, bookDTO.getColCategory());
        pst.setObject(6, bookDTO.getColPublisher());
        pst.setObject(7, bookDTO.getColYear());
        pst.setObject(8, bookDTO.getColQty());
        pst.setObject(9, bookDTO.getColPrice());

        int result = pst.executeUpdate();
        return result > 0;
    }

    public boolean updateBook(BookDTO bookDTO) throws SQLException {
        return CrudUtil.execute(
                "update book set isbn=?, title=?, writer=?, category_name=?, publisher=?, year=?, qty=?, price=? where book_id=?",
                bookDTO.getColISBN(),
                bookDTO.getColTitle(),
                bookDTO.getColWriter(),
                bookDTO.getColCategory(),
                bookDTO.getColPublisher(),
                bookDTO.getColYear(),
                bookDTO.getColQty(),
                bookDTO.getColPrice(),
                bookDTO.getColID()
        );
    }

    public BookDTO findById(String selectedBookId) throws SQLException {
        // Execute SQL query to find the book by ID
        ResultSet rst = CrudUtil.execute("select * from book where book_id=?", selectedBookId);

        // If the book is found, create an BookDTO object with the retrieved data
        if (rst.next()) {
            return new BookDTO(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getString(5),
                    rst.getString(6),
                    rst.getInt(7),
                    rst.getInt(8),
                    rst.getDouble(9)
            );
        }

        // Return null if the book is not found
        return null;
    }
    public boolean reduceQty(OrderDetailsDTO orderDetailsDTO) throws SQLException {
        // Execute SQL query to update the book quantity in the database
        return CrudUtil.execute(
                "update book set qty = qty - ? where book_id = ?",
                orderDetailsDTO.getQuantity(),   // Quantity to reduce
                orderDetailsDTO.getBookId()      // Book ID
        );
    }
}
