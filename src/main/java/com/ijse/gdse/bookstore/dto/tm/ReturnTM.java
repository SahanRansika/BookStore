package com.ijse.gdse.bookstore.dto.tm;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ReturnTM {
    private String ID;
    private String BookID;
    private String CustomerID;
    private String Qty;
    private String IssueDate;
    private String DueDate;
}
