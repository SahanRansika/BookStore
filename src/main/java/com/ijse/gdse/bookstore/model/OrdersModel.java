package com.ijse.gdse.bookstore.model;

import com.ijse.gdse.bookstore.db.DBConnection;
import com.ijse.gdse.bookstore.dto.OrderDTO;
import com.ijse.gdse.bookstore.util.CrudUtil;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class OrdersModel {
    private final OrderDetailsModel orderDetailsModel = new OrderDetailsModel();

    public String getNextOrderId() throws SQLException{
        // @rst: ResultSet from the query fetching the last order ID from the orders table
        ResultSet rst = CrudUtil.execute("select order_id from orders order by order_id desc limit 1");

        if (rst.next()) {
            // @lastId: Retrieves the last order ID
            String lastId = rst.getString(1); // e.g., "O002"
            // @substring: Extracts the numeric part from the order ID
            String substring = lastId.substring(1); // e.g., "002"
            // @i: Converts the numeric part to an integer
            int i = Integer.parseInt(substring); // 2
            // @newIdIndex: Increments the numeric part by 1
            int newIdIndex = i + 1; // 3
            // Returns the new order ID, formatted as "O" followed by a 3-digit number (e.g., O003)
            return String.format("O%03d", newIdIndex);
        }
        // Returns "O001" if no previous orders are found
        return "O001";
    }

    public boolean saveOrder(OrderDTO orderDTO) throws SQLException {
        // @connection: Retrieves the current connection instance for the database
        Connection connection = DBConnection.getInstance().getConnection();
        System.out.println(orderDTO);
        try {
            // @autoCommit: Disables auto-commit to manually control the transaction
            connection.setAutoCommit(false); // 1

            // @isOrderSaved: Saves the order details into the orders table
            boolean isOrderSaved = CrudUtil.execute(
                    "insert into orders values (?,?,?)",
                    orderDTO.getOrderId(),
                    orderDTO.getCustomerId(),
                    orderDTO.getOrderDate()
            );
            // If the order is saved successfully
            if (isOrderSaved) {
                // @isOrderDetailListSaved: Saves the list of order details
                boolean isOrderDetailListSaved = orderDetailsModel.saveOrderDetailsList(orderDTO.getOrderDetailsDTOS());
                if (isOrderDetailListSaved) {
                    // @commit: Commits the transaction if both order and details are saved successfully
                    connection.commit(); // 2
                    return true;
                }
            }
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
