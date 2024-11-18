package com.ijse.gdse.bookstore.dto;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserDTO {
    private String colID;
    private String colName;
    private String colEmail;
    private String colPosition;
    private String colPassword;
}
