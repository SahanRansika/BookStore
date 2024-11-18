package com.ijse.gdse.bookstore.model;

import com.ijse.gdse.bookstore.db.DBConnection;
import com.ijse.gdse.bookstore.dto.OrderDTO;
import com.ijse.gdse.bookstore.dto.ReturnDTO;
import com.ijse.gdse.bookstore.util.CrudUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ReturnModel {

    public String getNextReturnId() throws SQLException {
        ResultSet rst = null;
        rst = CrudUtil.execute("select return_id from book_return order by return_id desc limit 1");

        if (rst.next()) {
            String lastId = rst.getString(1); // Last return ID
            String substring = lastId.substring(1); // Extract the numeric part
            int i = Integer.parseInt(substring); // Convert the numeric part to integer
            int newIdIndex = i + 1; // Increment the number by 1
            return String.format("R%03d", newIdIndex); // Return the new return ID in format Cnnn
        }
        return "R001"; // Return the default return ID if no data is found
    }

    public boolean saveOrder(ReturnDTO returnDTO) throws SQLException {
        // @connection: Retrieves the current connection instance for the database
        Connection connection = DBConnection.getInstance().getConnection();
        try {
            // @autoCommit: Disables auto-commit to manually control the transaction
            connection.setAutoCommit(false); // 1

            // @isOrderSaved: Saves the order details into the orders table
            boolean isOrderSaved = CrudUtil.execute(
                    "insert into orders values (?,?,?,?)",
                    returnDTO.getColBookID(),
                    returnDTO.getColCustomerID(),
                    returnDTO.getColIssueDate(),
                    returnDTO.getColDueDate()
            );
//            // If the order is saved successfully
//            if (isOrderSaved) {
//                // @isOrderDetailListSaved: Saves the list of order details
//                boolean isOrderDetailListSaved = returnDTO.save(returnDTO.getOrderDetailsDTOS());
//                if (isOrderDetailListSaved) {
//                    // @commit: Commits the transaction if both order and details are saved successfully
//                    connection.commit(); // 2
//                    return true;
//                }
          //  }
            // @rollback: Rolls back the transaction if order details saving fails
            connection.rollback(); // 3
            return false;
        } catch (Exception e) {
            // @catch: Rolls back the transaction in case of any exception
            connection.rollback();
            return false;
        } finally {
            // @finally: Resets auto-commit to true after the operation
            connection.setAutoCommit(true); // 4
        }
    }

}
