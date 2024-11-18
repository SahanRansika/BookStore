package com.ijse.gdse.bookstore.dto.tm;

import javafx.scene.control.Button;
import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CartTM {
    private String BookId;
    private String BookName;
    private int cartQuantity;
    private double unitPrice;
    private double total;
    private Button removeBtn;
}