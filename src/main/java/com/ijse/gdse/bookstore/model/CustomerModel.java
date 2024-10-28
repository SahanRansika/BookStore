package com.ijse.gdse.bookstore.model;

import com.ijse.gdse.bookstore.db.DBConnection;
import com.ijse.gdse.bookstore.dto.CustomerDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomerModel {
    public String getNextCustomerId() throws Exception{
        Connection connection = DBConnection.getInstance().getConnection();
        String sql ="select customer_id from customer order by customer_id desc limit 1";
        PreparedStatement pst = connection.prepareStatement(sql);

        ResultSet rst = pst.executeQuery();
        if (rst.next()){
            String lastId = rst.getString(1);
            String substring = lastId.substring(1);
            int i = Integer.parseInt(substring);
            int newIdIndex = i+1;
            return String.format("C%03d",newIdIndex);
        }
        return "C001";
    }
    public boolean saveCustomer(CustomerDTO customerDTO) throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.getInstance().getConnection();
        String sql = "insert into customer values (?,?,?,?,?)";
        PreparedStatement pst = connection.prepareStatement(sql);

        pst.setObject(1,customerDTO.getColcustId());
        pst.setObject(2,customerDTO.getColname());
        pst.setObject(3,customerDTO.getColnic());
        pst.setObject(4,customerDTO.getColemail());
        pst.setObject(5,customerDTO.getColphone());

        int result = pst.executeUpdate();
        boolean isSaved = result>0;
        return isSaved;
    }


}
