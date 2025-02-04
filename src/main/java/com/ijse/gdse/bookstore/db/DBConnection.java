package com.ijse.gdse.bookstore.db;
import lombok.Getter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Getter
public class DBConnection {
    private static DBConnection dbConnection;
    private Connection connection;

    private DBConnection() throws ClassNotFoundException, SQLException{
        Class.forName("com.mysql.cj.jdbc.Driver");
        connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/Bookstore", "root", "Ijse@1234");
    }

    public static DBConnection getInstance()  throws SQLException{
        if(dbConnection ==null){
            try {
                dbConnection = new DBConnection();
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }

        return dbConnection;
    }

}
