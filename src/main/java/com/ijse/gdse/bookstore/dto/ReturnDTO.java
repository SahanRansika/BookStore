package com.ijse.gdse.bookstore.dto;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ReturnDTO {
    private String colID;
    private String colBookID;
    private String colCustomerID;
    private String colQty;
    private String colIssueDate;
    private String colDueDate;
}
