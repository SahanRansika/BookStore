package com.ijse.gdse.bookstore.dto.tm;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class BookTM {
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
