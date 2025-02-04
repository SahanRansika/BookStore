package com.ijse.gdse.bookstore.model;

import  com.ijse.gdse.bookstore.dto.CustomerDTO;
import com.ijse.gdse.bookstore.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CustomerModel {


    public String getNextCustomerId() throws SQLException {
        ResultSet rst = null;
        rst = CrudUtil.execute("select customer_id from customer order by customer_id desc limit 1");

        if (rst.next()) {
            String lastId = rst.getString(1); // Last customer ID
            String substring = lastId.substring(1); // Extract the numeric part
            int i = Integer.parseInt(substring); // Convert the numeric part to integer
            int newIdIndex = i + 1; // Increment the number by 1
            return String.format("C%03d", newIdIndex); // Return the new customer ID in format Cnnn
        }
        return "C001"; // Return the default customer ID if no data is found
    }

    public boolean saveCustomer(CustomerDTO customerDTO) throws SQLException {
        return CrudUtil.execute("insert into customer values (?,?,?,?)",
                customerDTO.getColID(),
                customerDTO.getColName(),
                customerDTO.getColEmail(),
                customerDTO.getColPhone());
    }

    public ArrayList<CustomerDTO> getAllCustomers() throws SQLException {
        ResultSet rst = null;
        rst = CrudUtil.execute("SELECT * FROM customer");
        ArrayList<CustomerDTO> customerDTOS = new ArrayList<>();

        while (rst.next()) {
            CustomerDTO customerDTO = new CustomerDTO(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4)
            );
            customerDTOS.add(customerDTO);
        }
        return customerDTOS;
    }

    public ArrayList<String> getAllCustomerIds() throws SQLException {
        ResultSet rst = null;
        rst = CrudUtil.execute("select customer_id from customer");

        ArrayList<String> customerIds = new ArrayList<>();

        while (rst.next()) {
            customerIds.add(rst.getString(1));
        }

        return customerIds;
    }

    public boolean updateCustomer(CustomerDTO customerDTO) throws SQLException, ClassNotFoundException{
        return CrudUtil.execute("update customer set customer_name=?, email=?, phone=? where customer_id=?  ",
                customerDTO.getColName(),
                customerDTO.getColEmail(),
                customerDTO.getColPhone(),
                customerDTO.getColID()
                );
    }

    public boolean DeleteCustomer(String customerId) throws SQLException {
        return CrudUtil.execute("delete from customer where customer_id=?", customerId);
    }

    public CustomerDTO findById(String selectedCustomerId) throws SQLException {
        ResultSet rst = null;
        rst = CrudUtil.execute("select * from customer where customer_id=?", selectedCustomerId);

        if (rst.next()) {
            return new CustomerDTO(
                    rst.getString(1),  // Customer ID
                    rst.getString(2),  // Name
                    rst.getString(3),  // Email
                    rst.getString(4)   // Phone
            );
        }
        return null;
    }


}
