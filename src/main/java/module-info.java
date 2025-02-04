module com.ijse.gdse.bookstore {
    requires javafx.controls;
    requires javafx.fxml;
    requires lombok;
    requires java.sql;
    requires net.sf.jasperreports.core;
    requires java.mail;

    opens com.ijse.gdse.bookstore.dto.tm to javafx.base;
    opens com.ijse.gdse.bookstore.controller to javafx.fxml;
    exports com.ijse.gdse.bookstore;
}