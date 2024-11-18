package com.ijse.gdse.bookstore.dto;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class BookDTO {
    private String colID;
    private String colISBN;
    private String colTitle;
    private String colWriter;
    private String colCategory;
    private String colPublisher;
    private String colYear;
    private String colQty;
    private String colPrice;
}
