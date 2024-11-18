package com.ijse.gdse.bookstore.util;

public class Validation {
    public static boolean isValidName(String name){
        return name.matches("^[A-Za-z ]+$");
    }

    public static boolean isValidEmail(String email){
        return email.matches("^[\\\\w!#$%&'*+/=?`{|}~^-]+(?:\\\\.[\\\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\\\.)+[a-zA-Z]{2,6}$");
    }

    public static boolean isValidContact(String name){
        return name.matches("^(\\\\d+)||((\\\\d+\\\\.)(\\\\d){2})$");
    }


}
