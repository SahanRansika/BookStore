package com.ijse.gdse.bookstore.model;

import com.ijse.gdse.bookstore.db.DBConnection;
import com.ijse.gdse.bookstore.dto.CategoryDTO;
import com.ijse.gdse.bookstore.dto.UserDTO;
import com.ijse.gdse.bookstore.util.CrudUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UserModel {

    public String getNextUserId() throws SQLException {
        ResultSet rst = null;
        rst = CrudUtil.execute("select user_id from user order by user_id desc limit 1");

        if (rst.next()) {
            String lastId = rst.getString(1); // Last user ID
            String substring = lastId.substring(1); // Extract the numeric part
            int i = Integer.parseInt(substring); // Convert the numeric part to integer
            int newIdIndex = i + 1; // Increment the number by 1
            return String.format("U%03d", newIdIndex); // Return the new user ID in format Cnnn
        }
        return "U001"; // Return the default user ID if no data is found
    }
    public boolean saveUser(UserDTO userDTO) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("insert into user values (?,?,?,?,?)",
                userDTO.getColID(),
                userDTO.getColName(),
                userDTO.getColEmail(),
                userDTO.getColPosition(),
                userDTO.getColPassword());
    }
    public ArrayList<UserDTO> getAllUserIds() throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.execute("SELECT * FROM user");
        ArrayList<UserDTO> userDTOS = new ArrayList<>();

        while (rst.next()) {
            UserDTO userDTO = new UserDTO(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getString(5)
            );
            userDTOS.add(userDTO);
        }
        return userDTOS;
    }
}
