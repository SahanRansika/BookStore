package com.ijse.gdse.bookstore.dto.tm;
import lombok.*;

import java.sql.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ReturnTM {
    private String ID;
    private String BookID;
    private String BookName;
//    private String Qty;
    private Date IssueDate;
    private Date DueDate;
}
