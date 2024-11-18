package com.ijse.gdse.bookstore.model;

import com.ijse.gdse.bookstore.db.DBConnection;
import com.ijse.gdse.bookstore.dto.CategoryDTO;
import com.ijse.gdse.bookstore.util.CrudUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CategoryModel {


    public String getNextCategoryId() throws SQLException {
        ResultSet rst = null;
        rst = CrudUtil.execute("select code from category order by code desc limit 1");

        if (rst.next()) {
            String lastId = rst.getString(  1); // Last code
            String substring = lastId.substring(3); // Extract the numeric part
            int i = Integer.parseInt(substring); // Convert the numeric part to integer
            int newIdIndex = i + 1; // Increment the number by 1
            return String.format("CAT%03d", newIdIndex); // Return the new code in format Cnnn
        }
        return "CAT001"; // Return the default code if no data is found
    }

    public boolean saveCategory(CategoryDTO categoryDTO) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("insert into category values (?,?,?)",
                categoryDTO.getColName(),
                categoryDTO.getColStatus(),
                categoryDTO.getColCode());
    }

    public ArrayList<CategoryDTO> getAllCategoryIds() throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.execute("SELECT * FROM category");
        ArrayList<CategoryDTO> categoryDTOS = new ArrayList<>();

        while (rst.next()) {
            CategoryDTO categoryDTO = new CategoryDTO(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3)
            );
            categoryDTOS.add(categoryDTO);
        }
        return categoryDTOS;
    }
}
