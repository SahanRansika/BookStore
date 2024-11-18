package com.ijse.gdse.bookstore.model;

import com.ijse.gdse.bookstore.db.DBConnection;
import com.ijse.gdse.bookstore.dto.EmployeeDTO;
import com.ijse.gdse.bookstore.util.CrudUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class EmployeeModel {

    public String getNextEmployeeId() throws SQLException {
        ResultSet rst = null;
        rst = CrudUtil.execute("select employee_id from employee order by employee_id desc limit 1");

        if (rst.next()) {
            String lastId = rst.getString(1); // Last book ID
            String substring = lastId.substring(1); // Extract the numeric part
            int i = Integer.parseInt(substring); // Convert the numeric part to integer
            int newIdIndex = i + 1; // Increment the number by 1
            return String.format("E%03d", newIdIndex); // Return the new book ID in format Cnnn
        }
        return "E001"; // Return the default book ID if no data is found
    }

    public boolean saveEmployee(EmployeeDTO employeeDTO) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("insert into employee values (?,?,?,?,?)",
                employeeDTO.getColID(),
                employeeDTO.getColName(),
                employeeDTO.getColPhone(),
                employeeDTO.getColPosition(),
                employeeDTO.getColSalary());
    }

    public ArrayList<EmployeeDTO> getAllEmployeeIds() throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.execute("SELECT * FROM employee");
        ArrayList<EmployeeDTO> employeeDTOS = new ArrayList<>();

        while (rst.next()) {
            EmployeeDTO employeeDTO = new EmployeeDTO(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getString(5)
            );
            employeeDTOS.add(employeeDTO);
        }
        return employeeDTOS;
    }
}
