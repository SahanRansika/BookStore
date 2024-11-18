package com.ijse.gdse.bookstore.model;

import com.ijse.gdse.bookstore.dto.OrderDetailsDTO;
import com.ijse.gdse.bookstore.util.CrudUtil;

import java.sql.SQLException;
import java.util.ArrayList;

public class OrderDetailsModel {
    private final BookModel bookModel = new BookModel();

    public boolean saveOrderDetailsList(ArrayList<OrderDetailsDTO> orderDetailsDTOS) throws SQLException {
        // Iterate through each order detail in the list
        for (OrderDetailsDTO orderDetailsDTO : orderDetailsDTOS) {
            // @isOrderDetailsSaved: Saves the individual order detail
            boolean isOrderDetailsSaved = saveOrderDetail(orderDetailsDTO);
            if (!isOrderDetailsSaved) {
                // Return false if saving any order detail fails
                return false;
            }

            // @isBookUpdated: Updates the book quantity in the stock for the corresponding order detail
            boolean isBookUpdated = bookModel.reduceQty(orderDetailsDTO);
            if (!isBookUpdated) {
                // Return false if updating the book quantity fails
                return false;
            }
        }
        // Return true if all order details are saved and item quantities updated successfully
        return true;
    }

    private boolean saveOrderDetail(OrderDetailsDTO orderDetailsDTO) throws SQLException {
        // Executes an insert query to save the order detail into the database
        return CrudUtil.execute(
                "insert into order_details values (?,?,?,?)",
                orderDetailsDTO.getOrderId(),
                orderDetailsDTO.getBookId(),
                orderDetailsDTO.getQuantity(),
                orderDetailsDTO.getPrice()
        );
    }

}
